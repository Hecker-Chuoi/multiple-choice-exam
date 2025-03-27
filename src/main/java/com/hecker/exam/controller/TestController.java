package com.hecker.exam.controller;

import com.hecker.exam.dto.request.test.QuestionCreationRequest;
import com.hecker.exam.dto.request.test.TestCreationRequest;
import com.hecker.exam.dto.response.ApiResponse;
import com.hecker.exam.dto.response.TestResponse;
import com.hecker.exam.entity.Question;
import com.hecker.exam.service.TestService;
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
public class TestController {
    TestService service;

    // create new test
    @PostMapping
    public ApiResponse<TestResponse> createTest(@RequestBody TestCreationRequest request){
        return ApiResponse.<TestResponse>builder()
                .result(service.createTest(request))
                .build();
    }

    // get test by id
    @GetMapping("/{testId}")
    public ApiResponse<TestResponse> getTest(@PathVariable long testId){
        return ApiResponse.<TestResponse>builder()
                .result(service.getTest(testId))
                .build();
    }

    // get valid tests
    @GetMapping("/valid")
    public ApiResponse<List<TestResponse>> getValidTests(@RequestParam(defaultValue = "False") boolean isDeleted){
        return ApiResponse.<List<TestResponse>>builder()
                .result(service.getTestByDeleted(isDeleted))
                .build();
    }

    // get all tests
    @GetMapping("/all")
    public ApiResponse<List<TestResponse>> getAllTests(){
        return ApiResponse.<List<TestResponse>>builder()
                .result(service.getAllTests())
                .build();
    }

    // update test by id
    @PutMapping("/{testId}")
    public ApiResponse<TestResponse> updateTest(@PathVariable long testId, @RequestBody TestCreationRequest request){
        return ApiResponse.<TestResponse>builder()
                .result(service.updateTest(testId, request))
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
        return ApiResponse.<TestResponse>builder()
                .result(service.setQuestions(testId, requests))
                .build();
    }
}
