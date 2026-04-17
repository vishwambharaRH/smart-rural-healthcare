package com.healthcare.healthcare_system.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "diagnoses")
public class Diagnosis {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "medical_record_id")
    private MedicalRecord medicalRecord;

    private String symptoms;
    private String diagnosis;
    private String notes;

    @OneToOne(mappedBy = "diagnosis", cascade = CascadeType.ALL)
    private Prescription prescription;
}

