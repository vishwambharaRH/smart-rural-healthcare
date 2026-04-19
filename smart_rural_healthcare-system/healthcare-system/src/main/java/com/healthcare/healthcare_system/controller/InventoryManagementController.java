package com.healthcare.healthcare_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.healthcare.healthcare_system.model.MedicineInventory;
import com.healthcare.healthcare_system.service.MedicineInventoryService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/inventory")
public class InventoryManagementController {

    @Autowired
    private MedicineInventoryService medicineInventoryService;

    @GetMapping("/dashboard")
    public String inventoryDashboard(Model model) {
        List<MedicineInventory> allMedicines = medicineInventoryService.getAllMedicines();
        List<MedicineInventory> lowStockMedicines = medicineInventoryService.getLowStockMedicines(10);
        
        model.addAttribute("totalMedicines", allMedicines.size());
        model.addAttribute("lowStockCount", lowStockMedicines.size());
        model.addAttribute("allMedicines", allMedicines);
        model.addAttribute("lowStockMedicines", lowStockMedicines);
        
        return "inventory-dashboard";
    }

    @GetMapping("/list")
    public String listMedicines(Model model) {
        List<MedicineInventory> medicines = medicineInventoryService.getAllMedicines();
        model.addAttribute("medicines", medicines);
        return "inventory-list";
    }

    @GetMapping("/view/{id}")
    public String viewMedicine(@PathVariable Long id, Model model) {
        Optional<MedicineInventory> medicine = medicineInventoryService.getMedicineById(id);
        if (medicine.isPresent()) {
            model.addAttribute("medicine", medicine.get());
            return "inventory-view";
        }
        return "redirect:/inventory/list";
    }

    @GetMapping("/create")
    public String createMedicineForm(Model model) {
        model.addAttribute("medicine", new MedicineInventory());
        return "inventory-create";
    }

    @PostMapping("/create")
    public String createMedicine(
            @RequestParam String medicineName,
            @RequestParam String batchNumber,
            @RequestParam(required = false) String expiryDate,
            @RequestParam Integer quantity,
            @RequestParam String location,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) Double costPerUnit) {
        MedicineInventory medicine = new MedicineInventory();
        medicine.setMedicineName(medicineName);
        medicine.setBatchNumber(batchNumber);
        medicine.setExpiryDate(expiryDate);
        medicine.setQuantity(quantity);
        medicine.setLocation(location);
        medicine.setSpecialty(specialty);
        medicine.setCostPerUnit(costPerUnit);
        
        MedicineInventory saved = medicineInventoryService.saveMedicine(medicine);
        return "redirect:/inventory/view/" + saved.getId();
    }

    @GetMapping("/edit/{id}")
    public String editMedicineForm(@PathVariable Long id, Model model) {
        Optional<MedicineInventory> medicine = medicineInventoryService.getMedicineById(id);
        if (medicine.isPresent()) {
            model.addAttribute("medicine", medicine.get());
            return "inventory-edit";
        }
        return "redirect:/inventory/list";
    }

    @PostMapping("/edit/{id}")
    public String editMedicine(
            @PathVariable Long id,
            @RequestParam String medicineName,
            @RequestParam String batchNumber,
            @RequestParam(required = false) String expiryDate,
            @RequestParam Integer quantity,
            @RequestParam String location,
            @RequestParam(required = false) String specialty,
            @RequestParam(required = false) Double costPerUnit) {
        Optional<MedicineInventory> existingMedicine = medicineInventoryService.getMedicineById(id);
        if (existingMedicine.isPresent()) {
            MedicineInventory medicine = existingMedicine.get();
            medicine.setMedicineName(medicineName);
            medicine.setBatchNumber(batchNumber);
            medicine.setExpiryDate(expiryDate);
            medicine.setQuantity(quantity);
            medicine.setLocation(location);
            medicine.setSpecialty(specialty);
            medicine.setCostPerUnit(costPerUnit);
            
            medicineInventoryService.saveMedicine(medicine);
            return "redirect:/inventory/view/" + id;
        }
        return "redirect:/inventory/list";
    }

    @PostMapping("/use/{id}")
    public String useMedicine(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        try {
            medicineInventoryService.useMedicine(id, quantity);
            return "redirect:/inventory/view/" + id;
        } catch (IllegalArgumentException e) {
            return "redirect:/inventory/view/" + id + "?error=" + e.getMessage();
        }
    }

    @PostMapping("/add-stock/{id}")
    public String addStock(
            @PathVariable Long id,
            @RequestParam Integer quantity) {
        try {
            medicineInventoryService.addMedicineStock(id, quantity);
            return "redirect:/inventory/view/" + id;
        } catch (IllegalArgumentException e) {
            return "redirect:/inventory/view/" + id + "?error=" + e.getMessage();
        }
    }

    @PostMapping("/delete/{id}")
    public String deleteMedicine(@PathVariable Long id) {
        medicineInventoryService.deleteMedicine(id);
        return "redirect:/inventory/list";
    }

    @GetMapping("/low-stock")
    public String lowStockMedicines(Model model) {
        List<MedicineInventory> lowStockMedicines = medicineInventoryService.getLowStockMedicines(10);
        model.addAttribute("medicines", lowStockMedicines);
        return "inventory-low-stock";
    }

    @GetMapping("/by-specialty/{specialty}")
    public String medicinesBySpecialty(@PathVariable String specialty, Model model) {
        List<MedicineInventory> medicines = medicineInventoryService.getMedicinesBySpecialty(specialty);
        model.addAttribute("medicines", medicines);
        model.addAttribute("specialty", specialty);
        return "inventory-by-specialty";
    }

    @GetMapping("/by-location/{location}")
    public String medicinesByLocation(@PathVariable String location, Model model) {
        List<MedicineInventory> medicines = medicineInventoryService.getMedicinesForVillage(location, 0);
        model.addAttribute("medicines", medicines);
        model.addAttribute("location", location);
        return "inventory-by-location";
    }
}
