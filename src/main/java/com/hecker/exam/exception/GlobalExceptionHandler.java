package com.hecker.exam.exception;

import com.hecker.exam.dto.response.ApiResponse;
import com.hecker.exam.dto.response.StatusCode;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.method.annotation.HandlerMethodValidationException;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<ApiResponse> fallbackExceptionHandling(Exception exception) {
        exception.printStackTrace();

        Throwable cause = getRootCause(exception);
        if(cause instanceof AppException appException) {
            return appExceptionHandling(appException);
        }

        return appExceptionHandling(new AppException(StatusCode.UNCATEGORIZED));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse> validationExceptionHandling(MethodArgumentNotValidException exception) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                ApiResponse.builder()
                        .statusCode(StatusCode.METHOD_ARGUMENT_NOT_VALID.getCode())
                        .message(StatusCode.METHOD_ARGUMENT_NOT_VALID.getMessage())
                        .result(exception.getFieldError().getDefaultMessage())
                        .build()
        );
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationException(HandlerMethodValidationException ex) {
        Map<String, String> errors = new HashMap<>();
        ex.getAllErrors().forEach(error -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });

        return ResponseEntity.badRequest().body(ApiResponse.<Map<String, String>>builder()
                        .statusCode(StatusCode.METHOD_ARGUMENT_NOT_VALID.getCode())
                        .message(StatusCode.METHOD_ARGUMENT_NOT_VALID.getMessage())
                        .result(errors)
                .build()
        );
    }

    private Throwable getRootCause(Throwable throwable) {
        if(throwable.getCause() == null)
            return throwable;
        return getRootCause(throwable.getCause());
    }

    @ExceptionHandler(value = AppException.class)
    public ResponseEntity<ApiResponse> appExceptionHandling(AppException exception) {
        exception.printStackTrace();
        return ResponseEntity.badRequest().body(
                ApiResponse.<AppException>builder()
                        .statusCode(exception.getStatusCode().getCode())
                        .message(exception.getStatusCode().getMessage())
                        .build()
        );
    }
}
