package com.nnk.springboot.services.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ValidPasswordValidator implements ConstraintValidator<ValidPassword, String> {

    private final String pattern = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[@$!%*#?&./])[A-Za-z0-9@$!%*#?&./]{8,}$";

    @Override
    public void initialize(ValidPassword constraintAnnotation) {

    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s.matches(pattern);
    }
}