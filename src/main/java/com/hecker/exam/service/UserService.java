package com.hecker.exam.service;

import com.hecker.exam.dto.request.user.UserCreationRequest;
import com.hecker.exam.dto.request.user.UserUpdateRequest;
import com.hecker.exam.dto.response.StatusCode;
import com.hecker.exam.entity.CandidateResult;
import com.hecker.exam.entity.TestSession;
import com.hecker.exam.entity.enums.Role;
import com.hecker.exam.entity.User;
import com.hecker.exam.entity.enums.Type;
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
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserService {
    UserRepository repos;
    UserMapper mapper;
    Validator validator;
    PasswordEncoder encoder;

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

    public User createUser(UserCreationRequest request) {
        User user = mapper.createUser(request);
        user.setUsername(generateUsername(request.getFullName()));

        user.setPassword(encoder.encode(request.getDob().format(DateTimeFormatter.ofPattern("ddMMyyyy"))));

        user.setRole(Role.USER);
        return repos.save(user);
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

    public User getMyInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return repos.findByUsername(username).orElseThrow(
                () -> new AppException(StatusCode.USER_NOT_FOUND)
        );
    }

    public User getUserByUsername(String username) {
        return repos.findByUsername(username).orElseThrow(
                () -> new AppException(StatusCode.USER_NOT_FOUND)
        );
    }

    public List<User> getUsers() {
        return repos.findByRoleAndIsDeleted(Role.USER, false);
    }

    public List<User> getUsersByTypes(String[] types) {
        Set<User> users = new HashSet<>();
        for(String type : types){
            users.addAll(repos.findByTypeAndIsDeleted(Type.fromString(type), false));
        }
        return users.stream().toList();
    }

    public List<CandidateResult> getTakenTests() {
        User user = getMyInfo();
        return user.getTakenTests();
    }

    public List<CandidateResult> getTakenTests(String username) {
        User user = getUserByUsername(username);
        return user.getTakenTests();
    }

    public List<TestSession> getAssignedSessions() {
        User user = getMyInfo();
        return user.getAssignedSessions();
    }

    public List<TestSession> getAssignedSessions(String username) {
        User user = getUserByUsername(username);
        return user.getAssignedSessions();
    }

    @Transactional
    public User updateUser(String username, UserUpdateRequest request) {
        User targetUser = repos.findByUsername(username).orElseThrow(
                () -> new AppException(StatusCode.USER_NOT_FOUND)
        );
        User newUser = mapper.updateUser(targetUser, request);
        newUser.setPassword(encoder.encode(request.getDob().format(DateTimeFormatter.ofPattern("ddMMyyyy"))));

        return repos.save(newUser);
    }

    @Transactional
    public void deleteUser(String username) {
        User targetUser = repos.findByUsername(username).orElseThrow(
                () -> new AppException(StatusCode.USER_NOT_FOUND)
        );
        repos.delete(targetUser);
    }
}
