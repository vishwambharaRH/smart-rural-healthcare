package com.healthcare.healthcare_system.service;

import com.healthcare.healthcare_system.model.*;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.awt.Color;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
public class ReportService {

    @Autowired
    private PrescriptionService prescriptionService;

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private MedicineInventoryService medicineInventoryService;

    @Autowired
    private PatientService patientService;

    /**
     * Generate prescription PDF
     */
    public byte[] generatePrescriptionPDF(Long prescriptionId) {
        Optional<Prescription> prescriptionOpt = prescriptionService.getPrescriptionById(prescriptionId);
        if (prescriptionOpt.isEmpty()) {
            return new byte[0];
        }

        Prescription prescription = prescriptionOpt.get();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Header
            Paragraph header = new Paragraph("PRESCRIPTION", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            Paragraph subtitle = new Paragraph("Smart Rural Healthcare System", 
                    FontFactory.getFont(FontFactory.HELVETICA, 12));
            subtitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitle);

            Paragraph timestamp = new Paragraph("Generated on: " + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                    FontFactory.getFont(FontFactory.HELVETICA, 10));
            timestamp.setAlignment(Element.ALIGN_CENTER);
            document.add(timestamp);

            document.add(new Paragraph("\n"));

            // Prescription Details
            if (prescription.getDiagnosis() != null && prescription.getDiagnosis().getMedicalRecord() != null) {
                Appointment appointment = prescription.getDiagnosis().getMedicalRecord().getAppointment();
                if (appointment != null) {
                    Patient patient = appointment.getPatient();
                    Doctor doctor = appointment.getDoctor();

                    Paragraph patientTitle = new Paragraph("PATIENT INFORMATION", 
                            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
                    document.add(patientTitle);
                    document.add(new Paragraph("Name: " + (patient != null ? patient.getName() : "N/A")));
                    document.add(new Paragraph("Age: " + (patient != null ? patient.getAge() : "N/A")));
                    document.add(new Paragraph("Village: " + (patient != null ? patient.getVillage() : "N/A")));
                    document.add(new Paragraph("Phone: " + (patient != null ? patient.getPhone() : "N/A")));

                    document.add(new Paragraph("\n"));

                    Paragraph doctorTitle = new Paragraph("DOCTOR INFORMATION",
                            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
                    document.add(doctorTitle);
                    document.add(new Paragraph("Doctor: " + (doctor != null ? doctor.getName() : "N/A")));
                    document.add(new Paragraph("Specialty: " + (doctor != null ? doctor.getSpecialty() : "N/A")));
                    document.add(new Paragraph("Hospital: " + (doctor != null ? doctor.getHospital() : "N/A")));

                    document.add(new Paragraph("\n"));

                    Paragraph diagnosisTitle = new Paragraph("DIAGNOSIS INFORMATION",
                            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
                    document.add(diagnosisTitle);
                    if (prescription.getDiagnosis() != null) {
                        document.add(new Paragraph("Diagnosis: " + prescription.getDiagnosis().getDiagnosis()));
                        document.add(new Paragraph("Symptoms: " + prescription.getDiagnosis().getSymptoms()));
                        document.add(new Paragraph("Notes: " + prescription.getDiagnosis().getNotes()));
                    }
                }
            }

            document.add(new Paragraph("\n"));

            Paragraph rxTitle = new Paragraph("PRESCRIPTION DETAILS",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            document.add(rxTitle);

            // Medicines table
            PdfPTable table = new PdfPTable(3);
            table.setWidthPercentage(100);
            
            PdfPCell cell = new PdfPCell(new Paragraph("Medicines"));
            cell.setBackgroundColor(new Color(200, 200, 200));
            table.addCell(cell);
            
            cell = new PdfPCell(new Paragraph("Dosage"));
            cell.setBackgroundColor(new Color(200, 200, 200));
            table.addCell(cell);
            
            cell = new PdfPCell(new Paragraph("Instructions"));
            cell.setBackgroundColor(new Color(200, 200, 200));
            table.addCell(cell);

            table.addCell(prescription.getMedicines() != null ? prescription.getMedicines() : "N/A");
            table.addCell(prescription.getDosage() != null ? prescription.getDosage() : "N/A");
            table.addCell(prescription.getInstructions() != null ? prescription.getInstructions() : "N/A");

            document.add(table);

            document.add(new Paragraph("\nDuration: " + prescription.getDurationDays() + " days"));

            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("Authorized By: _________________________"));
            document.add(new Paragraph("Date: " + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    /**
     * Generate patient report (all diagnoses and prescriptions for a patient)
     */
    public byte[] generatePatientReportPDF(Long patientId) {
        Optional<Patient> patientOpt = patientService.getPatientById(patientId);
        if (patientOpt.isEmpty()) {
            return new byte[0];
        }

        Patient patient = patientOpt.get();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Header
            Paragraph header = new Paragraph("PATIENT MEDICAL REPORT",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            Paragraph subtitle = new Paragraph("Smart Rural Healthcare System",
                    FontFactory.getFont(FontFactory.HELVETICA, 12));
            subtitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitle);

            Paragraph timestamp = new Paragraph("Generated on: " + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                    FontFactory.getFont(FontFactory.HELVETICA, 10));
            timestamp.setAlignment(Element.ALIGN_CENTER);
            document.add(timestamp);

            document.add(new Paragraph("\n"));

            // Patient Info
            Paragraph patientTitle = new Paragraph("PATIENT INFORMATION",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            document.add(patientTitle);
            document.add(new Paragraph("Name: " + patient.getName()));
            document.add(new Paragraph("Age: " + patient.getAge()));
            document.add(new Paragraph("Gender: " + patient.getGender()));
            document.add(new Paragraph("Village: " + patient.getVillage()));
            document.add(new Paragraph("Phone: " + patient.getPhone()));

            document.add(new Paragraph("\n"));

            // Appointments with Medical Records
            Paragraph appointmentTitle = new Paragraph("MEDICAL APPOINTMENTS & RECORDS",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            document.add(appointmentTitle);
            
            List<Appointment> appointments = appointmentService.getAllAppointments();
            java.util.List<Appointment> patientAppointments = new java.util.ArrayList<>();
            
            for (Appointment apt : appointments) {
                if (apt.getPatient() != null && apt.getPatient().getId().equals(patientId)) {
                    patientAppointments.add(apt);
                }
            }
            
            if (patientAppointments.isEmpty()) {
                document.add(new Paragraph("No appointments found."));
            } else {
                int appointmentNumber = 1;
                for (Appointment apt : patientAppointments) {
                    Paragraph aptHeader = new Paragraph("\n--- Appointment #" + appointmentNumber++ + " ---",
                            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11));
                    document.add(aptHeader);
                    
                    document.add(new Paragraph("Date: " + apt.getAppointmentDate()));
                    document.add(new Paragraph("Doctor: " + (apt.getDoctor() != null ? apt.getDoctor().getName() : "N/A")));
                    document.add(new Paragraph("Reason: " + apt.getReason()));
                    document.add(new Paragraph("Status: " + apt.getStatus()));
                    
                    // Medical Record Details
                    if (apt.getMedicalRecord() != null) {
                        MedicalRecord medRecord = apt.getMedicalRecord();
                        document.add(new Paragraph(""));
                        document.add(new Paragraph("Medical Record:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
                        document.add(new Paragraph("  Record Date: " + (medRecord.getRecordDate() != null ? medRecord.getRecordDate() : "N/A")));
                        document.add(new Paragraph("  Notes: " + (medRecord.getNotes() != null ? medRecord.getNotes() : "N/A")));
                        
                        // Diagnosis
                        if (medRecord.getDiagnosis() != null) {
                            Diagnosis diagnosis = medRecord.getDiagnosis();
                            document.add(new Paragraph(""));
                            document.add(new Paragraph("Diagnosis:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
                            document.add(new Paragraph("  Condition: " + (diagnosis.getDiagnosis() != null ? diagnosis.getDiagnosis() : "N/A")));
                            document.add(new Paragraph("  Symptoms: " + (diagnosis.getSymptoms() != null ? diagnosis.getSymptoms() : "N/A")));
                            document.add(new Paragraph("  Notes: " + (diagnosis.getNotes() != null ? diagnosis.getNotes() : "N/A")));
                            
                            // Prescription
                            if (diagnosis.getPrescription() != null) {
                                Prescription prescription = diagnosis.getPrescription();
                                document.add(new Paragraph(""));
                                document.add(new Paragraph("Prescription:", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 10)));
                                document.add(new Paragraph("  Medicines: " + (prescription.getMedicines() != null ? prescription.getMedicines() : "N/A")));
                                document.add(new Paragraph("  Dosage: " + (prescription.getDosage() != null ? prescription.getDosage() : "N/A")));
                                document.add(new Paragraph("  Instructions: " + (prescription.getInstructions() != null ? prescription.getInstructions() : "N/A")));
                                document.add(new Paragraph("  Duration: " + prescription.getDurationDays() + " days"));
                            }
                        }
                    }
                }
            }

            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("Authorized By: _________________________"));
            document.add(new Paragraph("Date: " + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    /**
     * Generate medicine inventory report
     */
    public byte[] generateInventoryReportPDF() {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            Paragraph header = new Paragraph("MEDICINE INVENTORY REPORT",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            Paragraph timestamp = new Paragraph("Generated on: " + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                    FontFactory.getFont(FontFactory.HELVETICA, 10));
            timestamp.setAlignment(Element.ALIGN_CENTER);
            document.add(timestamp);

            document.add(new Paragraph("\n"));

            // Inventory Table
            List<MedicineInventory> medicines = medicineInventoryService.getAllMedicines();
            if (medicines.isEmpty()) {
                document.add(new Paragraph("No medicines in inventory."));
            } else {
                PdfPTable table = new PdfPTable(6);
                table.setWidthPercentage(100);
                
                String[] headers = {"Medicine Name", "Quantity", "Batch Number", "Expiry Date", "Cost/Unit", "Location"};
                for (String headerText : headers) {
                    PdfPCell cell = new PdfPCell(new Paragraph(headerText));
                    cell.setBackgroundColor(new Color(200, 200, 200));
                    table.addCell(cell);
                }

                for (MedicineInventory med : medicines) {
                    table.addCell(med.getMedicineName());
                    table.addCell(String.valueOf(med.getQuantity()));
                    table.addCell(med.getBatchNumber());
                    table.addCell(med.getExpiryDate());
                    table.addCell(String.valueOf(med.getCostPerUnit()));
                    table.addCell(med.getLocation());
                }
                document.add(table);
            }

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    /**
     * Generate patient prescriptions list PDF
     */
    public byte[] generatePatientPrescriptionsListPDF(Long patientId) {
        Optional<Patient> patientOpt = patientService.getPatientById(patientId);
        if (patientOpt.isEmpty()) {
            return new byte[0];
        }

        Patient patient = patientOpt.get();
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        try {
            Document document = new Document();
            PdfWriter.getInstance(document, baos);
            document.open();

            // Header
            Paragraph header = new Paragraph("PATIENT PRESCRIPTIONS",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 20));
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            Paragraph subtitle = new Paragraph("Smart Rural Healthcare System",
                    FontFactory.getFont(FontFactory.HELVETICA, 12));
            subtitle.setAlignment(Element.ALIGN_CENTER);
            document.add(subtitle);

            Paragraph timestamp = new Paragraph("Generated on: " + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")),
                    FontFactory.getFont(FontFactory.HELVETICA, 10));
            timestamp.setAlignment(Element.ALIGN_CENTER);
            document.add(timestamp);

            document.add(new Paragraph("\n"));

            // Patient Info
            Paragraph patientTitle = new Paragraph("PATIENT INFORMATION",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            document.add(patientTitle);
            document.add(new Paragraph("Name: " + patient.getName()));
            document.add(new Paragraph("Age: " + patient.getAge()));
            document.add(new Paragraph("Gender: " + patient.getGender()));
            document.add(new Paragraph("Village: " + patient.getVillage()));
            document.add(new Paragraph("Phone: " + patient.getPhone()));

            document.add(new Paragraph("\n"));

            // Prescriptions
            Paragraph prescriptionTitle = new Paragraph("PRESCRIPTIONS",
                    FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12));
            document.add(prescriptionTitle);

            List<Appointment> appointments = appointmentService.getAllAppointments();
            List<Prescription> patientPrescriptions = new java.util.ArrayList<>();

            for (Appointment apt : appointments) {
                if (apt.getPatient() != null && apt.getPatient().getId().equals(patientId) 
                        && apt.getMedicalRecord() != null 
                        && apt.getMedicalRecord().getDiagnosis() != null
                        && apt.getMedicalRecord().getDiagnosis().getPrescription() != null) {
                    patientPrescriptions.add(apt.getMedicalRecord().getDiagnosis().getPrescription());
                }
            }

            if (patientPrescriptions.isEmpty()) {
                document.add(new Paragraph("No prescriptions found for this patient."));
            } else {
                int prescriptionNumber = 1;
                for (Prescription prescription : patientPrescriptions) {
                    Paragraph prescNum = new Paragraph("\nPrescription #" + prescriptionNumber++,
                            FontFactory.getFont(FontFactory.HELVETICA_BOLD, 11));
                    document.add(prescNum);

                    if (prescription.getDiagnosis() != null && prescription.getDiagnosis().getMedicalRecord() != null) {
                        Appointment apt = prescription.getDiagnosis().getMedicalRecord().getAppointment();
                        if (apt != null && apt.getDoctor() != null) {
                            document.add(new Paragraph("Doctor: " + apt.getDoctor().getName()));
                            document.add(new Paragraph("Date: " + apt.getAppointmentDate()));
                        }
                    }

                    document.add(new Paragraph("Medicines: " + (prescription.getMedicines() != null ? prescription.getMedicines() : "N/A")));
                    document.add(new Paragraph("Dosage: " + (prescription.getDosage() != null ? prescription.getDosage() : "N/A")));
                    document.add(new Paragraph("Instructions: " + (prescription.getInstructions() != null ? prescription.getInstructions() : "N/A")));
                    document.add(new Paragraph("Duration: " + prescription.getDurationDays() + " days"));

                    if (prescription.getDiagnosis() != null) {
                        document.add(new Paragraph("Diagnosis: " + prescription.getDiagnosis().getDiagnosis()));
                    }

                    document.add(new Paragraph("-------------------------------------------"));
                }
            }

            document.add(new Paragraph("\n\n"));
            document.add(new Paragraph("Authorized By: _________________________"));
            document.add(new Paragraph("Date: " + LocalDateTime.now()
                    .format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))));

            document.close();
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }
}
