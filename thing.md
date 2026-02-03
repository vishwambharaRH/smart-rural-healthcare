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

## 1. Highlighted Features

- Role-based user management (Patient, Doctor, Health Worker, Admin)
- Digital registration and management of rural patients
- Scheduling and management of rural health camps
- Appointment booking and conflict-free scheduling
- Centralized medical record management
- Diagnosis and prescription handling
- Automated notification system
- Report generation for healthcare analysis
- Object-oriented design following OOP principles

---

## 2. Functional Requirements (User Stories)

### Patient Stories
- As a **patient**, I want to register in the system so that my personal and medical details are stored digitally.
- As a **patient**, I want to view nearby health camps so that I can access healthcare easily.
- As a **patient**, I want to book appointments so that I can consult a doctor.
- As a **patient**, I want to view my medical records so that I can track my health history.
- As a **patient**, I want to receive notifications so that I am informed about appointments and camps.
- As a **patient**, I want to view prescriptions so that I can follow my treatment.

---

### Doctor Stories
- As a **doctor**, I want to view scheduled appointments so that I can plan consultations.
- As a **doctor**, I want to access patient medical records so that I can diagnose accurately.
- As a **doctor**, I want to record diagnoses so that patient conditions are documented.
- As a **doctor**, I want to issue prescriptions so that patients receive proper treatment.

---

### Health Worker Stories
- As a **health worker**, I want to register patients so that rural users without digital access are included.
- As a **health worker**, I want to manage health camps so that outreach services function smoothly.
- As a **health worker**, I want to notify patients so that participation in camps increases.

---

### Administrator Stories
- As an **administrator**, I want to manage system users so that access is controlled.
- As an **administrator**, I want to schedule health camps so that healthcare reaches rural areas.
- As an **administrator**, I want to generate reports so that healthcare performance can be analyzed.

---

### System Stories
- As a **system**, I want to authenticate users so that data remains secure.
- As a **system**, I want to prevent appointment conflicts so that doctors are not double-booked.
- As a **system**, I want to store medical records persistently so that data is not lost.
- As a **system**, I want to send automated notifications so that users are informed in time.

---

## 3. Non-Functional Requirements (Stories)

- As a **user**, I want the system to be easy to use so that even non-technical users can operate it.
- As a **user**, I want the system to respond quickly so that tasks are completed without delay.
- As a **user**, I want my medical data to be secure so that privacy is maintained.
- As a **user**, I want the system to be reliable so that data is not lost during normal operation.
- As a **developer**, I want the system to be modular so that future enhancements are easy to implement.
- As a **developer**, I want the system to be maintainable so that bugs and updates can be handled easily.

---

## 4. Classes and Objects

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
- Methods: registerPatient(), manageHealthCamp()

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

---

## Conclusion

The Smart Rural Healthcare Outreach Management System applies object-oriented programming principles to solve real-world rural healthcare challenges. By structuring the system around user stories, clear requirements, and well-defined classes, the project demonstrates effective OOP design and practical software engineering practices.
