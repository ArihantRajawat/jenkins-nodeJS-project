package com.hospital.management.customannotation;

import com.hospital.management.payload.UserDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ConfirmPasswordValidator implements ConstraintValidator<ConfirmPassword, UserDto> {
    @Override
    public boolean isValid(UserDto value, ConstraintValidatorContext context) {
        UserDto userDto = (UserDto) value;
        if(userDto == null){
            return true;
        }
        return userDto.getPassword().equals(userDto.getConfirmPassword());
    }
}
