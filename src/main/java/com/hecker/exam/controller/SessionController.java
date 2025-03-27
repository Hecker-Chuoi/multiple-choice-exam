package com.hecker.exam.controller;

import com.hecker.exam.dto.request.session.SessionCreationRequest;
import com.hecker.exam.dto.request.session.SessionUpdateRequest;
import com.hecker.exam.dto.response.ApiResponse;
import com.hecker.exam.dto.response.TestResponse;
import com.hecker.exam.dto.response.UserResponse;
import com.hecker.exam.entity.TestSession;
import com.hecker.exam.mapper.UserMapper;
import com.hecker.exam.service.SessionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class SessionController {
    SessionService service;
    UserMapper userMapper;

    @PostMapping
    public ApiResponse<TestSession> createSession(@RequestBody SessionCreationRequest request){
        return ApiResponse.<TestSession>builder()
                .result(service.createSession(request))
                .build();
    }

    @GetMapping("/{sessionId}")
    public ApiResponse<TestSession> getSession(@PathVariable long sessionId){
        return ApiResponse.<TestSession>builder()
                .result(service.getSession(sessionId))
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<List<TestSession>> getAllSessions(){
        return ApiResponse.<List<TestSession>>builder()
                .result(service.getAllSessions())
                .build();
    }

    @GetMapping("/{sessionId}/test")
    public ApiResponse<TestResponse> getTest(@PathVariable long sessionId){
        return ApiResponse.<TestResponse>builder()
                .result(service.getTest(sessionId))
                .build();
    }

    @GetMapping("/{sessionId}/candidates")
    public ApiResponse<List<UserResponse>> getCandidates(@PathVariable long sessionId){
        return ApiResponse.<List<UserResponse>>builder()
                .result(userMapper.toResponses(service.getCandidates(sessionId)))
                .build();
    }

    @PutMapping("/{sessionId}")
    public ApiResponse<TestSession> updateSession(@PathVariable long sessionId, @RequestBody SessionUpdateRequest request){
        return ApiResponse.<TestSession>builder()
                .result(service.updateSession(sessionId, request))
                .build();
    }

    @PutMapping("/{sessionId}/test")
    public ApiResponse<TestSession> changeTest(@PathVariable long sessionId, @RequestBody long testId){
        return ApiResponse.<TestSession>builder()
                .result(service.changeTest(sessionId, testId))
                .build();
    }

    @DeleteMapping("/{sessionId}")
    public ApiResponse<String> deleteSession(@PathVariable long sessionId){
        service.deleteSession(sessionId);
        return ApiResponse.<String>builder()
                .result("Session deleted successfully!")
                .build();
    }

    @PostMapping("/{sessionId}/candidates")
    public ApiResponse<String> candidateAssign(@PathVariable long sessionId, @RequestBody List<String> candidateUsernames){
        return service.candidateAssign(sessionId, candidateUsernames);
    }
}
