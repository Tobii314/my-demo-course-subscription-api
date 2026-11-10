# Course & Subscription Management Platform (REST API)

Language: [English](README.md) | **Deutsch**

Eine Spring Boot Backend-Anwendung zur einfachen Verwaltung von Benutzern, Kursen und Abonnements.

Das Projekt zeigt die Anwendung von Clean Architecture, bekannten Java Design Patterns, sicherer Datenübertragung mit DTOs und Unit-Testing mit JUnit 5 und Mockito.

---

## 🛠️ Tech Stack

* **Programmiersprache:** Java
* **Framework:** Spring Boot (Spring Web, Spring Data JPA)
* **Datenbank:** MySQL
* **Tools:** Lombok, Maven
* **Testing:** JUnit 5, Mockito
* **Architektur:** Layered Architecture, Strategy Pattern, DTOs, Global Exception Handling

---

## 🏛️ Architektur & Design Patterns

Das Projekt nutzt eine klassische **Layered Architecture** (`Controller` -> `Service` -> `Repository` -> `Database`). Dadurch bleibt der Code sauber und gut strukturiert.

Wichtige Architektur-Entscheidungen:

### 1. Strategy Pattern
Wird im `SubscriptionService` genutzt, um die Limits von Abonnements je nach Status des Benutzers (`DEFAULT`, `PREMIUM`, `VIP`) zu prüfen.
* **Vorteil:** Entspricht dem Open/Closed Principle (SOLID). Neue Abonnements können später einfach als neue Klassen hinzugefügt werden, ohne den bestehenden Code zu ändern.

### 2. DTOs (Data Transfer Objects)
Die REST-Controller nutzen keine Datenbank-Entitäten direkt:
* Alle Anfragen (z. B. Status-Updates via `updateUserStatus`) verwenden eigene DTOs (z. B. `UpdateUserStatusRequest`).
* Das schützt die Datenbankstruktur und hält die API stabil, wenn sich interne Modelle ändern.

### 3. Centralized Exception Handling
* Die Logik wirft eigene, klare Exceptions (`IdenticalStatusException`, `UserNotFoundException`, `SubscriptionLimitExceededException`).
* Ein zentraler `@ControllerAdvice` fängt diese Fehler ab und sendet saubere HTTP-Antworten an den Client zurück.

### 4. Unit-Testing
Die Geschäftslogik (`UserService`) ist mit isolierten Unit-Tests abgedeckt:
* **AAA-Muster:** Klare Strukturierung der Tests in **Arrange**, **Act** und **Assert**.
* **Mocks:** Verwendet `@Mock` und `@InjectMocks`, damit die Tests schnell und ohne echte Datenbank laufen.
* **Abdeckung:** Testet sowohl erfolgreiche Abläufe (Happy Paths) als auch Fehlerfälle (Prüfung von Exceptions mit `assertThrows`).

---

## 📡 REST-API Endpunkte

### Benutzer (`UserService`)
* `GET /api/v1/users/{id}` — Benutzerprofil nach ID abrufen.
* `PATCH /api/v1/users/{id}/status` — Status eines Benutzers aktualisieren (nutzt `UpdateUserStatusRequest`).

### Abonnements (`SubscriptionService`)
* `POST /api/v1/subscriptions` — Kurs abonnieren (prüft automatisch die Limits).
* `GET /api/v1/users/{id}/subscriptions` — Aktive Abonnements eines Benutzers abrufen.

---

## ⚙️ Anwendung starten

1. **Datenbank:** Stellen Sie sicher, dass MySQL läuft und die Zugangsdaten in `src/main/resources/application.properties` eingetragen sind.
2. **Tabellen:** Beim ersten Start erstellt Hibernate automatisch alle Tabellen in der Datenbank.
3. **Testdaten:** Im Ordner `/Sql_scripts` befinden sich fertige SQL-Skripte (`toUsers.sql`, `toCourses.sql`, `toSubscriptions.sql`), um Testdaten manuell hinzuzufügen.
4. Nach dem Start ist die Anwendung erreichbar unter: `http://localhost:8080`.