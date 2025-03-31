package com.hecker.exam.dto.request.test;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestCreationRequest {
    @NotBlank
    String testName;
    @NotBlank
    String subject;
}
