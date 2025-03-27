package com.hecker.exam.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.hecker.exam.dto.response.StatusCode;
import com.hecker.exam.exception.AppException;

import java.io.IOException;
import java.time.Duration;

public class DurationDeserializer extends JsonDeserializer<Duration> {
    @Override
    public Duration deserialize(JsonParser p, DeserializationContext context) {
        try{
            return Duration.ofMinutes(Long.parseLong(p.getText()));
        }
        catch(Exception e){
            throw new AppException(StatusCode.DURATION_FORMAT_INVALID);
        }
    }
}
