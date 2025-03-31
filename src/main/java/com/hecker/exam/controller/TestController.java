package com.hecker.exam.controller;

import com.hecker.exam.dto.request.test.QuestionCreationRequest;
import com.hecker.exam.dto.request.test.TestCreationRequest;
import com.hecker.exam.dto.response.ApiResponse;
import com.hecker.exam.dto.response.TestResponse;
import com.hecker.exam.entity.Question;
import com.hecker.exam.entity.Test;
import com.hecker.exam.mapper.TestMapper;
import com.hecker.exam.service.TestService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/test")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@SecurityRequirement(name = "admin-token")
public class TestController {
    TestService service;
    TestMapper mapper;

    // create new test
    @PostMapping
    public ApiResponse<TestResponse> createTest(@RequestBody TestCreationRequest request){
        Test result = service.createTest(request);
        return ApiResponse.<TestResponse>builder()
                .result(mapper.toResponse(result))
                .build();
    }

    // get test by id
    @GetMapping("/{testId}")
    public ApiResponse<TestResponse> getTest(@PathVariable long testId){
        Test result = service.getTest(testId);
        return ApiResponse.<TestResponse>builder()
                .result(mapper.toResponse(result))
                .build();
    }

    // get valid tests
    @GetMapping("/valid")
    public ApiResponse<List<TestResponse>> getValidTests(){
        List<Test> result = service.getValidTests();
        return ApiResponse.<List<TestResponse>>builder()
                .result(mapper.toResponses(result))
                .build();
    }

    // get all tests
    @GetMapping("/all")
    public ApiResponse<List<TestResponse>> getAllTests(){
        List<Test> result = service.getAllTests();
        return ApiResponse.<List<TestResponse>>builder()
                .result(mapper.toResponses(result))
                .build();
    }

    // update test by id
    @PutMapping("/{testId}")
    public ApiResponse<TestResponse> updateTest(@PathVariable long testId, @RequestBody TestCreationRequest request){
        Test result = service.updateTest(testId, request);
        return ApiResponse.<TestResponse>builder()
                .result(mapper.toResponse(result))
                .build();
    }

    // delete test by id
    @DeleteMapping("/{testId}")
    public ApiResponse<String> deleteTest(@PathVariable long testId){
        service.deleteTest(testId);
        return ApiResponse.<String>builder()
                .result("Test deleted successfully")
                .build();
    }

    @PostMapping("/{testId}/restore")
    public ApiResponse<String> restoreTest(@PathVariable("testId") long testId){
        service.restoreTest(testId);
        return ApiResponse.<String>builder()
                .result("Test restored successfully")
                .build();
    }

    @GetMapping("/{testId}/questions")
    public ApiResponse<List<Question>> getQuestions(@PathVariable("testId") long testId){
        return ApiResponse.<List<Question>>builder()
                .result(service.getTest(testId).getQuestions())
                .build();
    }

    // set/update questions by updating entire list
    @PostMapping("/{testId}/questions")
    public ApiResponse<TestResponse> setQuestions(@PathVariable long testId, @RequestBody List<@Valid QuestionCreationRequest> requests){
        Test result = service.setQuestions(testId, requests);
        return ApiResponse.<TestResponse>builder()
                .result(mapper.toResponse(result))
                .build();
    }
}
