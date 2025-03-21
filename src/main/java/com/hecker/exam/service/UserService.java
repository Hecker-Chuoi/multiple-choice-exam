package com.hecker.exam.service;

import com.hecker.exam.dto.request.UserCreationRequest;
import com.hecker.exam.dto.request.UserUpdateRequest;
import com.hecker.exam.dto.response.StatusCode;
import com.hecker.exam.entity.Role;
import com.hecker.exam.entity.User;
import com.hecker.exam.exception.AppException;
import com.hecker.exam.mapper.UserMapper;
import com.hecker.exam.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Valid;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.StringJoiner;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository repos;
    UserMapper mapper;
    Validator validator;

    public User createUser(UserCreationRequest request) {
        if(repos.existsByUsername(request.getUsername()))
            throw new AppException(StatusCode.USERNAME_ALREADY_EXISTS);

        PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        User user = mapper.createUser(request);
        user.setPassword(encoder.encode(request.getPassword()));

        user.setRole(Role.CANDIDATE);

        return repos.save(user);
    }

    public User getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return getUserByUsername(username);
    }

    public User getUserByUsername(String username) {
        return repos.findByUsername(username).orElseThrow(
                () -> new RuntimeException("User not found")
        );
    }

    public List<User> getCandidates() {
        return repos.findByRole(Role.CANDIDATE);
    }

    public List<User> getAllUsers() {
        return repos.findAll();
    }

    @Transactional
    public User updateUser(String username, UserUpdateRequest request) {
        User targetUser = getUserByUsername(username);
        return repos.save(mapper.updateUser(targetUser, request));
    }

    @Transactional
    public void deleteUser(String username) {
        User targetUser = getUserByUsername(username);
        repos.delete(targetUser);
    }

    @Transactional
    public List<User> createUsersFromExcel(MultipartFile excelFile) throws IOException {
        List<UserCreationRequest> requests = ExcelInputService.readUserRequestFromExcel(excelFile);
        List<User> result = new ArrayList<>();
        for (int i = 0; i < requests.size(); ++i) {
            Set<ConstraintViolation<UserCreationRequest>> violations = validator.validate(requests.get(i));
            if(!violations.isEmpty()){
                StringJoiner joiner = new StringJoiner(" & ");
                for (ConstraintViolation<UserCreationRequest> constraintViolation : violations) {
                    joiner.add(constraintViolation.getMessage());
                }
                throw new ConstraintViolationException("Error occurred in record %d: ".formatted(i) + joiner.toString(), violations);
            }
            result.add(createUser(requests.get(i)));
        }
        return result;
    }
}
