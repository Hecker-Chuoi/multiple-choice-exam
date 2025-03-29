package com.hecker.exam.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hecker.exam.dto.response.StatusCode;
import com.hecker.exam.exception.AppException;

public enum Type {
    SOLDIER("Chiến sĩ"),
    OFFICER("Sĩ quan"),
    PROFESSIONAL("Chuyên nghiệp");

    String name;

    Type(String name) {
        this.name = name;
    }

    @JsonCreator
    public static Type fromString(String value) {
        try{
            return Type.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new AppException(StatusCode.USER_TYPE_INVALID);
        }
    }

    @JsonValue
    public String toJson() {
        return this.name;
    }
}
