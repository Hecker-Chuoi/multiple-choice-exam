package com.hecker.exam.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.hecker.exam.entity.Test;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.Duration;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SessionCreationRequest {
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")
    LocalDateTime startTime;
    @JsonFormat(pattern = "HH:mm")
    Duration timeLimit;
    long testId;
}
