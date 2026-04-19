package com.healthcare.healthcare_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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

    private void loadCommonData(Model model) {
        model.addAttribute("medicines", medicineInventoryService.getAllMedicines());
        model.addAttribute("camps", campScheduleService.getAllCamps());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("appointments", appointmentService.getAllAppointments());
    }

    @GetMapping("/healthworker-dashboard")
    @PreAuthorize("hasRole('HEALTHWORKER')")
    public String dashboard(Model model, Authentication authentication) {

        CustomUserDetails user =
                (CustomUserDetails) authentication.getPrincipal();

        loadCommonData(model);
        return "healthworker-dashboard";
    }

    @GetMapping("/inventory")
    @PreAuthorize("hasRole('HEALTHWORKER')")
    public String inventory(Model model) {
        loadCommonData(model);
        model.addAttribute("view", "inventory");
        return "healthworker-dashboard";
    }

    @GetMapping("/camps")
    @PreAuthorize("hasRole('HEALTHWORKER')")
    public String camps(Model model) {
        loadCommonData(model);
        model.addAttribute("view", "camps");
        return "healthworker-dashboard";
    }

    @GetMapping("/add-camp-form")
    @PreAuthorize("hasRole('HEALTHWORKER')")
    public String addCampForm(Model model) {
        loadCommonData(model);
        model.addAttribute("newCamp", new CampSchedule());
        model.addAttribute("view", "addcamp");
        return "healthworker-dashboard";
    }

    @PostMapping("/add-camp")
    @PreAuthorize("hasRole('HEALTHWORKER')")
    public String addCamp(@ModelAttribute("newCamp") CampSchedule camp) {

        camp.setStatus("SCHEDULED");
        campScheduleService.saveCamp(camp);

        return "redirect:/camps";
    }

    @GetMapping("/camp/edit/{id}")
    @PreAuthorize("hasRole('HEALTHWORKER')")
    public String editCamp(@PathVariable Long id, Model model) {

        loadCommonData(model);

        CampSchedule camp = campScheduleService.getCampById(id);

        model.addAttribute("editCamp", camp);
        model.addAttribute("view", "editcamp");

        return "healthworker-dashboard";
    }

    @PostMapping("/camp/update")
    @PreAuthorize("hasRole('HEALTHWORKER')")
    public String updateCamp(@ModelAttribute("editCamp") CampSchedule camp) {

        campScheduleService.saveCamp(camp);

        return "redirect:/camps";
    }

    @GetMapping("/camp/delete/{id}")
    @PreAuthorize("hasRole('HEALTHWORKER')")
    public String deleteCamp(@PathVariable Long id) {

        campScheduleService.deleteCamp(id);

        return "redirect:/camps";
    }
}