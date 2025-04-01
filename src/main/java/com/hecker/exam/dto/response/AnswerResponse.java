package com.hecker.exam.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.validator.constraints.Length;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerResponse {
    long answerId;
    String answerText;
    Boolean isCorrect;
}
