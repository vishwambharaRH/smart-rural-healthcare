package com.healthcare.healthcare_system.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import com.healthcare.healthcare_system.model.Diagnosis;
import com.healthcare.healthcare_system.model.Prescription;
import com.healthcare.healthcare_system.model.MedicalRecord;
import com.healthcare.healthcare_system.model.Appointment;
import com.healthcare.healthcare_system.service.DiagnosisService;
import com.healthcare.healthcare_system.service.PrescriptionService;
import com.healthcare.healthcare_system.service.MedicineInventoryService;
import com.healthcare.healthcare_system.service.AppointmentService;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/prescription")
public class PrescriptionController {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private DiagnosisService diagnosisService;

    @Autowired
    private MedicineInventoryService medicineInventoryService;

    @Autowired
    private AppointmentService appointmentService;

    @GetMapping("/list")
    public String listPrescriptions(Model model) {
        List<Prescription> prescriptions = prescriptionService.getAllPrescriptions();
        model.addAttribute("prescriptions", prescriptions);
        return "prescription-list";
    }

    @GetMapping("/view/{id}")
    public String viewPrescription(@PathVariable Long id, Model model) {
        Optional<Prescription> prescription = prescriptionService.getPrescriptionById(id);
        if (prescription.isPresent()) {
            model.addAttribute("prescription", prescription.get());
            return "prescription-view";
        }
        return "redirect:/prescription/list";
    }

    @GetMapping("/create/{diagnosisId}")
    public String createPrescriptionForm(@PathVariable Long diagnosisId, Model model) {
        Optional<Diagnosis> diagnosis = diagnosisService.getDiagnosisById(diagnosisId);
        if (diagnosis.isPresent()) {
            model.addAttribute("diagnosis", diagnosis.get());
            model.addAttribute("prescription", new Prescription());
            model.addAttribute("medicines", medicineInventoryService.getAllMedicines());
            return "prescription-create";
        }
        return "redirect:/prescription/list";
    }

    @PostMapping("/create")
    public String createPrescription(
            @RequestParam Long diagnosisId,
            @RequestParam String medicines,
            @RequestParam String dosage,
            @RequestParam String instructions,
            @RequestParam Integer durationDays) {
        Optional<Diagnosis> diagnosis = diagnosisService.getDiagnosisById(diagnosisId);
        if (diagnosis.isPresent()) {
            Prescription prescription = prescriptionService.generatePrescriptionFromDiagnosis(
                    diagnosis.get(), medicines, dosage, instructions, durationDays);
            return "redirect:/prescription/view/" + prescription.getId();
        }
        return "redirect:/prescription/list";
    }

    @GetMapping("/edit/{id}")
    public String editPrescriptionForm(@PathVariable Long id, Model model) {
        Optional<Prescription> prescription = prescriptionService.getPrescriptionById(id);
        if (prescription.isPresent()) {
            model.addAttribute("prescription", prescription.get());
            model.addAttribute("medicines", medicineInventoryService.getAllMedicines());
            return "prescription-edit";
        }
        return "redirect:/prescription/list";
    }

    @PostMapping("/edit/{id}")
    public String editPrescription(
            @PathVariable Long id,
            @RequestParam String medicines,
            @RequestParam String dosage,
            @RequestParam String instructions,
            @RequestParam Integer durationDays) {
        Prescription prescriptionDetails = new Prescription();
        prescriptionDetails.setMedicines(medicines);
        prescriptionDetails.setDosage(dosage);
        prescriptionDetails.setInstructions(instructions);
        prescriptionDetails.setDurationDays(durationDays);

        Prescription updated = prescriptionService.updatePrescription(id, prescriptionDetails);
        if (updated != null) {
            return "redirect:/prescription/view/" + id;
        }
        return "redirect:/prescription/list";
    }

    @PostMapping("/delete/{id}")
    public String deletePrescription(@PathVariable Long id) {
        prescriptionService.deletePrescription(id);
        return "redirect:/prescription/list";
    }

    @GetMapping("/api/medicines-by-specialty/{specialty}")
    @ResponseBody
    public List<?> getMedicinesBySpecialty(@PathVariable String specialty) {
        return medicineInventoryService.getMedicinesBySpecialty(specialty);
    }

    /**
     * Create prescription from appointment - directly from appointments view
     */
    @GetMapping("/create-from-appointment/{appointmentId}")
    public String createPrescriptionFromAppointment(@PathVariable Long appointmentId, Model model) {
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment == null) {
            return "redirect:/appointments";
        }
        model.addAttribute("appointment", appointment);
        model.addAttribute("patient", appointment.getPatient());
        model.addAttribute("doctor", appointment.getDoctor());
        model.addAttribute("prescription", new Prescription());
        model.addAttribute("medicines", medicineInventoryService.getAllMedicines());
        
        return "prescription-from-appointment";
    }

    /**
     * Save prescription from appointment
     */
    @PostMapping("/save-from-appointment")
    public String savePrescriptionFromAppointment(
            @RequestParam Long appointmentId,
            @RequestParam String medicines,
            @RequestParam String dosage,
            @RequestParam String instructions,
            @RequestParam Integer durationDays) {
        
        Appointment appointment = appointmentService.getAppointmentById(appointmentId);
        if (appointment == null) {
            return "redirect:/appointments";
        }
        
        // Create diagnosis first if not exists
        Diagnosis diagnosis = new Diagnosis();
        diagnosis.setDiagnosis("From Appointment ID: " + appointmentId);
        diagnosis.setSymptoms("To be documented");
        diagnosis.setNotes("Prescription created from appointment view");
        diagnosis = diagnosisService.createDiagnosis(diagnosis);

        // Create prescription
        Prescription prescription = new Prescription();
        prescription.setDiagnosis(diagnosis);
        prescription.setMedicines(medicines);
        prescription.setDosage(dosage);
        prescription.setInstructions(instructions);
        prescription.setDurationDays(durationDays);
        
        prescription = prescriptionService.createPrescription(prescription);
        
        return "redirect:/prescription/view/" + prescription.getId();
    }
}
