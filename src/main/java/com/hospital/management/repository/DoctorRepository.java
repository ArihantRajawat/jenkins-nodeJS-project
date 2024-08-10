package com.hospital.management.repository;

import com.hospital.management.entity.Doctor;
import com.hospital.management.entity.Specialist;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends JpaRepository<Doctor, Integer> {
    Doctor findByEmail(String email);

    List<Doctor> findBySpecialization(Specialist specialist);

    List<Doctor> findByFirstnameContainingIgnoreCase(String keyword);
}
