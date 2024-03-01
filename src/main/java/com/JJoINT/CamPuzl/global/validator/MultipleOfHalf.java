package com.JJoINT.CamPuzl.global.validator;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Documented
@Constraint(validatedBy = MultipleOfHalfValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface MultipleOfHalf {
    String message() default "Value must be a multiple of 0.5";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}