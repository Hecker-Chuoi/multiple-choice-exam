package com.hecker.exam.mapper;

import com.hecker.exam.dto.request.QuestionCreationRequest;
import com.hecker.exam.dto.request.TestCreationRequest;
import com.hecker.exam.entity.Question;
import com.hecker.exam.entity.Test;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface TestMapper{
    Test createTest(TestCreationRequest request);
    Question createQuestion(QuestionCreationRequest request);
}
