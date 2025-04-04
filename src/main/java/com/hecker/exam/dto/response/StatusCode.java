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
    USER_NOT_FOUND(106, "User not found"),
    INVALID_NUMBERS_ANSWER(107, "Single choice question must have only one correct answer, multiple choice question must have at least one correct answer"),
    QUESTION_TYPE_INVALID(108, "Question type must be one of the following: " + "SINGLE_CHOICE, MULTIPLE_CHOICES"),
    TIME_FORMAT_INVALID(109, "Time format must be 'dd/MM/yyyy HH:mm'"),
    DURATION_FORMAT_INVALID(110, "Duration format must be 'mm'"),
    CANDIDATE_NOT_FOUND(111, "Candidate not found"),
    SESSION_ACCESS_PERMISSION(112, "You don't have permission to access this session"),
    SESSION_NOT_STARTED(113, "Session not yet started"),
    SESSION_EXPIRED(114, "Session has expired"),
    START_THE_TEST(115, "Please start the test first"),
    QUESTION_NOT_FOUND(116, "Question not found"),
    ALREADY_STARTED(117, "You have already started the test"),
    TEST_COMPLETED(118, "Test has been completed"),
    UNCATEGORIZED(999, "Uncategorized error");

    int code;
    String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
