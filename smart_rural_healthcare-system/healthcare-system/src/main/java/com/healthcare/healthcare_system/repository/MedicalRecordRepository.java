package com.healthcare.healthcare_system.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.healthcare.healthcare_system.model.MedicalRecord;


public interface MedicalRecordRepository extends JpaRepository<MedicalRecord, Long> {
}
