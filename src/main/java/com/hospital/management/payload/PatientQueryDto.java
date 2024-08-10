package com.hospital.management.payload;

import com.hospital.management.customannotation.Numeric;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class PatientQueryDto {
    private int id;
    @NotBlank(message = "Name is mandatory")
    private String name;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "[0-9]{10}", message = "Phone number must be 10 digits")
    @Numeric(message = "Invalid phone number")
    @Positive(message = "Invalid phone number")
    private String phone;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email")
    private String email;

    @NotBlank(message = "Please type your query message")
    private String query;
}
