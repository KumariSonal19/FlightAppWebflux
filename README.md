# FlightApp WebFlux - Reactive Flight Management & Booking System

A reactive backend system for flight inventory, search, and booking built with Spring Boot WebFlux, Reactive MongoDB, Reactor, JUnit, SonarCloud, and JMeter.

## Key Highlights

- Fully reactive architecture using Spring WebFlux
- Asynchronous data pipelines using Mono and Flux
- Centralized exception handling with unified API response structure
- Layered architecture separating controller, service, and repository layers
- Comprehensive unit testing with JUnit 5, Mockito, and Jacoco for test coverage
- Log tracing for all operations to ease debugging and monitoring

## Project Structure
```
src/
├─ main/
│ ├─ java/com/flightapp
│ │ ├─ controller 
│ │ ├─ service 
│ │ ├─ repository 
│ │ ├─ model
│ │ │ ├─ entity 
│ │ │ ├─ dto 
│ │ │ └─ enums 
│ │ ├─ exception 
│ │ ├─ util 
│ │ └─ config 
│ └─ resources/
│ └─ application.properties
└─ test/
├─ controller 
├─ service 
├─ model 
|─ util 
```


## API ENDPOINTS

- **Add Flight Inventory**  
  `POST /api/flight/airline/inventory`  
  Validates and adds flight data, ensuring no duplicates or conflicts. Responds with `201 Created` on success.

- **Search Flights**  
  `POST /api/flight/search`  
  Supports one-way and round-trip search with date-range filtering. Returns matching flights or `404 Not Found`.

- **Book Ticket**  
  `POST /api/flight/booking/{flightId}`  
  Validates passenger info, checks seat availability, deducts seats, creates booking with auto-generated PNR, returns `201 Created`.

- **Cancel Ticket**  
  `DELETE /api/flight/booking/cancel/{pnr}`  
  Applies 24-hour cancellation rule, marks booking as cancelled, restores available seats, returns `200 OK`.

- **Get Ticket by PNR**  
  `GET /api/flight/ticket/{pnr}`  
  Retrieves booking and passenger details, returns `200 OK` or `404 Not Found`.

- **Booking History by Email**  
  `GET /api/flight/booking/history/{email}`  
  Streams all past bookings for a user via Flux response.

## Technology Stack

| Component       | Technology                        |
|-----------------|---------------------------------- |
| Backend         | Spring Boot 3 + WebFlux           |
| Reactive Engine | Project Reactor (Mono / Flux)     |
| Database        | MongoDB Reactive Driver           |
| Validation      | Jakarta Validation                |
| Testing         | JUnit 5, Mockito, StepVerifier    |
| Code Coverage   | Jacoco                            |
| Logging         | Logback + Spring Boot logging     |
| Build Tool      | Maven                             |

## **Running the Application**

### **1. Clone the Repository**

```
git clone <repo-url>
cd FlightBookingWebFlux
```

### **2. Start MongoDB**

MongoDB must be running at:

```
mongodb://localhost:27017
```

### **3. Build and Run**

```
mvn clean install
mvn spring-boot:run
```

Application starts at:

```
http://localhost:8081
```

---

## **Running Tests**

### **Unit Tests + Coverage**

```
mvn clean verify
```

### **Jacoco Report**

Generated at:

```
target/site/jacoco/index.html
```

---

## **SonarCloud Integration**

This project includes:

* `sonar.projectKey`
* `sonar.organization`
* `sonar.host.url`
* `sonar.coverage.jacoco.xmlReportPaths`

GitHub Actions will generate:

* Test execution
* Coverage reports
* SonarCloud scan

---

## **Load Testing with JMeter**

Load scenarios included:

* 20 users
* 50 users
* 100 users

---

## **Configuration**

Application configuration file:

```
src/main/resources/application.properties
```

Includes:

* MongoDB settings
* Server port
* Logging configuration

---


