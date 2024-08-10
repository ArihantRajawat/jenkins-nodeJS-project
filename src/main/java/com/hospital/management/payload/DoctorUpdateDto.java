package com.hospital.management.payload;

import org.springframework.web.multipart.MultipartFile;

import com.hospital.management.customannotation.Numeric;
import com.hospital.management.entity.Specialist;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class DoctorUpdateDto {
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
    @Email(message = "Invalid email")
    private String email;

    @NotNull(message = "Specialization is mandatory")
    private Integer specializationId;

    private Specialist specialization;

    @NotBlank(message = "Experience is mandatory")
    private String experience;

    @NotBlank(message = "Qualification is mandatory")
    private String qualification;

    @NotNull(message = "ConsultationFee is mandatory")
    private Integer consultationFee;

    @NotNull(message = "Licence number is mandatory")
    private String licenceNo;

    private String about;

    private String imageUrl;

    @NotNull(message = "Image is mandatory")
    private MultipartFile image;
}
