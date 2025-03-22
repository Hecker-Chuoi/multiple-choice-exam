package com.hecker.exam.dto.request.test;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.hecker.exam.entity.enums.QuestionType;
import com.hecker.exam.utils.QuestionTypeDeserializer;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionCreationRequest {
    @NotBlank(message = "Question text is required")
    String questionText;
    String explainText;

    @Enumerated(EnumType.STRING)
//    @NotNull(message = "Question type must be one of the following: + \"SINGLE_CHOICE, MULTIPLE_CHOICE\"")
//    @ValidQuestionType(message = "Question type must be one of the following: " +
//            "SINGLE_CHOICE, MULTIPLE_CHOICE")
    @JsonDeserialize(using = QuestionTypeDeserializer.class)
    QuestionType questionType;

    @Valid
    @JsonProperty("answers")
    List<AnswerCreationRequest> answerCreationRequests;
}
