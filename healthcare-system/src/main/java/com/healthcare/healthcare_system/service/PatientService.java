package com.healthcare.healthcare_system.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.healthcare_system.model.Patient;
import com.healthcare.healthcare_system.repository.PatientRepository;

@Service
public class PatientService {

    @Autowired
    private PatientRepository patientRepository;

    public List<Patient> getAllPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> getPatientById(Long id) {
        return patientRepository.findById(id);
    }

    public Patient savePatient(Patient patient) {
        return patientRepository.save(patient);
    }


    public void deletePatient(Long id) {
        patientRepository.deleteById(id);
    }

    public Optional<Patient> getPatientByUsername(String username) {
        return patientRepository.findByUsername(username);
    }
}

