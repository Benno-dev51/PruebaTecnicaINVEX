# Employee Service API 🚀

A resilient, secure, and scalable backend service for managing employee operations. This project was developed following modern software engineering practices, Hexagonal Architecture (Ports and Adapters), and microservices principles.

## 🏛️ Architecture & Design Patterns
This service is built using **Hexagonal Architecture** to ensure a high degree of decoupling between the core business logic (Domain) and the external frameworks (Infrastructure/Web).

- **Domain Layer:** Contains the `Employee` entity as a **Rich Domain Model** (no anemic POJOs) and the core interfaces (Ports).
- **Application Layer:** Orchestrates business use cases, fully isolated from web or database annotations.
- **Infrastructure Layer:** Contains REST controllers (Web Adapters), Spring Data JPA repositories (Persistence Adapters), and global error handling mechanisms.

## 🛠️ Tech Stack
- **Java 17**
- **Spring Boot 3.x**
- **Spring Data JPA / Hibernate**
- **H2 In-Memory Database** (For development and testing)
- **Lombok** (Boilerplate reduction)
- **JUnit 5 & Mockito** (Testing)
- **Swagger / OpenAPI 3** (API Documentation)
- **Docker** (Containerization)

## ⚙️ Prerequisites
- JDK 17 installed locally.
- Maven 3.8+ installed.
- Docker (optional, for containerized execution).

## 🚀 Getting Started

### Option 1: Running Locally with Maven
1. Clone the repository.
2. Navigate to the root directory.
3. Run the application:
   ```bash
   mvn spring-boot:run