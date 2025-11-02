# Digital Health Backend Exercise – Levin Munyelele

# Project Overview

This project supports Patient and Encounter management with CRUD, search, pagination, validation, Dockerized deployment, and API key header authentication.
It follows a clean layered architecture: Controller → Service → Repository → Database. The design aligns with digital health interoperability principles like modularity, data quality, and scalability

# Technologies Used

# Category         | Technology                |
 | ---------------- | ------------------------- |
 | Language         | Java 17                   |
 | Framework        | Spring Boot 3.3.x         |
 | Database         | H2 (in-memory)            |
 | Build Tool       | Maven                     |
 | API Design       | RESTful                   |
 | Containerization | Docker                    |
 | Testing          | JUnit 5, Spring Boot Test |


# Features

✅ CRUD for Patients
✅ CRUD for Encounters
✅ Simple API Key authentication (basic security)
✅ Filter patients by family, given, identifier, or birthDate range
✅ Pagination and sorting support
✅ Validation and global error handling
✅ Layered architecture (Controller–Service–Repository)
✅ Docker support for easy deployment

# Project Architecture
starter-project/
│
├── src/
│   ├── main/
│   │   ├── java/org/example/patient/
│   │   │   ├── controller/   → REST API endpoints
│   │   │   ├── dto/          → Data Transfer Objects
│   │   │   ├── model/        → JPA Entities (Patient, Encounter)
│   │   │   ├── repository/   → Spring Data JPA Repositories
│   │   │   ├── service/      → Business logic
│   │   │   ├── security/     → API key filter
│   │   │   ├── config/       → Filter registration
│   │   │   └── spec/         → Filtering specifications
│   │   └── resources/
│   │       ├── application.properties
│   │    
│   └── test/
│       ├── java/org/example/patient/
│       │   ├── PatientServiceTest.java
│       │   └── PatientControllerTest.java
│       └── resources/
│           └── application.properties (test config)
├── pom.xml
├── Dockerfile
└── README.md


# Getting Started
Prerequisites

Java 17+

Maven 3.8+

Docker (for containerized run)

# Run Locally (no Docker)
# Build the project
mvn clean package

# Run the Spring Boot app
mvn spring-boot:run

# Visit  http://localhost:8080/api/patients


# Run with Docker
# Build Docker image
docker build -t digital-health-exercise .

# Run container with API key
docker run -d -p 8080:8080 -e API_KEY=secret123 --name digital-health digital-health-exercise

# API will be available at:
http://localhost:8080/api/patients



# API Endpoints
| Method   | Endpoint                       | Description                                                                                              |
| -------- | ------------------------------ | -------------------------------------------------------------------------------------------------------- |
| `POST`   | `/api/patients`                | Create new patient                                                                                       |
| `GET`    | `/api/patients/{id}`           | Get patient by ID                                                                                        |
| `PUT`    | `/api/patients/{id}`           | Update patient                                                                                           |
| `DELETE` | `/api/patients/{id}`           | Delete patient                                                                                           |
| `GET`    | `/api/patients`                | Search/filter patients (`family`, `given`, `identifier`, `birthDateFrom`, `birthDateTo`, `page`, `size`) |
| `POST`   | `/api/encounters`              | Create patient encounter                                                                                 |
| `GET`    | `/api/encounters/patient/{id}` | List encounters for a specific patient                                                                   |


# API Key Authentication

All endpoints are secured using an API key. Requests must include:

X-API-KEY: your-secret-key

# Example using curl:

# Successful request
curl -i -H "X-API-KEY: secret123" http://localhost:8080/api/patients

# Unauthorized request
curl -i -H "X-API-KEY: wrongkey" http://localhost:8080/api/patients
# Response: 401 Unauthorized

Note: The API key is set in Docker using the -e API_KEY=<your-key> flag or in local environment variables when running without Docker.

# Example Requests

# Create Patient

curl -X POST http://localhost:8080/api/patients \
-H "Content-Type: application/json" \
-H "X-API-KEY: secret123" \
-d '{
  "identifier": "P001",
  "givenName": "Levin",
  "familyName": "Munyelele",
  "birthDate": "1998-03-20",
  "gender": "M"
}'
'

# Search Patients  
curl -i -H "X-API-KEY: secret123" "http://localhost:8080/api/patients?given=Levin&page=0&size=5"

# Validation & Error Handling
Missing fields or invalid data return 400 Bad Request with descriptive errors.

Non-existent records return 404 Not Found.

Invalid API key returns 401 Unauthorized..


# Access the API

Base URL: http://localhost:8080/api/patients

H2 Console: http://localhost:8080/h2-console

JDBC URL: jdbc:h2:mem:testdb

User: sa

Password: (leave blank)

# DockerfileOverview
FROM eclipse-temurin:17-jdk-alpine
WORKDIR /app
COPY target/digital-health-exercise-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java", "-jar", "app.jar"]
Uses Eclipse Temurin JDK 17

Runs the compiled Spring Boot JAR directly

# Running Tests

You can run unit and integration tests with Maven:
mvn test


# Data Model Overview

Patient Entity
Patient {
  id: Long
  identifier: String
  givenName: String
  familyName: String
  birthDate: LocalDate
  gender: String
}

Encounter Entity
Encounter {
  id: Long
  patientId: Long
  start: LocalDateTime
  end: LocalDateTime
  encounterClass: String
}
Each Encounter is linked to a Patient, representing visits, consultations, or follow-ups.

# Design Notes

Built with modular separation of concerns (Controller → Service → Repository).

Uses Spring Data JPA Specifications for flexible filtering.

Includes data validation via @Valid and @NotBlank.

Provides pagination using PageRequest in search endpoints.

Supports containerization through Docker for quick deployment.

Default database is H2 (in-memory), but can easily switch to PostgreSQL for production by editing application.properties.

Sample Response (Paginated Search)
{
  "content": [
    {
      "id": 1,
      "identifier": "P001",
      "givenName": "Levin",
      "familyName": "Munyelele",
      "birthDate": "1998-03-20",
      "gender": "M"
    }
  ],
  "currentPage": 0,
  "pageSize": 10,
  "totalElements": 1,
  "totalPages": 1,
  "isLastPage": true
}

# License

This project is provided as part of the IntelliSOFT Consulting Digital Health Backend Developer Technical Assessment (2025).
All rights reserved to the author — Levin Munyelele.

# Final Notes

“Software that saves lives starts with reliable data.”
This backend prototype focuses on clean, validated, and extensible digital health data services, a strong foundation for systems like OpenMRS or DHIS2 integration.