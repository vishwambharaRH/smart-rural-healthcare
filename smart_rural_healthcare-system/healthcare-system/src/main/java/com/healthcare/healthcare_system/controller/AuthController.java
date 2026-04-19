package com.healthcare.healthcare_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.healthcare.healthcare_system.model.User;
import com.healthcare.healthcare_system.model.Patient;
import com.healthcare.healthcare_system.service.UserService;
import com.healthcare.healthcare_system.service.PatientService;

@Controller
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute User user, Model model) {

        if (userService.findByUsername(user.getUsername()) != null) {
            model.addAttribute("msg", "Username already exists");
            return "register";
        }

        // Save login user account
        userService.saveUser(user);

        // If registered as Patient/User, create patient profile
        if (user.getRole() == User.Role.USER) {

            if (patientService
                    .getPatientByUsername(user.getUsername())
                    .isEmpty()) {

                Patient patient = new Patient();

                patient.setName(user.getName());
                patient.setAge(0);
                patient.setGender("Not Provided");
                patient.setVillage(user.getVillage());
                patient.setPhone(user.getPhone());
                patient.setDiagnosis("None");
                patient.setUsername(user.getUsername());

                patientService.savePatient(patient);
            }
        }

        model.addAttribute("msg", "Registration successful! Please login.");
        return "register";
    }

    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}