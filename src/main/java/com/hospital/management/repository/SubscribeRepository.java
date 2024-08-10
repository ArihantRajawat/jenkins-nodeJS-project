package com.hospital.management.repository;

import com.hospital.management.entity.Subscribe;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SubscribeRepository extends JpaRepository<Subscribe, Long> {
    Subscribe findByEmail(String email);
}
