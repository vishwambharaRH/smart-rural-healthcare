package com.healthcare.healthcare_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.healthcare.healthcare_system.model.Doctor;
import com.healthcare.healthcare_system.service.AppointmentService;
import com.healthcare.healthcare_system.service.DoctorService;
import com.healthcare.healthcare_system.service.PatientService;

@Controller
public class DoctorDashboardController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

@GetMapping("/doctor-dashboard")
    @PreAuthorize("hasRole('DOCTOR')")
    public String doctorDashboard(Model model, Authentication authentication) {
        
        String username = authentication.getName();
        
        model.addAttribute("patients", patientService.getAllPatients());
        Doctor doctor = doctorService.getDoctorByUsername(username);
        model.addAttribute("doctor", doctor);
        model.addAttribute("pendingAppointments", appointmentService.getPendingAppointmentsByDoctorUsername(username));
        model.addAttribute("allAppointments", appointmentService.getAppointmentsByDoctorUsername(username));
        
        return "doctor-dashboard";
    }
}

