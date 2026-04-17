package com.healthcare.healthcare_system.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthcare.healthcare_system.model.Patient;


public interface PatientRepository extends JpaRepository<Patient, Long> {
    
    Optional<Patient> findByUsername(String username);
}
