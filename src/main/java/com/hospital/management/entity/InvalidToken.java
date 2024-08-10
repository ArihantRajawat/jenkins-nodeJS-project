package com.hospital.management.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Entity
@Table(name = "invalid_tokens")
@Data
public class InvalidToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "token", unique = true, nullable = false)
    private String token;
    @Column
    private long userId;
    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime expirationTime;

}
