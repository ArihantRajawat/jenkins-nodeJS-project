package com.hospital.management.repository;

import com.hospital.management.entity.Specialist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SpecialistRepository extends JpaRepository<Specialist, Integer> {
    Specialist findByName(String name);

    boolean existsByName(String name);

    List<Specialist> findByNameContainingIgnoreCase(String keyword);

    Specialist findByIdAndNameIgnoreCase(int id, String name);
}
