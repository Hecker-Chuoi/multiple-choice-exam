package com.hecker.exam.dto.request.test;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AnswerCreationRequest {
    @NotBlank(message = "Answer text is required")
    String answerText;
    @NotNull(message = "Is correct is required")
    Boolean isCorrect;
}
