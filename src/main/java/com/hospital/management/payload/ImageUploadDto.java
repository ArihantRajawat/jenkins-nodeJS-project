package com.hospital.management.payload;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ImageUploadDto {
    @NotNull(message = "Image is mandatory")
    private MultipartFile image;
}
