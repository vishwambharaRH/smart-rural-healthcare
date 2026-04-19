# Smart Rural Healthcare Outreach Management System

Team Members:
- [Vineet Anil Sharma](https://github.com/VineetSharma05) (PES1UG23CS690)
- [Vishruth Rallapalli](https://github.com/Vishy-55) (PES1UG23CS699)
- [Vishwambhara R Hebbalalu](https://github.com/vishwambharaRH) (PES1UG23CS700)
- [Vrishank N Amembal](https://github.com/vrishank-na) (PES1UG23CS705)

## Project Synopsis

The **Smart Rural Healthcare Outreach Management System** is an object-oriented software solution designed to manage and streamline healthcare services in rural and remote areas. The system focuses on organizing health camps, managing patient records, coordinating healthcare personnel, and ensuring timely communication between patients, doctors, and health workers.

Rural healthcare often suffers from fragmented record-keeping, poor coordination, and lack of accessibility. This system aims to digitally bridge that gap by offering a centralized platform for healthcare outreach programs, while demonstrating core **Object-Oriented Programming principles** such as **abstraction, inheritance, encapsulation, and polymorphism**.

---

## Objectives

- Digitize and centralize rural healthcare data
- Manage healthcare camps and appointments efficiently
- Maintain secure medical records for patients
- Facilitate communication between healthcare stakeholders
- Apply OOP concepts in a real-world problem domain

---

## Functional Requirements

### User Management
- The system shall support multiple user roles:
  - Patient
  - Doctor
  - Health Worker
  - Administrator
- Users shall be able to log in and perform role-specific operations
- Administrators shall be able to create, update, and remove users

### Patient Management
- The system shall allow registration of patients with personal and location details
- Patients shall be able to view their medical records and prescriptions
- Patients shall receive notifications about appointments and health camps

### Health Camp Management
- The system shall allow administrators or health workers to schedule health camps
- Each health camp shall store location, date, available doctors, and services offered
- Patients shall be able to enroll in nearby health camps

### Appointment Management
- Patients shall be able to book appointments with doctors during health camps
- Doctors shall be able to view their scheduled appointments
- The system shall prevent appointment conflicts

### Medical Records
- The system shall maintain a medical history for each patient
- Doctors shall be able to add diagnoses and prescriptions
- Medical records shall be retrievable based on patient identity

### Diagnosis and Prescription
- Doctors shall record diagnoses during consultations
- Prescriptions shall be generated and linked to diagnoses
- Patients shall be able to view their prescriptions

### Notification System
- The system shall send notifications for:
  - Upcoming appointments
  - Health camp schedules
  - Prescription updates

### Reporting
- The system shall generate reports such as:
  - Number of patients treated
  - Health camp performance
  - Disease trends (basic statistics)

---

## Non-Functional Requirements

### Usability
- The system shall have a simple and intuitive interface
- Users with minimal technical knowledge shall be able to use the system

### Performance
- The system shall support multiple users concurrently
- Basic operations shall execute with minimal delay

### Scalability
- The system shall allow addition of new user roles and healthcare services
- The system design shall support future expansion

### Security
- Medical data shall be accessible only to authorized users
- User authentication and role-based access control shall be enforced

### Reliability
- The system shall prevent data loss during normal operation
- Input validation shall be performed to avoid inconsistent data

### Maintainability
- The system shall follow modular object-oriented design
- Classes shall be loosely coupled and highly cohesive

## Quick Start

The runnable Spring Boot application is in `smart_rural_healthcare-system/healthcare-system`.

```bash
cd smart_rural_healthcare-system/healthcare-system
chmod +x mvnw
./mvnw spring-boot:run
```

The app is configured to start on `http://localhost:8084/`.

---

## Classes and Their Responsibilities

### User (Abstract Class)
- Attributes: userId, name, contactInfo, role
- Methods: login(), logout(), viewProfile()

### Patient (extends User)
- Attributes: patientId, age, gender, medicalRecords
- Methods: viewMedicalRecords(), bookAppointment()

### Doctor (extends User)
- Attributes: specialization, assignedCamps
- Methods: diagnosePatient(), prescribeMedication()

### HealthWorker (extends User)
- Attributes: assignedArea
- Methods: registerPatient(), manageCamp()

### Admin (extends User)
- Attributes: adminLevel
- Methods: manageUsers(), generateReports()

### HealthCamp
- Attributes: campId, date, location, doctorsAvailable
- Methods: scheduleCamp(), enrollPatient()

### MedicalRecord
- Attributes: recordId, patientId, diagnoses
- Methods: addRecord(), viewRecord()

### Appointment
- Attributes: appointmentId, patient, doctor, time
- Methods: schedule(), cancel()

### Diagnosis
- Attributes: diagnosisId, symptoms, result
- Methods: recordDiagnosis()

### Prescription
- Attributes: prescriptionId, medicines, dosage
- Methods: generatePrescription()

### Notification
- Attributes: notificationId, message, recipient
- Methods: sendNotification()

### Location
- Attributes: village, district, coordinates
- Methods: getLocationDetails()

### ReportGenerator
- Methods: generatePatientReport(), generateCampReport()
