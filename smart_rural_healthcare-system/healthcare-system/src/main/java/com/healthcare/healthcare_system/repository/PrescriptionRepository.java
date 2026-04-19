package com.healthcare.healthcare_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.healthcare_system.model.Prescription;
import com.healthcare.healthcare_system.model.Diagnosis;
import java.util.List;
import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Long> {
    
    Optional<Prescription> findByDiagnosis(Diagnosis diagnosis);
    
    List<Prescription> findAll();
}
