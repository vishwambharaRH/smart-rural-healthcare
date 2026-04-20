package com.healthcare.healthcare_system;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.healthcare.healthcare_system.model.Appointment;
import com.healthcare.healthcare_system.model.Doctor;
import com.healthcare.healthcare_system.model.Patient;
import com.healthcare.healthcare_system.model.User;
import com.healthcare.healthcare_system.repository.AppointmentRepository;
import com.healthcare.healthcare_system.repository.DoctorRepository;
import com.healthcare.healthcare_system.repository.PatientRepository;
import com.healthcare.healthcare_system.repository.UserRepository;
import com.healthcare.healthcare_system.model.CampSchedule;
import com.healthcare.healthcare_system.repository.CampScheduleRepository;
import com.healthcare.healthcare_system.model.MedicalRecord;
import com.healthcare.healthcare_system.model.Diagnosis;
import com.healthcare.healthcare_system.model.Prescription;
import com.healthcare.healthcare_system.model.Status;
import com.healthcare.healthcare_system.repository.MedicalRecordRepository;
import com.healthcare.healthcare_system.repository.DiagnosisRepository;
import com.healthcare.healthcare_system.repository.PrescriptionRepository;
import java.time.LocalDate;
import java.time.LocalTime;

@Component
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final DoctorRepository doctorRepository;
    private final PatientRepository patientRepository;
    private final AppointmentRepository appointmentRepository;
    private final CampScheduleRepository campScheduleRepository;
    private final MedicalRecordRepository medicalRecordRepository;
    private final DiagnosisRepository diagnosisRepository;
    private final PrescriptionRepository prescriptionRepository;

    public DataSeeder(UserRepository userRepository, DoctorRepository doctorRepository, PatientRepository patientRepository, AppointmentRepository appointmentRepository, CampScheduleRepository campScheduleRepository, MedicalRecordRepository medicalRecordRepository, DiagnosisRepository diagnosisRepository, PrescriptionRepository prescriptionRepository) {
        this.userRepository = userRepository;
        this.doctorRepository = doctorRepository;
        this.patientRepository = patientRepository;
        this.appointmentRepository = appointmentRepository;
        this.campScheduleRepository = campScheduleRepository;
        this.medicalRecordRepository = medicalRecordRepository;
        this.diagnosisRepository = diagnosisRepository;
        this.prescriptionRepository = prescriptionRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        // Check if data exists
        if (doctorRepository.count() == 0) {
            // 1. Admin User
            User admin = new User();
            admin.setUsername("admin");
admin.setPassword("$2a$10$N9Qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy");
            admin.setName("Admin");
            admin.setRole(User.Role.ADMIN);
            admin.setPhone("9999999999");
            admin.setVillage("Admin Village");
            userRepository.save(admin);

            // 2. Doctor User
            User doctorUser = new User();
            doctorUser.setUsername("doctor1");
doctorUser.setPassword("$2a$10$N9Qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy");
            doctorUser.setName("Dr. Raj");
            doctorUser.setRole(User.Role.DOCTOR);
            doctorUser.setPhone("9876543210");
            doctorUser.setVillage("Delhi Camp");
            userRepository.save(doctorUser);

            // 3. Patient User
            User patientUser = new User();
            patientUser.setUsername("patient1");
patientUser.setPassword("$2a$10$N9Qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy");
            patientUser.setName("Ramesh Kumar");
            patientUser.setRole(User.Role.USER);
            patientUser.setPhone("9123456789");
            patientUser.setVillage("Village Gopalpur");
            userRepository.save(patientUser);

            // 4. Healthworker
            User healthworker = new User();
            healthworker.setUsername("healthworker1");
healthworker.setPassword("$2a$10$N9Qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy");
            healthworker.setName("Sita Devi");
            healthworker.setRole(User.Role.HEALTHWORKER);
            healthworker.setPhone("9988776655");
            healthworker.setVillage("Rural Health Center");
            userRepository.save(healthworker);

            // 5. Doctors DB
            Doctor doctor1 = new Doctor();
            doctor1.setUsername("doctor1");
            doctor1.setName("Dr. Raj");
            doctor1.setSpecialty("General Physician");
            doctor1.setPhone("9876543210");
            doctor1.setHospital("Delhi Health Camp");
            doctor1.setGeoLocation("28.6139,77.2090");
            doctor1.setAvailable(true);
            doctorRepository.save(doctor1);


            Doctor doctor2 = new Doctor();
            doctor2.setUsername("doctor2");
            doctor2.setName("Dr. Priya");
            doctor2.setSpecialty("Pediatrics");
            doctor2.setPhone("8765432109");
            doctor2.setHospital("Gopalpur Camp");
            doctor2.setGeoLocation("28.7041,77.1025");
            doctor2.setAvailable(true);
            doctorRepository.save(doctor2);


            // 6. Patients DB
            Patient patient1 = new Patient();
            patient1.setUsername("patient1");
            patient1.setName("Ramesh Kumar");
            patient1.setAge(35);
            patient1.setGender("Male");
            patient1.setVillage("Gopalpur");
            patient1.setPhone("9123456789");
            patient1.setDiagnosis("Hypertension");
            patientRepository.save(patient1);

            // Camps DB
            Doctor doctor1Ref = doctorRepository.findByUsername("doctor1").orElse(doctor1);
            CampSchedule camp1 = new CampSchedule();
            camp1.setVillage("Gopalpur");
            camp1.setCampDate(LocalDate.now().plusDays(2));
            camp1.setStartTime(LocalTime.of(9, 0));
            camp1.setEndTime(LocalTime.of(17, 0));
            camp1.setDoctor(doctor1Ref);
            camp1.setStatus("SCHEDULED");
            campScheduleRepository.save(camp1);

            // 7. Appointments DB (link patient/doctor)
            doctorRepository.save(doctor1);
            
            Appointment apt1 = new Appointment();
            apt1.setAppointmentDate(LocalDateTime.now().plusDays(1));
            apt1.setReason("Checkup");
            apt1.setDoctor(doctor1);
            apt1.setPatient(patient1);
            apt1.setStatus(Status.APPROVED);
            appointmentRepository.save(apt1);

            // 8. Medical Records with Diagnosis and Prescriptions
            MedicalRecord medRecord = new MedicalRecord();
            medRecord.setAppointment(apt1);
            medRecord.setRecordDate(LocalDateTime.now());
            medRecord.setNotes("Patient was diagnosed with High Blood Pressure. Advised to reduce salt intake and regular exercise.");
            medicalRecordRepository.save(medRecord);

            // 9. Diagnosis
            Diagnosis diagnosis = new Diagnosis();
            diagnosis.setMedicalRecord(medRecord);
            diagnosis.setDiagnosis("Hypertension (High Blood Pressure)");
            diagnosis.setSymptoms("Headaches, dizziness, occasional fatigue");
            diagnosis.setNotes("BP readings: 150/95 mm Hg - Grade 1 Hypertension detected");
            diagnosisRepository.save(diagnosis);

            // 10. Prescription
            Prescription prescription = new Prescription();
            prescription.setDiagnosis(diagnosis);
            prescription.setMedicines("Amlodipine 5mg, Metoprolol 50mg");
            prescription.setDosage("Amlodipine: 1 tablet daily in morning, Metoprolol: 1 tablet twice daily");
            prescription.setInstructions("Take with water after meals. Do not skip doses. Monitor BP daily if possible.");
            prescription.setDurationDays(30);
            prescriptionRepository.save(prescription);

            // Update appointment with medical record
            apt1.setMedicalRecord(medRecord);
            appointmentRepository.save(apt1);

            System.out.println("✅ Seed data loaded: 4 users, 2 doctors, 1 patient, 1 appointment with complete medical records");
        }
    }
}

