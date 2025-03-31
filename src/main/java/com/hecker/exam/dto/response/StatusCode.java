package com.hecker.exam.dto.response;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

@Getter
@Schema(description = "Status code and message", enumAsRef = true)
public enum StatusCode {
    SUCCESS(0, "Success"),
    UNAUTHENTICATED(100, "Unauthenticated"),
    USERNAME_ALREADY_EXISTS(101, "Username already exists"),
    USER_NOT_FOUND(102, "User not found"),
    USER_TYPE_INVALID(103, "User type must be one of the following: " + "SOLDIER, OFFICER, PROFESSIONAL"),

    METHOD_ARGUMENT_NOT_VALID(200, "Method argument not valid"),
    DURATION_FORMAT_INVALID(201, "Duration format must be 'mm'"),
    DATE_FORMAT_INVALID(204, "Date format must be 'dd/MM/yyyy'"),
    TIME_FORMAT_INVALID(202, "Time format must be 'dd/MM/yyyy HH:mm'"),
    GENDER_INVALID(203, "Gender must be MALE, FEMALE or OTHER"),
    TAKING_STATUS_INVALID(204, "Taking status must be one of the following: " + "NOT_STARTED, IN_PROGRESS, COMPLETED"),

    TEST_NOT_FOUND(300, "Test not found"),
    INVALID_NUMBERS_ANSWER(301, "Single choice question must have only one correct answer, multiple choice question must have at least one correct answer"),
    QUESTION_NOT_FOUND(302, "Question not found"),
    QUESTION_TYPE_INVALID(303, "Question type must be one of the following: " + "SINGLE_CHOICE, MULTIPLE_CHOICES"),
    CANDIDATE_NOT_FOUND(304, "Candidate not found"),
    TEST_NOT_VALID(305, "This test has no valid questions or has been deleted, please choose another test or update your test first"),

    SESSION_ACCESS_PERMISSION(400, "You don't have permission to access this session"),
    SESSION_NOT_STARTED(401, "Session not yet started"),
    SESSION_NOT_FOUND(402, "Session not found"),
    SESSION_EXPIRED(403, "Session has expired"),
    START_THE_SESSION(404, "Please start the session first"),
    ALREADY_STARTED(405, "You have already started the session"),
    TEST_COMPLETED(406, "Session has been completed"),

    UNCATEGORIZED(999, "Uncategorized error");

    final int code;
    final String message;

    StatusCode(int code, String message) {
        this.code = code;
        this.message = message;
    }
}
