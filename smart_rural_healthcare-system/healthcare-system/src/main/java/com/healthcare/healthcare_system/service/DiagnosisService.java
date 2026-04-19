package com.healthcare.healthcare_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.healthcare_system.model.Diagnosis;
import com.healthcare.healthcare_system.model.MedicalRecord;
import com.healthcare.healthcare_system.repository.DiagnosisRepository;

@Service
public class DiagnosisService {

    @Autowired
    private DiagnosisRepository diagnosisRepository;

    public Diagnosis createDiagnosis(Diagnosis diagnosis) {
        return diagnosisRepository.save(diagnosis);
    }

    public Optional<Diagnosis> getDiagnosisById(Long id) {
        return diagnosisRepository.findById(id);
    }

    public Optional<Diagnosis> getDiagnosisByMedicalRecord(MedicalRecord medicalRecord) {
        return diagnosisRepository.findByMedicalRecord(medicalRecord);
    }

    public List<Diagnosis> getAllDiagnoses() {
        return diagnosisRepository.findAll();
    }

    public Diagnosis updateDiagnosis(Long id, Diagnosis diagnosisDetails) {
        Optional<Diagnosis> existingDiagnosis = diagnosisRepository.findById(id);
        if (existingDiagnosis.isPresent()) {
            Diagnosis diagnosis = existingDiagnosis.get();
            diagnosis.setSymptoms(diagnosisDetails.getSymptoms());
            diagnosis.setDiagnosis(diagnosisDetails.getDiagnosis());
            diagnosis.setNotes(diagnosisDetails.getNotes());
            return diagnosisRepository.save(diagnosis);
        }
        return null;
    }

    public void deleteDiagnosis(Long id) {
        diagnosisRepository.deleteById(id);
    }
}
