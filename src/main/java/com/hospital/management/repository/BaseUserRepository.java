package com.hospital.management.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hospital.management.entity.BaseUser;

@Repository
public interface BaseUserRepository extends JpaRepository<BaseUser, Long> {
    BaseUser findByEmail(String email);
}
