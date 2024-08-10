package com.hospital.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hospital.management.entity.PatientQuery;

public interface PatientQueryRepository extends JpaRepository<PatientQuery, Integer> {

}
