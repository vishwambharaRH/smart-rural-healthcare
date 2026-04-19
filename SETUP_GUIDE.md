# Smart Rural Healthcare System - Setup Guide

## Project Overview
A Spring Boot application for managing rural healthcare outreach programs with support for patients, doctors, health workers, and administrators.

## Prerequisites
- **Java**: OpenJDK 21 (already installed ✅)
- **Maven**: Not required - Maven wrapper is included
- **Git**: For version control

## Project Structure
```
healthcare-system/
├── src/
│   ├── main/
│   │   ├── java/com/healthcare/healthcare_system/
│   │   │   ├── config/          # Security & configuration
│   │   │   ├── controller/      # REST endpoints
│   │   │   ├── handler/         # Error handling
│   │   │   ├── model/           # Entity classes
│   │   │   ├── repository/      # Data access layer
│   │   │   └── service/         # Business logic
│   │   └── resources/
│   │       ├── application.properties  # App configuration
│   │       ├── static/                 # CSS, JS, images
│   │       └── templates/              # Thymeleaf HTML templates
│   └── test/
├── pom.xml                      # Maven configuration
├── mvnw                         # Maven wrapper (Linux/Mac)
└── mvnw.cmd                     # Maven wrapper (Windows)
```

## Key Technologies
- **Spring Boot 3.5.13**: Web framework
- **Spring Data JPA**: Database access
- **Hibernate**: ORM
- **Thymeleaf**: Template engine
- **H2 Database**: In-memory database (development)
- **MySQL Connector**: For MySQL support (production)
- **Lombok**: Boilerplate reduction

## Setup Steps

### 1. Build the Project
Navigate to the healthcare-system directory and run:

```bash
cd /Users/vishwam/VSCode/smart-rural-healthcare/healthcare-system
./mvnw clean package -DskipTests
```

**Note**: The Maven wrapper will automatically download Maven if not installed.

### 2. Run the Application
```bash
java -jar target/healthcare-system-0.0.1-SNAPSHOT.jar
```

Or use Maven directly:
```bash
./mvnw spring-boot:run
```

### 3. Access the Application
- **Home**: http://localhost:8084/
- **H2 Database Console**: http://localhost:8084/h2-console
  - JDBC URL: `jdbc:h2:mem:testdb`
  - Username: `sa`
  - Password: (leave empty)

## Configuration

### Application Port
Edit `src/main/resources/application.properties`:
```properties
server.port=8084
```

### Database
- **Development**: Uses H2 in-memory database (auto-creates schema)
- **Production**: Update `spring.datasource.url` to use MySQL

### Default Users (Seeded on Startup)
- **Admin**: admin / admin (password)
- **Doctor**: doctor1 / password
- **Health Worker**: worker1 / password
- **User**: patient1 / password

## Project Status

### ✅ What's Working
- Project builds successfully
- Application starts without errors on port 8084
- Spring Data JPA repositories configured (6 repositories found)
- Hibernate schema auto-generation working
- Seed data loaded successfully (4 users, 2 doctors, 1 patient, 1 appointment)
- H2 database console available
- Spring Security configured for authentication

### Database Schema
The following tables are created automatically:
- `users` - User accounts with roles
- `doctors` - Doctor profiles
- `patient` - Patient records
- `appointments` - Doctor-patient appointments
- `medical_records` - Patient medical history
- `diagnoses` - Diagnosis information
- `prescriptions` - Prescription data
- `camp_schedule` - Health camp schedules
- `availabilities` - Doctor availability
- `medicine_inventory` - Medicine stock

## Common Commands

### Clean Build
```bash
./mvnw clean
```

### Run Tests
```bash
./mvnw test
```

### Generate JAR
```bash
./mvnw package
```

### Run with Maven
```bash
./mvnw spring-boot:run
```

### Run Built JAR
```bash
java -jar target/healthcare-system-0.0.1-SNAPSHOT.jar
```

## Troubleshooting

### Maven Wrapper Permission Error
If you get "operation not permitted" on macOS:
```bash
xattr -d com.apple.quarantine ./mvnw
chmod +x ./mvnw
```

### Port Already in Use
If port 8084 is already in use, modify `application.properties`:
```properties
server.port=8085
```

### H2 Database Not Accessible
Make sure `spring.h2.console.enabled=true` is set in `application.properties`

## Next Steps
1. Explore the controllers in `src/main/java/com/healthcare/healthcare_system/controller/`
2. Review the entity models in `src/main/java/com/healthcare/healthcare_system/model/`
3. Check the HTML templates in `src/main/resources/templates/`
4. Run the application and access http://localhost:8084/

## Support
For issues or questions, refer to:
- Spring Boot Docs: https://spring.io/projects/spring-boot
- Spring Data JPA: https://spring.io/projects/spring-data-jpa
- Hibernate: https://hibernate.org/
