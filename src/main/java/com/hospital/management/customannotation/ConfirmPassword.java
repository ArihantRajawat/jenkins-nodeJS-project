package com.hospital.management.customannotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConfirmPasswordValidator.class)
@Documented
public @interface ConfirmPassword {
    String message() default "Password do not match";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
