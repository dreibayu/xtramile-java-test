# Patient Web Application CRUD

A simple full-stack Patient management application built for the technical assessment.

## Technology Stack

Backend:
- Java 17
- Spring Boot 3.x
- Spring Web
- Spring Data JPA
- H2 in-memory database
- Maven
- JUnit 5, Mockito, MockMvc

Frontend:
- Angular 20
- Angular Reactive Forms
- Angular HttpClient
- Bootstrap 5

## Project Structure

```text
backend/   Spring Boot Maven REST API
frontend/  Angular patient CRUD UI
```

## Backend Features

- Patient CRUD REST API
- Unique PID validation
- Server-side pagination
- Search by PID, first name, last name, or full name
- H2 database seed data
- Global exception handling
- Service and controller unit tests

## Frontend Features

- Patient table/grid
- Search input
- Server-side pagination controls
- Create patient form
- Edit patient form
- Delete confirmation
- Reactive form validation messages

## Run Backend

```bash
cd backend
mvn spring-boot:run
```

Backend runs on:

```text
http://localhost:8080
```

H2 console:

```text
http://localhost:8080/h2-console
```

H2 JDBC URL:

```text
jdbc:h2:mem:patientdb
```

## Run Backend Tests

```bash
cd backend
mvn test
```

## Run Frontend

```bash
cd frontend
npm install
ng serve
```

Frontend runs on:

```text
http://localhost:4200
```

## API Endpoints

```text
GET    /api/patients?page=0&size=10&search=
GET    /api/patients/{id}
POST   /api/patients
PUT    /api/patients/{id}
DELETE /api/patients/{id}
```

## Example Patient JSON

```json
{
  "pid": "PID-1005",
  "firstName": "Mia",
  "lastName": "Anderson",
  "dateOfBirth": "1992-06-18",
  "gender": "FEMALE",
  "phoneNo": "0412 333 444",
  "address": "10 George Street",
  "suburb": "Sydney",
  "state": "NSW",
  "postcode": "2000"
}
```