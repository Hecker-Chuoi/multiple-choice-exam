package com.hecker.exam.controller;

import com.hecker.exam.dto.request.answer.CandidateAnswerRequest;
import com.hecker.exam.dto.response.ApiResponse;
import com.hecker.exam.dto.response.ResultResponse;
import com.hecker.exam.entity.CandidateResult;
import com.hecker.exam.mapper.CandidateResultMapper;
import com.hecker.exam.service.TakingTestService;
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
public class TakingTestController {
    TakingTestService service;
    CandidateResultMapper mapper;

    @PostMapping("/{sessionId}/start")
    public ApiResponse startTest(@PathVariable("sessionId") Long sessionId){
        service.startTest(sessionId);
        return ApiResponse.builder()
                .message("Test started")
                .build();
    }

    @PostMapping("/{sessionId}/save-progress")
    public ApiResponse saveAnswer(@PathVariable("sessionId") Long sessionId, @RequestBody List<@Valid CandidateAnswerRequest> requests){
        service.saveProgress(sessionId, requests);
        return ApiResponse.builder()
                .message("Answers saved")
                .build();
    }

    @PostMapping("/{sessionId}/submit")
    public ApiResponse submitTest(@PathVariable("sessionId") Long sessionId){
        service.submitTest(sessionId);
        return ApiResponse.builder()
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
