package com.hospital.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.management.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Image findByUrl(String url);
}
