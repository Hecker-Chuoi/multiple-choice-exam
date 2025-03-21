package com.hecker.exam.controller;

import com.hecker.exam.dto.request.TestCreationRequest;
import com.hecker.exam.entity.Question;
import com.hecker.exam.entity.Test;
import com.hecker.exam.service.TestService;
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

    @PostMapping
    public Test createTest(@RequestBody TestCreationRequest request){
        return service.createTest(request);
    }

    @PostMapping("/{testId}/questions")
    public Test setQuestions(@PathVariable long testId, @RequestBody List<Question> questions){
        return service.setQuestions(testId, questions);
    }

    @GetMapping("/{testId}")
    public Test getTest(@PathVariable long testId){
        return service.getTest(testId);
    }

    @GetMapping("/all")
    public List<Test> getAllTests(){
        return service.getAllTests();
    }
}
