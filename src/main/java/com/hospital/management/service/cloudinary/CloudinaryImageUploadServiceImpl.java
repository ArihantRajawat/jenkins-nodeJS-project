package com.hospital.management.service.cloudinary;

import java.io.IOException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.cloudinary.Cloudinary;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class CloudinaryImageUploadServiceImpl implements CloudinaryImageUploadService {
    @Autowired
    private Cloudinary cloudinary;

    @SuppressWarnings("rawtypes")
    @Override
    public Map uploadImage(MultipartFile file) {
        try {
            Map uploadResult = this.cloudinary.uploader().upload(file.getBytes(), Map.of());
            return uploadResult;
        } catch (IOException e) {
            throw new RuntimeException("image uploading error.");
        }
    }
}
