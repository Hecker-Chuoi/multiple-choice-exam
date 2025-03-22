package com.hecker.exam.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hecker.exam.dto.response.StatusCode;
import com.hecker.exam.exception.AppException;

public enum QuestionType {
    SINGLE_CHOICE,
    MULTIPLE_CHOICES;

//    @JsonCreator
//    public static QuestionType fromString(String value) {
//        try{
//            return QuestionType.valueOf(value.toUpperCase());
//        } catch (IllegalArgumentException e) {
//            throw new AppException(StatusCode.QUESTION_TYPE_INVALID);
//        }
//    }
//
//    @JsonValue
//    public String toJson() {
//        return name();
//    }
}
