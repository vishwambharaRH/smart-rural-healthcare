package com.healthcare.healthcare_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.security.core.Authentication;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.healthcare.healthcare_system.model.Appointment;

import com.healthcare.healthcare_system.service.AppointmentService;
import com.healthcare.healthcare_system.service.DoctorService;
import com.healthcare.healthcare_system.service.PatientService;

@Controller
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    @GetMapping("/appointments")
    public String viewAppointments(Model model) {
        model.addAttribute("listAppointments", appointmentService.getAllAppointments());
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "appointment";
    }



@PostMapping("/appointments/admin")
    public String saveAppointment(@ModelAttribute("appointment") Appointment appointment) {
        appointmentService.saveAppointment(appointment);
        return "redirect:/appointments";
    }

    @GetMapping("/appointments/edit/{id}")
    public String editAppointmentForm(@PathVariable Long id, Model model) {
        model.addAttribute("appointment", appointmentService.getAppointmentById(id));
        model.addAttribute("patients", patientService.getAllPatients());
        model.addAttribute("doctors", doctorService.getAllDoctors());
        return "editAppointment";
    }

@GetMapping("/appointments/cancel/{id}")
    public String cancelAppointment(@PathVariable("id") Long id, Authentication authentication, RedirectAttributes redirectAttributes) {
        appointmentService.deleteAppointment(id);
        redirectAttributes.addFlashAttribute("message", "Appointment cancelled successfully!");
        return "redirect:/patient-dashboard";
    }

    @PostMapping("/appointments/reschedule")
    public String rescheduleAppointment(@ModelAttribute Appointment appointment, RedirectAttributes redirectAttributes) {
        Appointment rescheduled = appointmentService.rescheduleAppointment(appointment);
        if (rescheduled != null) {
            redirectAttributes.addFlashAttribute("message", "Appointment rescheduled successfully!");
        } else {
            redirectAttributes.addFlashAttribute("error", "Failed to reschedule appointment!");
        }
        return "redirect:/patient-dashboard";
    }

    @GetMapping("/appointments/reschedule/{id}")
    public String rescheduleAppointmentForm(@PathVariable("id") Long id, Model model, Authentication authentication) {
        Appointment appointment = appointmentService.getAppointmentById(id);
        model.addAttribute("appointment", appointment);
        return "rescheduleAppointment";
    }

    @GetMapping("/appointments/{id}")
    public String deleteAppointment(@PathVariable Long id) {
        appointmentService.deleteAppointment(id);
        return "redirect:/appointments";
    }
}

