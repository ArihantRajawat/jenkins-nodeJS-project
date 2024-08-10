package com.hospital.management.customannotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

@Documented
@Target({ ElementType.METHOD, ElementType.FIELD })
@Constraint(validatedBy = NotZeroValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface NotZero {
    String message() default "Can not be zero.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
