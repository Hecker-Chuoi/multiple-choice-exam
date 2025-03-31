package com.hecker.exam.entity.enums;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hecker.exam.dto.response.StatusCode;
import com.hecker.exam.exception.AppException;

public enum TakingStatus {
    NOT_STARTED,
    IN_PROGRESS,
    COMPLETED;

    @JsonCreator
    public static TakingStatus fromString (String status){
        try{
            return TakingStatus.valueOf(status.toUpperCase());
        } catch (Exception e) {
            throw new AppException(StatusCode.TAKING_STATUS_INVALID);
        }
    }

    @JsonValue
    public String toJson(){
        return this.name();
    }
}
