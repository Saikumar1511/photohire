# PhotoHire

## Overview

PhotoHire is a Spring Boot REST API that allows clients to register, manage photographer profiles, define availability, and create bookings. The application follows a layered architecture using Controllers, Services, Repositories, DTOs, and Mappers.

---

## Features

* User registration
* Photographer profile management
* Photographer availability management
* Booking creation and management
* Exception handling with custom exceptions
* Request/Response DTOs
* Unit tests for Services and Controllers

---

## Tech Stack

* Java 21
* Spring Boot
* Spring Data JPA
* Hibernate
* MySQL
* Maven
* JUnit 5
* Mockito

---

## Project Structure

```
src
├── main
│   ├── controller
│   ├── dto
│   │   ├── request
│   │   └── response
│   ├── entity
│   ├── enums
│   ├── exception
│   ├── mapper
│   ├── repository
│   ├── service
│   │   └── impl
│   └── resources
└── test
    ├── controller
    └── service
```

---

## API Endpoints

### Users

| Method | Endpoint    | Description         |
| ------ | ----------- | ------------------- |
| POST   | /users      | Register a new user |
| GET    | /users      | Get all users       |
| GET    | /users/{id} | Get user by id      |

---

### Photographers

| Method | Endpoint            | Description                 |
| ------ | ------------------- | --------------------------- |
| POST   | /photographers      | Create photographer profile |
| GET    | /photographers      | Get all photographers       |
| GET    | /photographers/{id} | Get photographer by id      |
| PUT    | /photographers/{id} | Update photographer         |

---

### Availability

| Method | Endpoint           | Description                            |
| ------ | ------------------ | -------------------------------------- |
| POST   | /availability      | Create availability                    |
| GET    | /availability      | Get all availability                   |
| GET    | /availability/{id} | Get availability by id                 |
| PUT    | /availability/{id} | Update availability *(if implemented)* |
| DELETE | /availability/{id} | Delete availability                    |

---

### Bookings

| Method | Endpoint       | Description       |
| ------ | -------------- | ----------------- |
| POST   | /bookings      | Create booking    |
| GET    | /bookings      | Get all bookings  |
| GET    | /bookings/{id} | Get booking by id |
| PUT    | /bookings/{id} | Update booking    |

---

## Running the Application

### Clone the repository

```bash
git clone <repository-url>
```

### Navigate to the project

```bash
cd photohire
```

### Configure the database

Update `application.properties` with your MySQL configuration.

Example:

```
spring.datasource.url=jdbc:mysql://localhost:3306/photohire
spring.datasource.username=root
spring.datasource.password=your_password
```

### Build the project

```
mvn clean install
```

### Run the application

```
mvn spring-boot:run
```

The application starts on:

```
http://localhost:8080
```

---

## Testing

Run all tests using:

```
mvn test
```

The project includes unit tests for:

* Controllers
* Service implementations

using JUnit 5 and Mockito.

---

## Future Improvements

* Bean Validation
* Pagination and sorting
* API documentation using Swagger/OpenAPI
* Spring Security with JWT authentication
* Role-based authorization
* Image upload support
* Search and filtering
* Booking cancellation
* Email notifications

---

## Author

Developed as a Spring Boot REST API project for learning layered architecture and RESTful web services.
