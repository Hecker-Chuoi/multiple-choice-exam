package com.hecker.exam.controller;

import com.hecker.exam.dto.request.session.SessionCreationRequest;
import com.hecker.exam.dto.request.session.SessionUpdateRequest;
import com.hecker.exam.dto.response.*;
import com.hecker.exam.entity.CandidateResult;
import com.hecker.exam.entity.Question;
import com.hecker.exam.entity.Test;
import com.hecker.exam.entity.TestSession;
import com.hecker.exam.mapper.CandidateResultMapper;
import com.hecker.exam.mapper.SessionMapper;
import com.hecker.exam.mapper.TestMapper;
import com.hecker.exam.mapper.UserMapper;
import com.hecker.exam.service.SessionService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/session")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "admin-token")
public class SessionController {
    SessionService service;
    SessionMapper sessionMapper;
    UserMapper userMapper;
    TestMapper testMapper;
    CandidateResultMapper resultMapper;

// Create
    @PostMapping
    public ApiResponse<SessionResponse> createSession(@RequestBody SessionCreationRequest request){
        TestSession session = service.createSession(request);
        return ApiResponse.<SessionResponse>builder()
                .result(sessionMapper.toResponse(session))
                .build();
    }

    @PostMapping("/{sessionId}/candidates")
    public ApiResponse<String> candidateAssignByUsernames(@PathVariable long sessionId, @RequestBody List<String> usernames){
        return service.candidateAssignByUsername(sessionId, usernames);
    }

    @PostMapping("/{sessionId}/types")
    public ApiResponse<String> candidateAssignByTypes(@PathVariable long sessionId, @RequestBody List<String> types){
        return service.candidateAssignByTypes(sessionId, types);
    }

// Read
    @GetMapping("/{sessionId}")
    public ApiResponse<SessionResponse> getSession(@PathVariable long sessionId){
        TestSession result = service.getSession(sessionId);
        return ApiResponse.<SessionResponse>builder()
                .result(sessionMapper.toResponse(result))
                .build();
    }

    @GetMapping("/all")
    public ApiResponse<List<SessionResponse>> getAllSessions(@RequestParam(required = false, defaultValue = "startTime") String sortBy){
        List<TestSession> result = service.getAllSessions(sortBy);
        return ApiResponse.<List<SessionResponse>>builder()
                .result(sessionMapper.toResponses(result))
                .build();
    }

    @GetMapping("/{sessionId}/test")
    public ApiResponse<TestResponse> getTest(@PathVariable long sessionId){
        Test test = service.getTest(sessionId);
        return ApiResponse.<TestResponse>builder()
                .result(testMapper.toResponse(test))
                .build();
    }

    @GetMapping("/{sessionId}/questions")
    public ApiResponse<List<Question>> getQuestions(@PathVariable long sessionId){
        List<Question> result = service.getQuestions(sessionId);
        return ApiResponse.<List<Question>>builder()
                .result(result)
                .build();
    }

    @GetMapping("/{sessionId}/candidates")
    public ApiResponse<List<UserResponse>> getCandidates(@PathVariable long sessionId){
        return ApiResponse.<List<UserResponse>>builder()
                .result(userMapper.toResponses(service.getCandidates(sessionId)))
                .build();
    }

    @GetMapping("/{sessionId}/results")
    public ApiResponse<List<ResultResponse>> getCandidateResults(@PathVariable long sessionId){
        List<CandidateResult> results = service.getCandidateResults(sessionId);
        return ApiResponse.<List<ResultResponse>>builder()
                .result(resultMapper.toResponses(results))
                .build();
    }

// Update
    @PutMapping("/{sessionId}")
    public ApiResponse<SessionResponse> updateSession(@PathVariable long sessionId, @RequestBody SessionUpdateRequest request){
        TestSession result = service.updateSession(sessionId, request);
        return ApiResponse.<SessionResponse>builder()
                .result(sessionMapper.toResponse(result))
                .build();
    }

    @PutMapping("/{sessionId}/test")
    public ApiResponse<SessionResponse> changeTest(@PathVariable long sessionId, @RequestBody long testId){
        TestSession result = service.changeTest(sessionId, testId);
        return ApiResponse.<SessionResponse>builder()
                .result(sessionMapper.toResponse(result))
                .build();
    }

// Delete
    @DeleteMapping("/{sessionId}")
    public ApiResponse<String> deleteSession(@PathVariable long sessionId){
        service.deleteSession(sessionId);
        return ApiResponse.<String>builder()
                .result("Session deleted successfully!")
                .build();
    }
}
