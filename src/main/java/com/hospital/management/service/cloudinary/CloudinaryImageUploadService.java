package com.hospital.management.service.cloudinary;

import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface CloudinaryImageUploadService {
    @SuppressWarnings("rawtypes")
    Map uploadImage(MultipartFile file);
}
