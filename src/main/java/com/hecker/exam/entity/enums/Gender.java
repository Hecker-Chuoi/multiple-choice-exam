package com.hecker.exam.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hecker.exam.dto.response.StatusCode;
import com.hecker.exam.exception.AppException;

public enum Gender {
    MALE ("Nam"),
    FEMALE ("Nữ"),
    OTHER ("Khác");

    final String vn;

    Gender(String vn) {
        this.vn = vn;
    }

    @JsonCreator
    public static Gender fromString(String value) {
        try{
            return Gender.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(StatusCode.GENDER_INVALID);
        }
    }

    @JsonValue
    public String toJson() {
        return this.vn;
    }
}
