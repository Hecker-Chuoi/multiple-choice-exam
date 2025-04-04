package com.hecker.exam.mapper;

import com.hecker.exam.dto.request.test.AnswerCreationRequest;
import com.hecker.exam.dto.request.test.QuestionCreationRequest;
import com.hecker.exam.dto.request.test.TestCreationRequest;
import com.hecker.exam.dto.response.TestResponse;
import com.hecker.exam.entity.Answer;
import com.hecker.exam.entity.Question;
import com.hecker.exam.entity.Test;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TestMapper{
    Test toTest(TestCreationRequest request);
    void updateTest(@MappingTarget Test test, TestCreationRequest request);
    Question toQuestion(QuestionCreationRequest request);
    List<Answer> toAnswers(List<AnswerCreationRequest> requests);
    TestResponse toResponse(Test test);
    List<TestResponse> toResponses(List<Test> tests);
}
