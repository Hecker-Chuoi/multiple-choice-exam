package com.hecker.exam.controller;

import com.hecker.exam.dto.request.auth.UserCreationRequest;
import com.hecker.exam.dto.request.auth.UserUpdateRequest;
import com.hecker.exam.dto.response.ApiResponse;
import com.hecker.exam.dto.response.UserResponse;
import com.hecker.exam.mapper.UserMapper;
import com.hecker.exam.service.UserService;
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
public class UserController {
    UserService service;
    UserMapper mapper;

    @PostMapping("/one")
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<UserResponse>builder()
                        .result(service.createUser(request))
                        .build();
    }

    @PostMapping("/many")
    public ApiResponse<List<UserResponse>> createUsers(@RequestParam("file") MultipartFile file) throws IOException {
        return ApiResponse.<List<UserResponse>>builder()
                .result(service.createUsersFromExcel(file))
                .build();
    }

    @GetMapping("/{username}")
    public ApiResponse<UserResponse> getUserByUsername(@PathVariable String username) {
        return ApiResponse.<UserResponse>builder()
                .result(mapper.toResponse(service.getUserByUsername(username)))
                .build();
    }

    @GetMapping("/myinfo")
    public ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(mapper.toResponse(service.getMyInfo()))
                .build();
    }

    @GetMapping("/candidates")
    public ApiResponse<List<UserResponse>> getCandidates() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(service.getCandidates())
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<List<UserResponse>> getAllUsers() {
        return ApiResponse.<List<UserResponse>>builder()
                .result(service.getAllUsers())
                .build();
    }

    @PutMapping("/{username}")
    public ApiResponse<UserResponse> updateUser(@PathVariable String username, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<UserResponse>builder()
                .result(service.updateUser(username, request))
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
