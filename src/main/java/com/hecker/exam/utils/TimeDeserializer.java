package com.hecker.exam.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.hecker.exam.dto.response.StatusCode;
import com.hecker.exam.exception.AppException;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TimeDeserializer extends JsonDeserializer<LocalDateTime> {
    @Override
    public LocalDateTime deserialize(JsonParser p, DeserializationContext context){
        try{
            return LocalDateTime.parse(p.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
        }
        catch(Exception e){
            throw new AppException(StatusCode.TIME_FORMAT_INVALID);
        }
    }
}
