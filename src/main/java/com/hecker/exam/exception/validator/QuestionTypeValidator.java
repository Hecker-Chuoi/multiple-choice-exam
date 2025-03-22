package com.hecker.exam.exception.validator;

import com.hecker.exam.entity.enums.QuestionType;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class QuestionTypeValidator implements ConstraintValidator<ValidQuestionType, String> {
    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        for(QuestionType questionType : QuestionType.values()) {
            if(questionType.name().equals(s)) {
                return true;
            }
        }
        return false;
    }
}
