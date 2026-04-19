package com.healthcare.healthcare_system.controller;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboardController {

    @GetMapping("/profile")
    public String profile() {
        return "profile"; // Create profile.html later if needed
    }

    @GetMapping("/dashboard")
    public String dashboard(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return "redirect:/";
        }

        boolean isDoctor = authentication.getAuthorities().stream()
            .anyMatch(authority -> "ROLE_DOCTOR".equals(authority.getAuthority()));
        if (isDoctor) {
            return "redirect:/doctor-dashboard";
        }

        boolean isPatient = authentication.getAuthorities().stream()
            .anyMatch(authority -> "ROLE_USER".equals(authority.getAuthority()));
        if (isPatient) {
            return "redirect:/patient-dashboard";
        }

        boolean isHealthWorker = authentication.getAuthorities().stream()
            .anyMatch(authority -> "ROLE_HEALTHWORKER".equals(authority.getAuthority()));
        if (isHealthWorker) {
            return "redirect:/healthworker-dashboard";
        }

        boolean isAdmin = authentication.getAuthorities().stream()
            .anyMatch(authority -> "ROLE_ADMIN".equals(authority.getAuthority()));
        if (isAdmin) {
            return "redirect:/admin-dashboard";
        }

        return "redirect:/";
    }
}


