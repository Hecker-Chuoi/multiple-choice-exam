package com.hecker.exam.controller;

import com.hecker.exam.dto.request.user.UserCreationRequest;
import com.hecker.exam.dto.request.user.UserUpdateRequest;
import com.hecker.exam.dto.response.ApiResponse;
import com.hecker.exam.dto.response.ResultResponse;
import com.hecker.exam.dto.response.SessionResponse;
import com.hecker.exam.dto.response.UserResponse;
import com.hecker.exam.entity.TestSession;
import com.hecker.exam.entity.User;
import com.hecker.exam.mapper.CandidateResultMapper;
import com.hecker.exam.mapper.SessionMapper;
import com.hecker.exam.mapper.UserMapper;
import com.hecker.exam.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.Session;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class UserController {
    UserService service;
    UserMapper mapper;
    CandidateResultMapper resultMapper;
    SessionMapper sessionMapper;

    @Operation(summary = "1. Create user")
    @SecurityRequirement(name = "admin-token")
    @PostMapping("/one")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        User res = service.createUser(request);
        return ApiResponse.<UserResponse>builder()
                .result(mapper.toResponse(res))
                .build();
    }

    @Operation(summary = "2. Create users")
    @SecurityRequirement(name = "admin-token")
    @PostMapping("/many")
    public ApiResponse<List<UserResponse>> createUsers(@RequestParam("file") MultipartFile file) throws IOException {
        List<User> users = service.createUsersFromExcel(file);
        return ApiResponse.<List<UserResponse>>builder()
                .result(mapper.toResponses(users))
                .build();
    }

    @Operation(summary = "3. Get user by username")
    @SecurityRequirement(name = "admin-token")
    @GetMapping("/{username}")
    public ApiResponse<UserResponse> getUserByUsername(@PathVariable String username) {
        User user = service.getUserByUsername(username);
        return ApiResponse.<UserResponse>builder()
                .result(mapper.toResponse(user))
                .build();
    }

    @Operation(summary = "4. Get all users")
    @SecurityRequirement(name = "admin-token")
    @GetMapping("/all")
    public ApiResponse<List<UserResponse>> getUsers() {
        List<User> users = service.getUsers();
        return ApiResponse.<List<UserResponse>>builder()
                .result(mapper.toResponses(users))
                .build();
    }

    @Operation(summary = "5. Get users by type")
    @SecurityRequirement(name = "admin-token")
    @GetMapping("/type")
    public ApiResponse<List<UserResponse>> getUsersByType(@RequestBody List<String> type) {
        List<User> users = service.getUsersByTypes(type);
        return ApiResponse.<List<UserResponse>>builder()
                .result(mapper.toResponses(users))
                .build();
    }

    @Operation(summary = "6. Get current logged in user")
    @SecurityRequirement(name = "user-token")
    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(mapper.toResponse(service.getMyInfo()))
                .build();
    }

    @Operation(summary = "7. Get my taken tests")
    @SecurityRequirement(name = "user-token")
    @GetMapping("/takenTests/{status}")
    public ApiResponse<List<ResultResponse>> getMyTakenTests(@PathVariable(required = false) String status) {
        return ApiResponse.<List<ResultResponse>>builder()
                .result(resultMapper.toResponses(service.getTakenTests(status)))
                .build();
    }

    @Operation(summary = "8. Get my assigned sessions")
    @SecurityRequirement(name = "user-token")
    @GetMapping("/assignedSessions")
    public ApiResponse<List<SessionResponse>> getMyAssignedSessions() {
        List<TestSession> results = service.getAssignedSessions();
        return ApiResponse.<List<SessionResponse>>builder()
                .result(sessionMapper.toResponses(results))
                .build();
    }

    @Operation(summary = "9. Get my upcoming session")
    @SecurityRequirement(name = "user-token")
    @GetMapping("/upcomingSession")
    public ApiResponse<List<SessionResponse>> getUpcomingSession() {
        List<TestSession> results = service.getMyUpcomingSession();
        return ApiResponse.<List<SessionResponse>>builder()
                .result(sessionMapper.toResponses(results))
                .build();
    }

    @Operation(summary = "10. Get user's assigned session")
    @SecurityRequirement(name = "admin-token")
    @GetMapping("/{username}/assignedSessions")
    public ApiResponse<List<TestSession>> getUserAssignedSessions(@PathVariable("username") String username) {
        return ApiResponse.<List<TestSession>>builder()
                .result(service.getAssignedSessions(username))
                .build();
    }

    @Operation(summary = "11. Get user's taken tests")
    @SecurityRequirement(name = "admin-token")
    @GetMapping("/{username}/takenTests")
    public ApiResponse<List<ResultResponse>> getUserTakenTests(@PathVariable("username") String username) {
        return ApiResponse.<List<ResultResponse>>builder()
                .result(resultMapper.toResponses(service.getTakenTests(username)))
                .build();
    }

// Put
    @Operation(summary = "12. Update user")
    @SecurityRequirement(name = "admin-token")
    @PutMapping("/{username}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String username, @RequestBody UserUpdateRequest request) {
        User user = service.updateUser(username, request);
        return ApiResponse.<UserResponse>builder()
                .result(mapper.toResponse(user))
                .build();
    }

    @Operation(summary = "13. Delete user")
    @SecurityRequirement(name = "admin-token")
    @DeleteMapping("/{username}")
    public ApiResponse<String> deleteUser(@PathVariable String username) {
        service.deleteUser(username);
        return ApiResponse.<String>builder()
                .result("UserResponse deleted")
                .build();
    }
}

