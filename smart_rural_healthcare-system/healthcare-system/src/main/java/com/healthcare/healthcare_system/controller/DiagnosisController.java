package com.healthcare.healthcare_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.healthcare.healthcare_system.model.Diagnosis;
import com.healthcare.healthcare_system.model.MedicalRecord;
import com.healthcare.healthcare_system.service.DiagnosisService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/diagnosis")
public class DiagnosisController {

    @Autowired
    private DiagnosisService diagnosisService;

    @GetMapping("/list")
    public String listDiagnoses(Model model) {
        List<Diagnosis> diagnoses = diagnosisService.getAllDiagnoses();
        model.addAttribute("diagnoses", diagnoses);
        return "diagnosis-list";
    }

    @GetMapping("/view/{id}")
    public String viewDiagnosis(@PathVariable Long id, Model model) {
        Optional<Diagnosis> diagnosis = diagnosisService.getDiagnosisById(id);
        if (diagnosis.isPresent()) {
            model.addAttribute("diagnosis", diagnosis.get());
            return "diagnosis-view";
        }
        return "redirect:/diagnosis/list";
    }

    @GetMapping("/create")
    public String createDiagnosisForm(Model model) {
        model.addAttribute("diagnosis", new Diagnosis());
        return "diagnosis-create";
    }

    @PostMapping("/create")
    public String createDiagnosis(
            @RequestParam String symptoms,
            @RequestParam String diagnosis,
            @RequestParam(required = false) String notes) {
        Diagnosis newDiagnosis = new Diagnosis();
        newDiagnosis.setSymptoms(symptoms);
        newDiagnosis.setDiagnosis(diagnosis);
        newDiagnosis.setNotes(notes);
        
        Diagnosis saved = diagnosisService.createDiagnosis(newDiagnosis);
        return "redirect:/diagnosis/view/" + saved.getId();
    }

    @GetMapping("/edit/{id}")
    public String editDiagnosisForm(@PathVariable Long id, Model model) {
        Optional<Diagnosis> diagnosis = diagnosisService.getDiagnosisById(id);
        if (diagnosis.isPresent()) {
            model.addAttribute("diagnosis", diagnosis.get());
            return "diagnosis-edit";
        }
        return "redirect:/diagnosis/list";
    }

    @PostMapping("/edit/{id}")
    public String editDiagnosis(
            @PathVariable Long id,
            @RequestParam String symptoms,
            @RequestParam String diagnosis,
            @RequestParam(required = false) String notes) {
        Diagnosis diagnosisDetails = new Diagnosis();
        diagnosisDetails.setSymptoms(symptoms);
        diagnosisDetails.setDiagnosis(diagnosis);
        diagnosisDetails.setNotes(notes);
        
        Diagnosis updated = diagnosisService.updateDiagnosis(id, diagnosisDetails);
        if (updated != null) {
            return "redirect:/diagnosis/view/" + id;
        }
        return "redirect:/diagnosis/list";
    }

    @PostMapping("/delete/{id}")
    public String deleteDiagnosis(@PathVariable Long id) {
        diagnosisService.deleteDiagnosis(id);
        return "redirect:/diagnosis/list";
    }
}
