package com.healthcare.healthcare_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.healthcare.healthcare_system.config.CustomUserDetails;
import com.healthcare.healthcare_system.model.Appointment;
import com.healthcare.healthcare_system.model.Doctor;
import com.healthcare.healthcare_system.model.Patient;
import com.healthcare.healthcare_system.model.Status;
import com.healthcare.healthcare_system.service.AppointmentService;
import com.healthcare.healthcare_system.service.DoctorService;
import com.healthcare.healthcare_system.service.PatientService;

@Controller
public class AppointmentBookingController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private DoctorService doctorService;

    @Autowired
    private PatientService patientService;


    @GetMapping("/appointments/new")
    public String newAppointment(Model model, Authentication authentication, @RequestParam(required = false) Long doctorId) {
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        
        Patient patient = patientService.getPatientByUsername(username)
            .orElseGet(() -> {
                Patient newPatient = new Patient();
                newPatient.setUsername(username);
                newPatient.setName("Patient " + username);
                newPatient.setVillage("Unknown");
                newPatient.setAge(30);
                newPatient.setGender("Not Specified");
                return patientService.savePatient(newPatient);
            });
        
        Appointment appt = new Appointment();
        appt.setPatient(patient);
        if (doctorId != null) {
            Doctor selectedDoctor = doctorService.getDoctorById(doctorId);
            if (selectedDoctor != null) {
                appt.setDoctor(selectedDoctor);
            }
        }
        model.addAttribute("appointment", appt);
        model.addAttribute("doctors", doctorService.getAvailableDoctors());
        
        return "addAppointment";
    }


    @PostMapping("/appointments")

    public String createAppointment(@ModelAttribute Appointment appointment, Authentication authentication, RedirectAttributes redirectAttributes) {
        
        CustomUserDetails userDetails = (CustomUserDetails) authentication.getPrincipal();
        String username = userDetails.getUsername();
        
        Patient patient = patientService.getPatientByUsername(username)
            .orElseThrow(() -> new RuntimeException("Patient not found for username: " + username));
        
        Doctor doctor = null;
        if (appointment.getDoctor() != null && appointment.getDoctor().getId() != null) {
            doctor = doctorService.getDoctorById(appointment.getDoctor().getId());
        }
        if (doctor == null) {
            redirectAttributes.addFlashAttribute("error", "Invalid doctor selected");
            return "redirect:/appointments/new";
        }
        
        appointment.setPatient(patient);
        appointment.setDoctor(doctor);
        appointment.setStatus(Status.PENDING);
        
        appointmentService.saveAppointment(appointment);
        redirectAttributes.addFlashAttribute("message", "Appointment booked successfully!");
        return "redirect:/patient-dashboard";
    }

}

