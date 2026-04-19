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
import com.healthcare.healthcare_system.service.ReportService;
import com.healthcare.healthcare_system.service.PatientService;
import com.healthcare.healthcare_system.model.Patient;
import com.healthcare.healthcare_system.model.Appointment;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Optional;

@Controller
public class PatientDashboardController {

    private final DoctorService doctorService;
    private final AppointmentService appointmentService;
private final CampScheduleService campScheduleService;
    private final ReportService reportService;
    private final PatientService patientService;

    public PatientDashboardController(
            DoctorService doctorService,
            AppointmentService appointmentService,
CampScheduleService campScheduleService, ReportService reportService, PatientService patientService) {

        this.doctorService = doctorService;
        this.appointmentService = appointmentService;
        this.campScheduleService = campScheduleService;
        this.reportService = reportService;
        this.patientService = patientService;
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

    @GetMapping("/patient/download-medical-report")
    @PreAuthorize("hasRole('USER')")
    public void downloadMedicalReport(HttpServletResponse response, Authentication authentication) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Optional<Patient> patientOpt = patientService.getPatientByUsername(username);
        if (patientOpt.isPresent()) {
            byte[] pdfBytes = reportService.generatePatientReportPDF(patientOpt.get().getId());
            if (pdfBytes.length > 0) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=\"medical-report.pdf\"");
                response.setContentLength(pdfBytes.length);
                response.getOutputStream().write(pdfBytes);
                response.getOutputStream().flush();
            }
        }
    }

    @GetMapping("/patient/download-prescription-list")
    @PreAuthorize("hasRole('USER')")
    public void downloadPrescriptionList(HttpServletResponse response, Authentication authentication) throws IOException {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();

        Optional<Patient> patientOpt = patientService.getPatientByUsername(username);
        if (patientOpt.isPresent()) {
            // Note: For all prescriptions, could loop single PDFs but use patient report for simplicity
            byte[] pdfBytes = reportService.generateInventoryReportPDF(); // Reuse or extend for prescriptions
            // TODO: Implement multi-prescription PDF if needed
            if (pdfBytes.length > 0) {
                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition", "attachment; filename=\"prescriptions.pdf\"");
                response.setContentLength(pdfBytes.length);
                response.getOutputStream().write(pdfBytes);
                response.getOutputStream().flush();
            }
        }
    }
}
