package com.hospital.management.service.image;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.hospital.management.entity.Image;
import com.hospital.management.repository.ImageRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ImageServiceImpl implements ImageService {
    @Autowired
    private ImageRepository imageRepository;

    @Override
    public Image saveImage(MultipartFile file) throws IOException {
        try {
            Image image = new Image();
            image.setType(file.getContentType());
            image.setSize(file.getSize());
            String url = UUID.randomUUID().toString() + file.getOriginalFilename();
            image.setUrl(url);
            image.setImage(file.getBytes());
            image.setCreatedAt(LocalDateTime.now());
            imageRepository.save(image);
            return image;
        } catch (Exception e) {
            throw new IOException("Error occurred while saving image");
        }

    }

    @Override
    public Image getImage(String url) {
        return imageRepository.findByUrl(url);
    }

}
