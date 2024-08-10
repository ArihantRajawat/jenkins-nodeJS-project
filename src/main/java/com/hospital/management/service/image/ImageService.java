package com.hospital.management.service.image;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.hospital.management.entity.Image;

public interface ImageService {
    Image saveImage(MultipartFile file) throws IOException;

    Image getImage(String url);
}
