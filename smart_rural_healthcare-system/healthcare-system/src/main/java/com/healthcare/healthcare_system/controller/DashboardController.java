package com.healthcare.healthcare_system.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/profile")
    public String profile() {
        return "profile"; // Create profile.html later if needed
    }

    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
}


