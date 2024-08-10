package com.hospital.management.payload;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class PasswordChangeDto {
    @NotBlank(message = "Enter your old password")
    private String oldPassword;

    @NotBlank(message = "Enter your new password")
    private String newPassword;

    @NotBlank(message = "Enter your confirm password")
    private String confirmNewPassword;
}
