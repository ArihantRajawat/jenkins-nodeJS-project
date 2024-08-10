package com.hospital.management.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class AuthRequestDto {
    @NotBlank(message = "Email or Username is mandatory")
    private String username;
    @NotBlank(message = "Password is mandatory")
    private String password;
}
