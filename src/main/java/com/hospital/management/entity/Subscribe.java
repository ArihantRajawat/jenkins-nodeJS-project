package com.hospital.management.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "subscribers")
@Data
public class Subscribe {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String email;
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;
}
