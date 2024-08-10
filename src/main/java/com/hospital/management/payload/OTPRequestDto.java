package com.hospital.management.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OTPRequestDto {

    @NotBlank(message = "Please enter your email")
    private String email;
}
