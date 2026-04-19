package com.healthcare.healthcare_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.healthcare_system.model.Diagnosis;
import com.healthcare.healthcare_system.model.MedicalRecord;
import java.util.List;
import java.util.Optional;

@Repository
public interface DiagnosisRepository extends JpaRepository<Diagnosis, Long> {
    
    Optional<Diagnosis> findByMedicalRecord(MedicalRecord medicalRecord);
    
    List<Diagnosis> findAll();
}
