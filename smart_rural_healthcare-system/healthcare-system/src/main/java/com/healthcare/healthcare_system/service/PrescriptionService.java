package com.healthcare.healthcare_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.healthcare_system.model.Diagnosis;
import com.healthcare.healthcare_system.model.Prescription;
import com.healthcare.healthcare_system.repository.PrescriptionRepository;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    public Prescription createPrescription(Prescription prescription) {
        return prescriptionRepository.save(prescription);
    }

    public Optional<Prescription> getPrescriptionById(Long id) {
        return prescriptionRepository.findById(id);
    }

    public Optional<Prescription> getPrescriptionByDiagnosis(Diagnosis diagnosis) {
        return prescriptionRepository.findByDiagnosis(diagnosis);
    }

    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    public Prescription updatePrescription(Long id, Prescription prescriptionDetails) {
        Optional<Prescription> existingPrescription = prescriptionRepository.findById(id);
        if (existingPrescription.isPresent()) {
            Prescription prescription = existingPrescription.get();
            prescription.setMedicines(prescriptionDetails.getMedicines());
            prescription.setDosage(prescriptionDetails.getDosage());
            prescription.setInstructions(prescriptionDetails.getInstructions());
            prescription.setDurationDays(prescriptionDetails.getDurationDays());
            return prescriptionRepository.save(prescription);
        }
        return null;
    }

    public void deletePrescription(Long id) {
        prescriptionRepository.deleteById(id);
    }

    public Prescription generatePrescriptionFromDiagnosis(Diagnosis diagnosis, String medicines, 
                                                         String dosage, String instructions, 
                                                         int durationDays) {
        Prescription prescription = new Prescription();
        prescription.setDiagnosis(diagnosis);
        prescription.setMedicines(medicines);
        prescription.setDosage(dosage);
        prescription.setInstructions(instructions);
        prescription.setDurationDays(durationDays);
        return prescriptionRepository.save(prescription);
    }
}
