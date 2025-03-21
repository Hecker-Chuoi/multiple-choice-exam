package com.hecker.exam.dto.response;

import lombok.Getter;

@Getter
public enum StatusCode {
    SUCCESS(100, "Success"),
    UNAUTHENTICATED(101, "Unauthenticated"),
    TEST_NOT_FOUND(102, "Test not found"),
    SESSION_NOT_FOUND(103, "Session not found"),
    METHOD_ARGUMENT_NOT_VALID(104, "Method argument not valid"),
    USERNAME_ALREADY_EXISTS(105, "Username already exists"),
    UNCATEGORIZED(999, "Uncategorized error");

    int code;
    String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
