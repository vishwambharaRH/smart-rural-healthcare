package com.healthcare.healthcare_system.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "camp_schedule")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CampSchedule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;

    @Column(nullable = false)
    private String village;

    private LocalDate campDate;

    private LocalTime startTime;
    
    private LocalTime endTime;

    private int hoursRequired;

    private String status; // SCHEDULED, COMPLETED, CANCELLED
}

