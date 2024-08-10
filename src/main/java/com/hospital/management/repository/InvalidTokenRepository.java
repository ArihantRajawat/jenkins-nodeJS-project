package com.hospital.management.repository;

import com.hospital.management.entity.InvalidToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvalidTokenRepository extends JpaRepository<InvalidToken, Long> {
    boolean existsByToken(String token);
}
