package com.hospital.management.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "specialities")
@Data
public class Specialist {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private String description;

    @Column
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime createdAt;

    @OneToMany(mappedBy = "specialization")
    @JsonIgnore
    private List<Doctor> doctors;

}
