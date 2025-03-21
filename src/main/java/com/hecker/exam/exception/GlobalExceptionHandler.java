package com.hecker.exam.exception;

import com.hecker.exam.dto.response.ApiResponse;
import com.hecker.exam.dto.response.StatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse<Object>> fallbackExceptionHandling(Exception exception) {
        exception.printStackTrace();
        return ResponseEntity.badRequest().body(ApiResponse.builder()
                .statusCode(StatusCode.UNCATEGORIZED.getCode())
                .message(StatusCode.UNCATEGORIZED.getMessage())
                .build()
        );
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> validationExceptionHandling(MethodArgumentNotValidException exception) {
        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .statusCode(StatusCode.METHOD_ARGUMENT_NOT_VALID.getCode())
                        .message(StatusCode.METHOD_ARGUMENT_NOT_VALID.getMessage())
                        .result(exception.getFieldError().getDefaultMessage())
                        .build()
        );
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse> appExceptionHandling(AppException exception) {
        exception.printStackTrace();
        return ResponseEntity.badRequest().body(
                ApiResponse.builder()
                        .statusCode(exception.getStatusCode().getCode())
                        .message(exception.getStatusCode().getMessage())
                        .build()
        );
    }
}
