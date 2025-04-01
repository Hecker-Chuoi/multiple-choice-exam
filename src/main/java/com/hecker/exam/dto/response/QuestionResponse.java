package com.hecker.exam.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hecker.exam.entity.Answer;
import com.hecker.exam.entity.enums.QuestionType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuestionResponse {
    long questionId;
    String questionText;
    String explainText;

    @Enumerated(EnumType.STRING)
    QuestionType questionType;

    List<AnswerResponse> answers;
}
