package com.hospital.management.controller.user;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class UserViewController {
    @GetMapping("/book-appointment")
    @PreAuthorize("hasRole('USER')")
    public String bookAppointment() {
        return "user/book-appointment";
    }

    @GetMapping(value = "/change-password")
    public String changePassword() {
        return "/user/change_password";
    }

    @GetMapping(value = "/doctors")
    public String doctors() {
        return "/user/doctors";
    }

    @GetMapping(value = "/doctors/detail")
    public String doctorDetail() {
        return "/user/doctor_detail";
    }
}
