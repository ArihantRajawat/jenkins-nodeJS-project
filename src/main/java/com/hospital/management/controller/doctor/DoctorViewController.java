package com.hospital.management.controller.doctor;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/doctor")
public class DoctorViewController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "doctor/home";
    }

    @GetMapping("/appointment")
    public String appointment() {
        return "doctor/appointment";
    }

    @GetMapping("/patient")
    public String patient() {
        return "doctor/patient";
    }

    @GetMapping("/profile")
    public String profile() {
        return "doctor/profile";
    }
}
