package com.hospital.management.customannotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class NumericValidator implements ConstraintValidator<Numeric, String> {

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(value == null){
            return true;
        }
        return value.matches("[0-9]+");
    }
}
