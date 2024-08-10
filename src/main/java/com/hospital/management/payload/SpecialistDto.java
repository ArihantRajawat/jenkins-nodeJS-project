package com.hospital.management.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SpecialistDto {
    private Integer id;
    @NotBlank(message = "Specialist name mandatory")
    private String name;
    @Size(min = 0, max = 500, message = "Maximum 500 character allowed")
    private String description;
}
