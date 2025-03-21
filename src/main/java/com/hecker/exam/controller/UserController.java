package com.hecker.exam.controller;

import com.hecker.exam.dto.request.UserCreationRequest;
import com.hecker.exam.dto.request.UserUpdateRequest;
import com.hecker.exam.dto.response.ApiResponse;
import com.hecker.exam.entity.User;
import com.hecker.exam.service.UserService;
import jakarta.validation.Valid;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @PostMapping("/one")
    public ApiResponse<User> createUser(@RequestBody @Valid UserCreationRequest request) {
        return ApiResponse.<User>builder()
                        .result(service.createUser(request))
                        .build();
    }

    @PostMapping("/many")
    public ApiResponse<List<User>> createUsers(@RequestParam("file") MultipartFile file) throws IOException {
        return ApiResponse.<List<User>>builder()
                .result(service.createUsersFromExcel(file))
                .build();
    }

    @GetMapping("/{username}")
    public ApiResponse<User> getUserByUsername(@PathVariable String username) {
        return ApiResponse.<User>builder()
                .result(service.getUserByUsername(username))
                .build();
    }

    @GetMapping("/myinfo")
    public ApiResponse<User> getMyInfo() {
        return ApiResponse.<User>builder()
                .result(service.getMyInfo())
                .build();
    }

    @GetMapping("/candidates")
    public ApiResponse<List<User>> getCandidates() {
        return ApiResponse.<List<User>>builder()
                .result(service.getCandidates())
                .build();
    }

    @GetMapping
    public ApiResponse<List<User>> getAllUsers() {
        return ApiResponse.<List<User>>builder()
                .result(service.getAllUsers())
                .build();
    }

    @PutMapping("/{username}")
    public ApiResponse<User> updateUser(@PathVariable String username, @RequestBody UserUpdateRequest request) {
        return ApiResponse.<User>builder()
                .result(service.updateUser(username, request))
                .build();
    }

    @DeleteMapping("/{username}")
    public ApiResponse<String> deleteUser(@PathVariable String username) {
        service.deleteUser(username);
        return ApiResponse.<String>builder()
                .result("User deleted")
                .build();
    }
}
