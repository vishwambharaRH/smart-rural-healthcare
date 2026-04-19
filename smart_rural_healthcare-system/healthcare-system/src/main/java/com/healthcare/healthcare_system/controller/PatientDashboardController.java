package com.healthcare.healthcare_system.controller;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.healthcare.healthcare_system.config.CustomUserDetails;
import com.healthcare.healthcare_system.model.Appointment;
import com.healthcare.healthcare_system.model.Doctor;
import com.healthcare.healthcare_system.service.AppointmentService;
import com.healthcare.healthcare_system.service.DoctorService;
import com.healthcare.healthcare_system.service.CampScheduleService;

@Controller
public class PatientDashboardController {

    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
    private final CampScheduleService campScheduleService;

    public PatientDashboardController(
            DoctorService doctorService,
            AppointmentService appointmentService,
            CampScheduleService campScheduleService) {

        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.campScheduleService = campScheduleService;
    }

    @GetMapping("/patient-dashboard")
    @PreAuthorize("hasRole('USER')")
    public String patientDashboard(Model model, Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        String username = userDetails.getUsername();

        List<Doctor> doctors = doctorService.getAvailableDoctors();
        model.addAttribute("doctors", doctors);

        List<Appointment> appointments =
                appointmentService.getAppointmentsByPatientUsername(username);

        model.addAttribute("appointments", appointments);

        model.addAttribute("camps",
                campScheduleService.getAllCamps());

        model.addAttribute("diagnoses", List.of());
        model.addAttribute("prescriptions", List.of());

        return "patient-dashboard";
    }
}