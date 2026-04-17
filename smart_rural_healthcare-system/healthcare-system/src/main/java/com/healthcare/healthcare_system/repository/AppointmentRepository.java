package com.healthcare.healthcare_system.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.healthcare.healthcare_system.model.Appointment;
import com.healthcare.healthcare_system.model.Status;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByPatientUsername(String username);
    List<Appointment> findByDoctorUsernameAndStatus(String username, Status status);
}

