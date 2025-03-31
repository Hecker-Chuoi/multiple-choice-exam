package com.hecker.exam.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.hecker.exam.entity.CandidateAnswer;
import com.hecker.exam.entity.enums.TakingStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ResultResponse {
    long testResultId;
    @JsonFormat(pattern = "2.2f", shape = JsonFormat.Shape.STRING)
    float timeTaken;
    @JsonFormat(pattern = "2.2f", shape = JsonFormat.Shape.STRING)
    float score;
    @Enumerated(EnumType.STRING)
    TakingStatus status;
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss", shape = JsonFormat.Shape.STRING)
    LocalDateTime submitAt;

    Long candidateId;
    Long sessionId;
    List<CandidateAnswer> candidateAnswered;
}
