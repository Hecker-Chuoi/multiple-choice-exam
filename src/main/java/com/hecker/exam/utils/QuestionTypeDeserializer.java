package com.hecker.exam.utils;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.hecker.exam.dto.response.StatusCode;
import com.hecker.exam.entity.enums.QuestionType;
import com.hecker.exam.exception.AppException;

import java.io.IOException;

@Deprecated
public class QuestionTypeDeserializer extends JsonDeserializer<QuestionType> {
    @Override
    public QuestionType deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException, JacksonException {
        String value = jsonParser.getValueAsString();

        if(value == null || value.trim().isEmpty()){
            throw new AppException(StatusCode.QUESTION_TYPE_INVALID);
        }
        try{
            return QuestionType.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e){
            throw new AppException(StatusCode.QUESTION_TYPE_INVALID);
        }
    }
}
