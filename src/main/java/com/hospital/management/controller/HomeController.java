package com.hospital.management.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @RequestMapping(value = { "/", "/home" })
    public String home() {
        return "index";
    }

    @RequestMapping(value = "/register")
    public String register() {
        return "register";
    }

    @GetMapping(value = "/forgot-password")
    public String forgotPassword() {
        return "forgot_password";
    }

    @GetMapping(value = "/forgot-password/verify-otp")
    public String verifyOtp() {
        return "otp_verify";
    }

    @GetMapping(value = "/forgot-password/set-new-password")
    public String setNewPassword() {
        return "set_new_password";
    }

    @GetMapping("/admin")
    @ResponseBody
    public String admin() {
        return "Admin login";
    }

    @GetMapping("/user")
    @ResponseBody
    public String user() {
        return "User login";
    }

    @GetMapping("/doctor")
    @ResponseBody
    public String doctor() {
        return "Doctor login";
    }

    @GetMapping("/error/404")
    public String pageNotFound() {
        return "404";
    }
}
