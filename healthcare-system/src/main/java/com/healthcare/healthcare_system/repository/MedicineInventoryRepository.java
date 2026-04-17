package com.healthcare.healthcare_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.healthcare_system.model.MedicineInventory;

@Repository
public interface MedicineInventoryRepository extends JpaRepository<MedicineInventory, Long> {
    
    List<MedicineInventory> findBySpecialty(String specialty);
    
    List<MedicineInventory> findByLocationAndQuantityGreaterThan(String location, int quantity);
    
    MedicineInventory findByBatchNumber(String batchNumber);
}

