package com.hospital.management.payload;

import com.hospital.management.customannotation.Numeric;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.Transient;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UserDto {
    private Long id;

    @NotBlank(message = "Firstname is mandatory")
    private String firstname;

    @NotBlank(message = "Lastname is mandatory")
    private String lastname;

    @NotBlank(message = "Phone number is mandatory")
    @Pattern(regexp = "[0-9]{10}", message = "Phone number must be 10 digits")
    @Numeric(message = "Invalid phone number")
    @Positive(message = "Invalid phone number")
    private String phone;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Invalid email format")
    private String email;

    @NotBlank(message = "Password is mandatory")
    private String password;

    @NotBlank(message = "Confirm password is mandatory")
    @Transient
    private String confirmPassword;

    private MultipartFile image;

    private String imageUrl;

    @Transient
    @AssertTrue(message = "Please check the term & condition")
    private boolean check;
}
