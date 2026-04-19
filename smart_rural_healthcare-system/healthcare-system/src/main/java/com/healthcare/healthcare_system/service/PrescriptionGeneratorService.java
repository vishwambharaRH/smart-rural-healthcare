package com.healthcare.healthcare_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.healthcare_system.model.*;
import com.healthcare.healthcare_system.repository.PrescriptionRepository;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service for generating and managing prescriptions
 * Provides intelligent prescription generation based on diagnosis and available medicines
 */
@Service
public class PrescriptionGeneratorService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private MedicineInventoryService medicineInventoryService;

    @Autowired
    private DiagnosisService diagnosisService;

    /**
     * Generate a prescription with suggested medicines based on diagnosis and available inventory
     */
    public Prescription generatePrescriptionWithSuggestions(Long diagnosisId, String doctorSpecialty) {
        Optional<Diagnosis> diagnosisOpt = diagnosisService.getDiagnosisById(diagnosisId);
        if (diagnosisOpt.isEmpty()) {
            throw new IllegalArgumentException("Diagnosis not found");
        }

        Diagnosis diagnosis = diagnosisOpt.get();
        
        // Get available medicines for the doctor's specialty
        List<MedicineInventory> availableMedicines = 
            medicineInventoryService.getMedicinesBySpecialty(doctorSpecialty);

        // Filter medicines with sufficient quantity
        List<MedicineInventory> recommendedMedicines = availableMedicines.stream()
            .filter(med -> med.getQuantity() != null && med.getQuantity() > 5)
            .collect(Collectors.toList());

        // Create prescription from diagnosis
        Prescription prescription = new Prescription();
        prescription.setDiagnosis(diagnosis);
        prescription.setDurationDays(7); // Default 7 days
        
        // Set medicines from recommendations
        if (!recommendedMedicines.isEmpty()) {
            String medicinesList = recommendedMedicines.stream()
                .limit(3)
                .map(MedicineInventory::getMedicineName)
                .collect(Collectors.joining(", "));
            prescription.setMedicines(medicinesList);
            prescription.setDosage("As per doctor's prescription");
            prescription.setInstructions("Take with water, after meals if stomach upset");
        }

        return prescription;
    }

    /**
     * Generate a quick prescription for common ailments
     */
    public Prescription generateQuickPrescription(String symptom, String location) {
        // Get common medicine recommendations based on symptom
        Map<String, String> symptomMedicineMap = new HashMap<>();
        symptomMedicineMap.put("fever", "Paracetamol, Ibuprofen");
        symptomMedicineMap.put("cough", "Cough Syrup, Lozenges");
        symptomMedicineMap.put("cold", "Multivitamins, Antihistamines");
        symptomMedicineMap.put("headache", "Aspirin, Paracetamol");
        symptomMedicineMap.put("stomach", "Antacid, Omeprazole");
        symptomMedicineMap.put("diarrhea", "Oral Rehydration Salts, Loperamide");
        symptomMedicineMap.put("allergy", "Antihistamines, Hydrocortisone");

        Prescription prescription = new Prescription();
        prescription.setMedicines(symptomMedicineMap.getOrDefault(symptom.toLowerCase(), "Paracetamol"));
        prescription.setDosage("As prescribed by health worker");
        prescription.setInstructions("Follow dosage strictly. Consult doctor if symptoms persist.");
        prescription.setDurationDays(3);

        return prescription;
    }

    /**
     * Get medicine suggestions for a specific diagnosis
     */
    public List<MedicineInventory> suggestMedicinesForDiagnosis(String diagnosis) {
        // Map common diagnoses to required medicine categories
        Map<String, List<String>> diagnosisMedicineMap = new HashMap<>();
        diagnosisMedicineMap.put("hypertension", Arrays.asList("Lisinopril", "Amlodipine", "Atenolol"));
        diagnosisMedicineMap.put("diabetes", Arrays.asList("Metformin", "Glipizide", "Insulin"));
        diagnosisMedicineMap.put("asthma", Arrays.asList("Salbutamol", "Fluticasone", "Theophylline"));
        diagnosisMedicineMap.put("infection", Arrays.asList("Amoxicillin", "Ciprofloxacin", "Azithromycin"));
        diagnosisMedicineMap.put("pain", Arrays.asList("Ibuprofen", "Diclofenac", "Tramadol"));
        
        List<String> suggestedMedicineNames = diagnosisMedicineMap
            .getOrDefault(diagnosis.toLowerCase(), new ArrayList<>());

        return medicineInventoryService.getAllMedicines().stream()
            .filter(med -> suggestedMedicineNames.stream()
                .anyMatch(name -> med.getMedicineName().toLowerCase().contains(name.toLowerCase())))
            .collect(Collectors.toList());
    }

    /**
     * Check if prescription can be fulfilled with available inventory
     */
    public boolean canFulfillPrescription(Prescription prescription) {
        if (prescription.getMedicines() == null || prescription.getMedicines().isEmpty()) {
            return false;
        }

        String[] medicines = prescription.getMedicines().split(",");
        List<MedicineInventory> allMedicines = medicineInventoryService.getAllMedicines();

        for (String medicineName : medicines) {
            boolean found = allMedicines.stream()
                .anyMatch(med -> med.getMedicineName().equalsIgnoreCase(medicineName.trim()) 
                    && med.getQuantity() != null && med.getQuantity() > 0);
            if (!found) {
                return false;
            }
        }
        return true;
    }

    /**
     * Get alternative medicines if primary medicine is not available
     */
    public List<MedicineInventory> getAlternativeMedicines(String medicineName, String specialty) {
        List<MedicineInventory> alternatives = medicineInventoryService.getMedicinesBySpecialty(specialty);
        
        return alternatives.stream()
            .filter(med -> med.getQuantity() != null && med.getQuantity() > 0)
            .filter(med -> !med.getMedicineName().equalsIgnoreCase(medicineName))
            .limit(3)
            .collect(Collectors.toList());
    }

    /**
     * Generate prescription report with available inventory info
     */
    public Map<String, Object> generatePrescriptionReport(Long prescriptionId) {
        Optional<Prescription> prescriptionOpt = prescriptionRepository.findById(prescriptionId);
        if (prescriptionOpt.isEmpty()) {
            throw new IllegalArgumentException("Prescription not found");
        }

        Prescription prescription = prescriptionOpt.get();
        Map<String, Object> report = new HashMap<>();

        report.put("prescriptionId", prescriptionId);
        report.put("medicines", prescription.getMedicines());
        report.put("dosage", prescription.getDosage());
        report.put("duration", prescription.getDurationDays());
        report.put("canFulfill", canFulfillPrescription(prescription));
        report.put("instructions", prescription.getInstructions());

        return report;
    }
}
