package com.hecker.exam.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ApiResponse <T>{
    @Builder.Default
    int statusCode = StatusCode.SUCCESS.getCode();
    @Builder.Default
    String message = StatusCode.SUCCESS.getMessage();
    T result;
}
