# Course & Subscription Management Platform (REST API)

Language: **English** | [Deutsch](README.de.md)

A Spring Boot backend application for managing users, course catalogs, and subscriptions.

This project demonstrates clean architecture, standard Java design patterns, DTO-based data safety, and reliable unit testing with JUnit 5 and Mockito.

---

## Tech Stack

* **Language:** Java
* **Framework:** Spring Boot (Spring Web, Spring Data JPA)
* **Database:** MySQL
* **Tools:** Lombok, Maven
* **Testing:** JUnit 5, Mockito
* **Key Concepts:** Layered Architecture, Strategy Pattern, DTOs, Global Exception Handling

---

## Architecture & Design Patterns

The project follows a standard **Layered Architecture** (`Controller` -> `Service` -> `Repository` -> `Database`) to keep the code clean and well-organized.

Key design choices:

### 1. Strategy Pattern
Used in `SubscriptionService` to check subscription limits based on the user's plan (`DEFAULT`, `PREMIUM`, `VIP`).
* **Why it helps:** Keeps the code open for expansion (SOLID Open/Closed Principle). Adding a new subscription plan in the future won't require breaking or rewriting the existing service logic.

### 2. DTOs (Data Transfer Objects)
The REST controllers never expose database entities directly:
* All requests (like updating a user's status via `updateUserStatus`) use custom DTOs (e.g., `UpdateUserStatusRequest`).
* This protects the database structure and keeps the API stable when internal models change.

### 3. Centralized Exception Handling
* The business logic throws clear custom exceptions (`IdenticalStatusException`, `UserNotFoundException`, `SubscriptionLimitExceededException`).
* A global `@ControllerAdvice` catches these exceptions and returns clean, standard HTTP error responses to the client.

### 4. Unit Testing Standards
The core business services (`UserService`) are fully covered with unit tests:
* **AAA Pattern:** Tests are structured into clear **Arrange**, **Act**, and **Assert** steps.
* **Mocks:** Uses `@Mock` and `@InjectMocks` so tests run fast without calling a real database.
* **Full Coverage:** Covers both success cases (happy paths) and error handling (checking exceptions with `assertThrows` and verifying no unwanted database operations happen).

---

## Key REST API Endpoints

### Users (`UserService`)
* `GET /api/v1/users/{id}` — Get user profile by ID.
* `PATCH /api/v1/users/{id}/status` — Update user status (accepts `UpdateUserStatusRequest`).

### Subscriptions (`SubscriptionService`)
* `POST /api/v1/subscriptions` — Subscribe to a course (validates plan limits automatically).
* `GET /api/v1/users/{id}/subscriptions` — Get active subscriptions for a user.

---

## How to Run

1. **Database:** Make sure MySQL is running and your connection details are set in `src/main/resources/application.properties`.
2. **Tables:** On startup, Hibernate automatically creates the necessary database tables.
3. **Sample Data:** Check the `/Sql_scripts` folder in the root directory for sample SQL scripts (`toUsers.sql`, `toCourses.sql`, `toSubscriptions.sql`) to manually populate test data.
4. The API will be available at: `http://localhost:8080`.