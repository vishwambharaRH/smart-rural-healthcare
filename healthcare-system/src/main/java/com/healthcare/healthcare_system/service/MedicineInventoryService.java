package com.healthcare.healthcare_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.healthcare_system.model.MedicineInventory;
import com.healthcare.healthcare_system.repository.MedicineInventoryRepository;

@Service
public class MedicineInventoryService {

    @Autowired
    private MedicineInventoryRepository medicineInventoryRepository;

    public List<MedicineInventory> getAllMedicines() {
        return medicineInventoryRepository.findAll();
    }

    public MedicineInventory getMedicineById(Long id) {
        return medicineInventoryRepository.findById(id).orElse(null);
    }

    public List<MedicineInventory> getMedicinesBySpecialty(String specialty) {
        return medicineInventoryRepository.findBySpecialty(specialty);
    }

    public MedicineInventory saveMedicine(MedicineInventory medicine) {
        return medicineInventoryRepository.save(medicine);
    }

    public void deleteMedicine(Long id) {
        medicineInventoryRepository.deleteById(id);
    }

    public List<MedicineInventory> getMedicinesForVillage(String village, int minQuantity) {
        return medicineInventoryRepository.findByLocationAndQuantityGreaterThan(village, minQuantity);
    }
}

