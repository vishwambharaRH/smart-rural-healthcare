package com.healthcare.healthcare_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.healthcare.healthcare_system.model.User;
import com.healthcare.healthcare_system.service.UserService;
import com.healthcare.healthcare_system.service.PatientService;
import com.healthcare.healthcare_system.service.DoctorService;
import com.healthcare.healthcare_system.service.AppointmentService;
import com.healthcare.healthcare_system.service.MedicineInventoryService;
import com.healthcare.healthcare_system.service.CampScheduleService;
import com.healthcare.healthcare_system.repository.UserRepository;

import java.util.List;

@Controller
public class AdminDashboardController {

    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private MedicineInventoryService medicineInventoryService;

    @Autowired
    private CampScheduleService campScheduleService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/admin-dashboard")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminDashboard(Model model, Authentication authentication) {
        String username = authentication.getName();
        User adminUser = userService.findByUsername(username);

        model.addAttribute("adminUser", adminUser);
        model.addAttribute("totalUsers", userRepository.count());
        model.addAttribute("totalPatients", patientService.getAllPatients() != null ? patientService.getAllPatients().size() : 0);
        model.addAttribute("totalDoctors", doctorService.getAvailableDoctors().size());
        model.addAttribute("totalAppointments", appointmentService.getAllAppointments() != null ? appointmentService.getAllAppointments().size() : 0);
        model.addAttribute("medicines", medicineInventoryService.getAllMedicines());
        model.addAttribute("camps", campScheduleService.getAllCamps());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAvailableDoctors());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
        model.addAttribute("users", userRepository.findAll());

        return "admin-dashboard";
    }
}
