package com.hospital.management.controller.admin;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hospital.management.repository.SpecialistRepository;
import com.hospital.management.service.appointment.AppointmentService;
import com.hospital.management.service.doctor.DoctorService;
import com.hospital.management.service.specialist.SpecialistService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/admin")
public class AdminDashboardController {
    @Autowired
    private SpecialistRepository specialistRepository;
    @Autowired
    private SpecialistService specialistService;
    @Autowired
    private DoctorService doctorService;
    @Autowired
    private AppointmentService apoAppointmentService;

    @GetMapping("/getTotalSpecialist")
    public ResponseEntity<Long> getTotalSpecialist() {
        Long totalSpecialist = specialistRepository.count();
        return ResponseEntity.ok(totalSpecialist);
    }

    @GetMapping("/getTotalDoctor")
    public ResponseEntity<Long> getTotalDoctors() {
        Long totalDoctor = doctorService.getTotalDoctor();
        return ResponseEntity.ok(totalDoctor);
    }

}
