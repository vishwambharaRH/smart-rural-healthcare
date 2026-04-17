package com.healthcare.healthcare_system.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.healthcare_system.model.Patient;
import com.healthcare.healthcare_system.repository.PatientRepository;

@Service
public class PatientFactoryService {

    @Autowired
    private PatientRepository patientRepository;

    public Patient createOrGetPatient(String username) {
        if (patientRepository.findByUsername(username).isPresent()) {
            return patientRepository.findByUsername(username).get();
        }

        Patient patient = new Patient();
        patient.setUsername(username);
        patient.setName("Patient");
        patient.setVillage("Village");
        patient.setAge(30);
        patient.setGender("Male");
        patient.setPhone("1234567890");
        patient.setDiagnosis("N/A");
        return patientRepository.save(patient);
    }
}


