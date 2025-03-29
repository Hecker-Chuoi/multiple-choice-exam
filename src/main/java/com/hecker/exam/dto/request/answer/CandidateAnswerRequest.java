package com.hecker.exam.dto.request.answer;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidateAnswerRequest {
    String answerChosen; // 0 for un selected, 1 for selected
    @NotNull
    Long questionId;
}
