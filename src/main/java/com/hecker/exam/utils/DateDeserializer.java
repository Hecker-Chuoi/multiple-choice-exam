package com.hecker.exam.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.hecker.exam.dto.response.StatusCode;
import com.hecker.exam.exception.AppException;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DateDeserializer extends JsonDeserializer<LocalDate> {
    @Override
    public LocalDate deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String text = jsonParser.getText();
        try{
            return LocalDate.parse(text, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        } catch (Exception e){
            throw new AppException(StatusCode.DATE_FORMAT_INVALID);
        }
    }
}
