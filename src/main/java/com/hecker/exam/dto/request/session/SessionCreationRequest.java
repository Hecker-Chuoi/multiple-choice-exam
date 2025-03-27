package com.hecker.exam.dto.request.session;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.hecker.exam.utils.DurationDeserializer;
import com.hecker.exam.utils.TimeDeserializer;
import jakarta.validation.constraints.NotNull;
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
    @NotNull(message = "Test id is required")
    long testId;
    @JsonDeserialize(using = TimeDeserializer.class)
    LocalDateTime startTime;
    @JsonDeserialize(using = DurationDeserializer.class)
    Duration timeLimit;
}
