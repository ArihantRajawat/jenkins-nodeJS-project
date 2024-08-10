package com.hospital.management.customannotation;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ImageExtensionValidator implements ConstraintValidator<ImageExtension, MultipartFile> {
    private static final List<String> ALLOWED_EXTENSIONS = Arrays.asList("jpg", "jpeg", "png");

    @SuppressWarnings("null")
    @Override
    public boolean isValid(MultipartFile file, ConstraintValidatorContext context) {
        if (file == null && file.isEmpty()) {
            return true;
        }
        String originalFilename = file.getOriginalFilename();
        String fileExtension = originalFilename.substring(originalFilename.lastIndexOf(".") + 1).toLowerCase();
        return ALLOWED_EXTENSIONS.contains(fileExtension);
    }

}
