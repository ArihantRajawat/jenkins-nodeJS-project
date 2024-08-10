package com.hospital.management.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Entity
@Table(name = "doctors")
@PrimaryKeyJoinColumn(name = "user_id")
@Data
@EqualsAndHashCode(callSuper = false)
public class Doctor extends BaseUser {

    @Column(name = "experience")
    private String experience;

    @Column(name = "qualification")
    private String qualification;

    @Column(name = "consultation_fee")
    private int consultationFee;

    @Column(name = "licence_no")
    private String licenceNo;

    @Column(name = "about", length = 1000)
    private String about;

    @Column(name = "active")
    private Boolean active;

    @ManyToOne
    @JoinColumn(name = "specialization_id")
    private Specialist specialization;
}
