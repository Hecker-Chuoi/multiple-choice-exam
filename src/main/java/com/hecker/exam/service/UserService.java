package com.hecker.exam.service;

import com.hecker.exam.dto.request.auth.UserCreationRequest;
import com.hecker.exam.dto.request.auth.UserUpdateRequest;
import com.hecker.exam.dto.response.StatusCode;
import com.hecker.exam.dto.response.UserResponse;
import com.hecker.exam.entity.enums.Role;
import com.hecker.exam.entity.User;
import com.hecker.exam.exception.AppException;
import com.hecker.exam.mapper.UserMapper;
import com.hecker.exam.repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import jakarta.validation.Validator;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
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

    private String generateUsername(String fullName) {
        StringBuilder sb = new StringBuilder();
        for(String part : fullName.toLowerCase().trim().split("\\s+")){
            sb.append(part.charAt(0));
        }
        sb.append((int)(Math.random() * 1000));
        if(repos.existsByUsername(sb.toString()))
            return generateUsername(fullName);
        return sb.toString();
    }

    public UserResponse createUser(UserCreationRequest request) {
        User user = mapper.createUser(request);
        user.setUsername(generateUsername(request.getFullName()));

        PasswordEncoder encoder = new BCryptPasswordEncoder(10);
        user.setPassword(encoder.encode(request.getDob().format(DateTimeFormatter.ofPattern("ddMMyyyy"))));

        user.setRole(Role.CANDIDATE);
        return mapper.toResponse(repos.save(user));
    }

    public User getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return getUserByUsername(username);
    }

    public User getUserByUsername(String username) {
        return repos.findByUsername(username).orElseThrow(
                () -> new AppException(StatusCode.USER_NOT_FOUND)
        );
    }

    public List<UserResponse> getCandidates() {
        return mapper.toResponses(repos.findByRole(Role.CANDIDATE));
    }

    public List<UserResponse> getAllUsers() {
        return mapper.toResponses(repos.findAll());
    }

    @Transactional
    public UserResponse updateUser(String username, UserUpdateRequest request) {
        User targetUser = repos.findByUsername(username).orElseThrow(
                () -> new AppException(StatusCode.USER_NOT_FOUND)
        );
        User newUser = repos.save(mapper.updateUser(targetUser, request));
        return mapper.toResponse(newUser);
    }

    @Transactional
    public void deleteUser(String username) {
        User targetUser = repos.findByUsername(username).orElseThrow(
                () -> new AppException(StatusCode.USER_NOT_FOUND)
        );
        repos.delete(targetUser);
    }

    @Transactional
    public List<UserResponse> createUsersFromExcel(MultipartFile excelFile) throws IOException {
        List<UserCreationRequest> requests = ExcelInputService.readUserRequestFromExcel(excelFile);
        List<UserResponse> result = new ArrayList<>();
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
