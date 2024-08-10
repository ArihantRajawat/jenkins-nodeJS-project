package com.hospital.management.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/admin")
public class AdminViewController {

    @GetMapping("/dashboard")
    public String adminDashboard() {
        return "admin/home";
    }

    @GetMapping("/specialist")
    public String specialist() {
        return "admin/specialist";
    }

    @GetMapping("/doctor")
    public String doctor() {
        return "admin/doctor";
    }

    @GetMapping("/doctor/add")
    public String addNewDoctor() {
        return "admin/add_new_doctor";
    }

    @GetMapping("/doctor/add/summary")
    public String createDoctorAccountSummary() {
        return "admin/create_doctor_account_summary";
    }
}
