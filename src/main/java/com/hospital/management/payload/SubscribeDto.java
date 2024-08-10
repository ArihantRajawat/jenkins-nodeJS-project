package com.hospital.management.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class SubscribeDto {

    private Long id;

    @NotBlank(message = "Please enter your email")
    @Email(message = "Invalid email")
    private String email;
}
