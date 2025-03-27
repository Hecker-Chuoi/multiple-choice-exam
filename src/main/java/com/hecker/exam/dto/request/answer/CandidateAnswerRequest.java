package com.hecker.exam.dto.request.answer;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CandidateAnswerRequest {
    String answerChosen; // 0 for un selected, 1 for selected
    long questionId;
}
