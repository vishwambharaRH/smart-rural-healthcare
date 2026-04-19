package com.healthcare.healthcare_system.controller;

import com.healthcare.healthcare_system.service.ReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Controller
@RequestMapping("/reports")
public class ReportController {

    @Autowired
    private ReportService reportService;

    /**
     * Download prescription PDF
     */
    @GetMapping("/prescription/{id}/download")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<byte[]> downloadPrescriptionPDF(@PathVariable Long id) {
        byte[] pdfContent = reportService.generatePrescriptionPDF(id);

        if (pdfContent.length == 0) {
            return ResponseEntity.notFound().build();
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss"));
        String filename = "Prescription_" + id + "_" + timestamp + ".pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                        ContentDisposition.attachment().filename(filename).build().toString())
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfContent);
    }

    /**
     * Download patient medical report PDF
     */
    @GetMapping("/patient/{id}/download")
    @PreAuthorize("hasRole('DOCTOR') or hasRole('ADMIN')")
    public ResponseEntity<byte[]> downloadPatientReportPDF(@PathVariable Long id) {
        byte[] pdfContent = reportService.generatePatientReportPDF(id);

        if (pdfContent.length == 0) {
            return ResponseEntity.notFound().build();
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss"));
        String filename = "Patient_Report_" + id + "_" + timestamp + ".pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                        ContentDisposition.attachment().filename(filename).build().toString())
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfContent);
    }

    /**
     * Download inventory report PDF
     */
    @GetMapping("/inventory/download")
    @PreAuthorize("hasRole('ADMIN') or hasRole('HEALTHWORKER')")
    public ResponseEntity<byte[]> downloadInventoryReportPDF() {
        byte[] pdfContent = reportService.generateInventoryReportPDF();

        if (pdfContent.length == 0) {
            return ResponseEntity.badRequest().build();
        }

        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("ddMMyyyy_HHmmss"));
        String filename = "Inventory_Report_" + timestamp + ".pdf";

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, 
                        ContentDisposition.attachment().filename(filename).build().toString())
                .contentType(MediaType.APPLICATION_PDF)
                .body(pdfContent);
    }
}
