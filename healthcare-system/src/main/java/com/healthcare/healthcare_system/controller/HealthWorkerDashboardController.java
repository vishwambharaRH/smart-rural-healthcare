package com.healthcare.healthcare_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.healthcare.healthcare_system.service.MedicineInventoryService;
import com.healthcare.healthcare_system.service.PatientService;
import com.healthcare.healthcare_system.service.AppointmentService;
import com.healthcare.healthcare_system.service.CampScheduleService;
import com.healthcare.healthcare_system.config.CustomUserDetails;
import com.healthcare.healthcare_system.model.CampSchedule;

@Controller
public class HealthWorkerDashboardController {

    @Autowired
    private MedicineInventoryService medicineInventoryService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private CampScheduleService campScheduleService;

    @GetMapping("/healthworker-dashboard")
    @PreAuthorize("hasRole('HEALTHWORKER')")
    public String healthWorkerDashboard(Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        model.addAttribute("medicines", medicineInventoryService.getAllMedicines());
        model.addAttribute("camps", campScheduleService.getAllCamps());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        
        return "healthworker-dashboard";
    }

    @GetMapping("/healthworker-functionalities")
    @PreAuthorize("hasRole('HEALTHWORKER')")
    public String healthWorkerFunctionalities(Model model, Authentication authentication) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        model.addAttribute("newCamp", new CampSchedule());
        model.addAttribute("medicines", medicineInventoryService.getAllMedicines());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        
        return "healthworker-functionalities";
    }
}


