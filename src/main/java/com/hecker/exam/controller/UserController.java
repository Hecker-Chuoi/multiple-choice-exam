package com.hecker.exam.controller;

import com.hecker.exam.dto.request.user.UserCreationRequest;
import com.hecker.exam.dto.request.user.UserUpdateRequest;
import com.hecker.exam.dto.response.ApiResponse;
import com.hecker.exam.dto.response.ResultResponse;
import com.hecker.exam.dto.response.UserResponse;
import com.hecker.exam.entity.TestSession;
import com.hecker.exam.entity.User;
import com.hecker.exam.mapper.CandidateResultMapper;
import com.hecker.exam.mapper.UserMapper;
import com.hecker.exam.service.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "bearer-jwt")
public class UserController {
    UserService service;
    UserMapper mapper;
    CandidateResultMapper resultMapper;

    @PostMapping("/one")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        User res = service.createUser(request);
        return ApiResponse.<UserResponse>builder()
                        .result(mapper.toResponse(res))
                        .build();
    }

    @PostMapping("/many")
    public ApiResponse<List<UserResponse>> createUsers(@RequestParam("file") MultipartFile file) throws IOException {
        List<User> users = service.createUsersFromExcel(file);
        return ApiResponse.<List<UserResponse>>builder()
                .result(mapper.toResponses(users))
                .build();
    }

    @GetMapping("/{username}")
    public ApiResponse<UserResponse> getUserByUsername(@PathVariable String username) {
        User user = service.getUserByUsername(username);
        return ApiResponse.<UserResponse>builder()
                .result(mapper.toResponse(user))
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<List<UserResponse>> getUsers() {
        List<User> users = service.getUsers();
        return ApiResponse.<List<UserResponse>>builder()
                .result(mapper.toResponses(users))
                .build();
    }

    @GetMapping("/type")
    public ApiResponse<List<UserResponse>> getUsersByType(@RequestBody String[] type) {
        List<User> users = service.getUsersByTypes(type);
        return ApiResponse.<List<UserResponse>>builder()
                .result(mapper.toResponses(users))
                .build();
    }

    @GetMapping("/myInfo")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(mapper.toResponse(service.getMyInfo()))
                .build();
    }

    @GetMapping("/myInfo/takenTests")
    public ApiResponse<List<ResultResponse>> getMyTakenTests() {
        return ApiResponse.<List<ResultResponse>>builder()
                .result(resultMapper.toResponses(service.getTakenTests()))
                .build();
    }

    @GetMapping("/myInfo/assignedSessions")
    public ApiResponse<List<TestSession>> getMyAssignedSessions() {
        return ApiResponse.<List<TestSession>>builder()
                .result(service.getAssignedSessions())
                .build();
    }

    @GetMapping("/{username}/assignedSessions")
    public ApiResponse<List<TestSession>> getUserAssignedSessions(@PathVariable("username") String username) {
        return ApiResponse.<List<TestSession>>builder()
                .result(service.getAssignedSessions(username))
                .build();
    }

    @GetMapping("/{username}/takenTests")
    public ApiResponse<List<ResultResponse>> getUserTakenTests(@PathVariable("username") String username) {
        return ApiResponse.<List<ResultResponse>>builder()
                .result(resultMapper.toResponses(service.getTakenTests(username)))
                .build();
    }

    @PutMapping("/{username}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String username, @RequestBody UserUpdateRequest request) {
        User user = service.updateUser(username, request);
        return ApiResponse.<UserResponse>builder()
                .result(mapper.toResponse(user))
                .build();
    }

    @DeleteMapping("/{username}")
    public ApiResponse<String> deleteUser(@PathVariable String username) {
        service.deleteUser(username);
        return ApiResponse.<String>builder()
                .result("UserResponse deleted")
                .build();
    }
}
