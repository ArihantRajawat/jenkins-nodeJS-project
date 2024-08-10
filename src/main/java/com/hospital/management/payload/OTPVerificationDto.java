package com.hospital.management.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class OTPVerificationDto {
    @NotBlank(message = "Please enter your valid OTP")
    private String otp;
    private String email;
}
