package com.hecker.exam.controller;

import com.hecker.exam.dto.request.answer.CandidateAnswerRequest;
import com.hecker.exam.dto.response.ApiResponse;
import com.hecker.exam.dto.response.ResultResponse;
import com.hecker.exam.dto.response.TestResponse;
import com.hecker.exam.entity.CandidateResult;
import com.hecker.exam.entity.Question;
import com.hecker.exam.entity.Test;
import com.hecker.exam.mapper.CandidateResultMapper;
import com.hecker.exam.mapper.TestMapper;
import com.hecker.exam.service.TakingTestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.List;

@RestController
@RequestMapping("/taking-test")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "user-token")
public class TakingTestController {
    TakingTestService service;
    CandidateResultMapper mapper;
    TestMapper testMapper;

    @GetMapping("/{sessionId}/test")
    public ApiResponse<TestResponse> getTest(@PathVariable("sessionId") Long sessionId){
        Test result = service.getTest(sessionId);
        return ApiResponse.<TestResponse>builder()
                .result(testMapper.toResponse(result))
                .build();
    }

    @GetMapping("/{sessionId}/questions")
    public ApiResponse<List<Question>> getQuestions(@PathVariable("sessionId") Long sessionId){
        List<Question> result = service.getQuestions(sessionId);
        return ApiResponse.<List<Question>>builder()
                .result(result)
                .build();
    }

    @PostMapping("/{sessionId}/start")
    public ApiResponse<String> startTest(@PathVariable("sessionId") Long sessionId){
        service.startTest(sessionId);
        return ApiResponse.<String>builder()
                .message("Test started")
                .build();
    }

    @PostMapping("/{sessionId}/save-progress")
    public ApiResponse<String> saveAnswer(@PathVariable("sessionId") Long sessionId, @RequestBody List<@Valid CandidateAnswerRequest> requests){
        service.saveProgress(sessionId, requests);
        return ApiResponse.<String>builder()
                .message("Answers saved")
                .build();
    }

    @PostMapping("/{sessionId}/submit")
    public ApiResponse<String> submitTest(@PathVariable("sessionId") Long sessionId){
        service.submitTest(sessionId);
        return ApiResponse.<String>builder()
                .message("Test submitted")
                .build();
    }

    @GetMapping("/{sessionId}/result")
    public ApiResponse<ResultResponse> getResult(@PathVariable("sessionId") Long sessionId){
        return ApiResponse.<ResultResponse>builder()
                .result(mapper.toResponse(service.getResult(sessionId)))
                .build();
    }
}
