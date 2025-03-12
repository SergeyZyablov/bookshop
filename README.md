# Book Shop - Microservices Architecture

## Overview

Book Shop is a microservices-based application built using **Spring Boot** and various technologies like **Spring Data,
Spring Web, Spring Security, Grafana, RabbitMQ, and Config Server**. The system is designed for scalability, fault
tolerance, and high performance.

## **Core Microservices**

### **1. Book Service** (Managing Books)

- Stores book details: title, author, description, price, availability.
- CRUD operations for book management.
- Provides book search functionality by genre, author, etc.
- **Technologies Used:**
    - Spring Data JPA (database operations)
    - REST API (expose book data to other services)

### **2. Order Service** (Handling Orders)

- Creates and manages customer orders.
- Retrieves cart data from Cart Service to place orders.
- Tracks order status and maintains order history.
- **Technologies Used:**
    - Spring Data JPA (order database operations)
    - REST API (communication with Notification and User Service)

### **3. User Service** (User Authentication & Profile Management)

- Handles user registration, authentication, and authorization.
- Manages user profiles and personal data.
- **Technologies Used:**
    - Spring Security (authentication & authorization)
    - JWT (JSON Web Token for secure authentication)

### **6. Config Server** (Centralized Configuration Management)

- Stores and manages configuration files for all microservices.
- Allows dynamic configuration updates without restarting services.
- **Technologies Used:**
    - Spring Cloud Config Server (configuration management)
    - Git (backend storage for configuration files)
    - Spring Cloud Bus (Kafka for configuration updates propagation)

### **7. Monitoring & Logging Service**

- Tracks the health and performance of microservices.
- Logs events and enables centralized log analysis.
- **Technologies Used:**
    - Spring Boot Actuator (health & metrics monitoring)
    - Prometheus + Grafana (metrics visualization)

## **Communication & Routing**

### **1. API Gateway**

- Single entry point for client applications.
- Routes requests to respective microservices.
- Provides authentication and security layer.

### **2. Load Balancer**

- Distributes incoming traffic across microservice instances.
- Ensures system reliability and fault tolerance.

## **Deployment & Scaling**

- **Docker & Kubernetes**: Containerization and orchestration.
- **Service Discovery**: Eureka (optional) for dynamic service registration.
- **Horizontal Scaling**: Scaling services independently based on load.



