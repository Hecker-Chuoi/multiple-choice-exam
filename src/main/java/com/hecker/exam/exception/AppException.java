package com.hecker.exam.exception;

import com.hecker.exam.dto.response.StatusCode;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class AppException extends RuntimeException {
    private StatusCode statusCode;

    public AppException(StatusCode statusCode){
        super(statusCode.getMessage());
        this.statusCode = statusCode;
    }
}
