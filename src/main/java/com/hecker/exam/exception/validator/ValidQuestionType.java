package com.hecker.exam.exception.validator;

import jakarta.validation.Constraint;

import java.lang.annotation.*;

@Constraint(validatedBy = QuestionTypeValidator.class)
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidQuestionType {
    String message() default "Invalid question type";

    Class<?>[] groups() default {};

    Class<?>[] payload() default {};
}
