package com.hospital.management.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = NumericValidator.class)
@Documented
public @interface Numeric {
    String message() default "Must be a numeric value";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
