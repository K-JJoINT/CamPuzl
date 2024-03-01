package com.JJoINT.CamPuzl.global.validator;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MultipleOfHalfValidator implements ConstraintValidator<MultipleOfHalf, Double> {

    @Override
    public void initialize(MultipleOfHalf constraintAnnotation) {
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        return value % 0.5 == 0;
    }
}