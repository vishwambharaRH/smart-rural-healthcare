package com.healthcare.healthcare_system.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.healthcare.healthcare_system.model.Appointment;
import com.healthcare.healthcare_system.model.Status;
import com.healthcare.healthcare_system.repository.AppointmentRepository;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Appointment saveAppointment(Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public Appointment getAppointmentById(Long id) {
        return appointmentRepository.findById(id).orElse(null);
    }

    public void deleteAppointment(Long id) {
        Appointment appointment = appointmentRepository.findById(id).orElse(null);
        if (appointment != null) {
            appointment.setStatus(Status.CANCELLED);
            appointmentRepository.save(appointment);
        }
    }

    public Appointment rescheduleAppointment(Appointment appointment) {
        Appointment existing = appointmentRepository.findById(appointment.getId()).orElse(null);
        if (existing != null) {
            existing.setAppointmentDate(appointment.getAppointmentDate());
            existing.setReason(appointment.getReason());
            // Keep the status as PENDING for rescheduled appointments
            existing.setStatus(Status.PENDING);
            return appointmentRepository.save(existing);
        }
        return null;
    }

    public List<Appointment> getAppointmentsByPatientUsername(String username) {
        return appointmentRepository.findByPatientUsername(username);
    }

    public List<Appointment> getPendingAppointmentsByDoctorUsername(String username) {
        return appointmentRepository.findByDoctorUsernameAndStatus(username, Status.PENDING);
    }
}

