package com.healthcare.healthcare_system.service;

import java.util.List;
import java.util.Optional;

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

    public Optional<MedicineInventory> getMedicineById(Long id) {
        return medicineInventoryRepository.findById(id);
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

    public MedicineInventory updateMedicineQuantity(Long id, Integer newQuantity) {
        Optional<MedicineInventory> medicine = medicineInventoryRepository.findById(id);
        if (medicine.isPresent()) {
            MedicineInventory med = medicine.get();
            med.setQuantity(newQuantity);
            return medicineInventoryRepository.save(med);
        }
        throw new IllegalArgumentException("Medicine with ID " + id + " not found");
    }

    public MedicineInventory useMedicine(Long id, Integer quantityToUse) {
        Optional<MedicineInventory> medicine = medicineInventoryRepository.findById(id);
        if (medicine.isPresent()) {
            MedicineInventory med = medicine.get();
            if (med.getQuantity() < quantityToUse) {
                throw new IllegalArgumentException("Insufficient quantity of medicine. Available: " + med.getQuantity() + ", Required: " + quantityToUse);
            }
            med.setQuantity(med.getQuantity() - quantityToUse);
            return medicineInventoryRepository.save(med);
        }
        throw new IllegalArgumentException("Medicine with ID " + id + " not found");
    }

    public MedicineInventory reserveMedicine(Long id, Integer quantityToReserve) {
        return useMedicine(id, quantityToReserve);
    }

    public MedicineInventory addMedicineStock(Long id, Integer quantityToAdd) {
        Optional<MedicineInventory> medicine = medicineInventoryRepository.findById(id);
        if (medicine.isPresent()) {
            MedicineInventory med = medicine.get();
            med.setQuantity(med.getQuantity() + quantityToAdd);
            return medicineInventoryRepository.save(med);
        }
        throw new IllegalArgumentException("Medicine with ID " + id + " not found");
    }

    public List<MedicineInventory> getLowStockMedicines(Integer threshold) {
        return medicineInventoryRepository.findAll().stream()
                .filter(med -> med.getQuantity() != null && med.getQuantity() < threshold)
                .toList();
    }

    public List<MedicineInventory> getMedicinesByBatchNumber(String batchNumber) {
        MedicineInventory medicine = medicineInventoryRepository.findByBatchNumber(batchNumber);
        return medicine != null ? List.of(medicine) : List.of();
    }
}

