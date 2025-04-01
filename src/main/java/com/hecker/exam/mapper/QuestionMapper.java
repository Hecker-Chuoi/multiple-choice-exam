package com.hecker.exam.mapper;

import com.hecker.exam.dto.request.test.QuestionCreationRequest;
import com.hecker.exam.dto.response.AnswerResponse;
import com.hecker.exam.dto.response.QuestionResponse;
import com.hecker.exam.entity.Answer;
import com.hecker.exam.entity.Question;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface QuestionMapper {
    Question toQuestion(QuestionCreationRequest request);
    @Mapping(target = "answers", source = "answers", qualifiedByName = "toAnswerResponses")
    QuestionResponse toResponse(Question question);
    @Mapping(target = "answers", source = "answers", qualifiedByName = "toAnswerResponses")
    List<QuestionResponse> toResponses(List<Question> question);

    @Named("toAnswerResponses")
    List<AnswerResponse> toAnswerResponses(List<Answer> answers);
}
