# 🧠 Spring Boot SaaS Task Manager – JWT Auth Example

A starter SaaS-style task management project with full **JWT-based authentication** using Spring Boot 3. Features include user registration, login, password encoding, and protected endpoints with JWT validation.

---

## 🧱 Tech Stack

- Java 17
- Spring Boot 3.x
- Spring Security
- Spring Data JPA
- JWT (`jjwt` library)
- H2 / PostgreSQL (DB agnostic)
- Lombok

---

## 🔐 Authentication Components Overview

### 1. `User`
- Entity class representing application users.
- Contains fields like `username`, `password`, `email`, `role`, etc.

### 2. `UserRepository`
- JPA repository to perform CRUD on `User`.
- Includes a method: `findByUsername()` for login.

### 3. `CustomUserDetailsService`
- Loads user details from DB.
- Used internally by Spring Security.

### 4. `JwtTokenProvider`
- Generates and validates JWT tokens.
- Signs tokens with secret key.
- Extracts `username` from token.

### 5. `JwtAuthenticationFilter`
- Intercepts requests.
- Reads `Authorization: Bearer <token>` header.
- Validates token and sets Spring Security context.

### 6. `AuthService`
- Handles registration & login logic.
- On registration: saves user with encrypted password.
- On login: authenticates and returns token.

### 7. `AuthController`
- Exposes `/auth/register` and `/auth/login` endpoints.
- Accepts DTOs, returns token in `AuthResponse`.

### 8. DTOs
- `RegisterRequest`: For new users.
- `AuthRequest`: For login.
- `AuthResponse`: Returns JWT token.

---

## 🗃️ Directory Structure

```plaintext
src/
└── main/
    └── java/com/example/taskmanager/
        ├── controller/
        │   └── AuthController.java
        ├── dto/
        │   ├── RegisterRequest.java
        │   ├── AuthRequest.java
        │   └── AuthResponse.java
        ├── model/
        │   └── User.java
        ├── repository/
        │   └── UserRepository.java
        ├── security/
        │   ├── JwtTokenProvider.java
        │   ├── JwtAuthenticationFilter.java
        │   └── CustomUserDetailsService.java
        ├── service/
        │   └── AuthService.java
        └── config/
            └── SecurityConfig.java
```

## 🔁 Authentication Flow

```plaintext
 +-------------------+
 |    Client (UI)    |
 +-------------------+
           |
           | POST /auth/register or /auth/login
           v
 +-------------------+
 |   AuthController  |
 +-------------------+
           |
           v
 +-------------------+
 |    AuthService    |
 +-------------------+
           |
           v
 +----------------------------+
 |   UserRepository (JPA)     | <-> Database
 +----------------------------+
           |
           v
 +-------------------+
 | JwtTokenProvider  |
 +-------------------+
           |
           v
 +-------------------+
 |    AuthController |
 +-------------------+
           |
           v
 +-------------------+
 |     Client (UI)   | <-- Stores token
 +-------------------+

Protected Endpoint Request:

Client --> [Authorization: Bearer <token>]
           |
           v
 +---------------------------+
 |   JwtAuthenticationFilter |
 +---------------------------+
           |
           v
 +-------------------+
 | Secured Endpoints |
 +-------------------+
```
## 🧪 Sample Requests
### 🔐 Register
```bash
Copy
Edit
POST /auth/register
Content-Type: application/json

{
"username": "amol",
"password": "pass123",
"fullName": "Amol Rajgure",
"email": "amol@example.com"
}
```

## 🔑 Login
```pgsql
POST /auth/login
Content-Type: application/json

{
"username": "amol",
"password": "pass123"
}
```

## ✅ Response
```json
{
"token": "eyJhbGciOiJIUzI1NiJ9..."
}
```


## 📘 Spring Boot JWT Auth – Annotation Reference Table

This file lists all the annotations used in the Spring Boot SaaS Task Manager application with JWT authentication.

| Annotation                      | Package                                     | Used In                           | Description                                                                 |
|--------------------------------|---------------------------------------------|-----------------------------------|-----------------------------------------------------------------------------|
| `@RestController`              | `org.springframework.web.bind.annotation`   | `AuthController`                  | Marks class as a REST controller (returns JSON responses).                 |
| `@RequestMapping`              | `org.springframework.web.bind.annotation`   | `AuthController`                  | Sets the base URI path for all endpoints in the controller.               |
| `@PostMapping`                 | `org.springframework.web.bind.annotation`   | `AuthController`                  | Maps HTTP POST requests to a method.                                       |
| `@RequestBody`                 | `org.springframework.web.bind.annotation`   | `AuthController`                  | Binds the request JSON body to a Java object (DTO).                        |
| `@ResponseBody` (implied)      | `org.springframework.web.bind.annotation`   | All methods in `@RestController`  | Converts Java object to JSON automatically.                                |
| `@Service`                     | `org.springframework.stereotype`            | `AuthService`, `CustomUserDetailsService` | Marks class as a service component (Spring-managed bean).       |
| `@Component`                   | `org.springframework.stereotype`            | `JwtTokenProvider`, `JwtFilter`  | Generic stereotype for any Spring-managed bean.                            |
| `@Repository` (optional)       | `org.springframework.stereotype`            | `UserRepository` (implicit via `JpaRepository`) | Indicates DB access layer.     |
| `@Entity`                      | `jakarta.persistence` / `javax.persistence` | `User`                            | Declares this class as a JPA entity mapped to a table.                     |
| `@Table`                       | `jakarta.persistence`                       | `User`                            | Optionally specifies the DB table name.                                    |
| `@Id`                          | `jakarta.persistence`                       | `User`                            | Marks the primary key field.                                               |
| `@GeneratedValue`              | `jakarta.persistence`                       | `User`                            | Configures auto-generation for primary key.                                |
| `@Column`                      | `jakarta.persistence`                       | `User`                            | Maps a field to a DB column.                                               |
| `@Builder`                     | `lombok`                                     | `User`, DTOs                      | Enables the builder pattern for object creation.                           |
| `@Data`                        | `lombok`                                     | DTOs (`AuthRequest`, etc.)       | Generates getters, setters, toString, equals, and hashCode.                |
| `@AllArgsConstructor`          | `lombok`                                     | `AuthResponse`, `User`           | Generates a constructor with all fields.                                   |
| `@NoArgsConstructor`           | `lombok`                                     | `User`                            | Generates a default constructor.                                           |
| `@RequiredArgsConstructor`     | `lombok`                                     | `AuthService`, `JwtProvider`     | Generates constructor for `final` fields (used for DI).                    |
| `@EnableWebSecurity`           | `org.springframework.security.config.annotation.web.configuration` | `SecurityConfig`      | Enables Spring Security’s web support.                                    |
| `@Configuration`              | `org.springframework.context.annotation`     | `SecurityConfig`                 | Marks class as a Spring configuration source.                              |
| `@Bean`                        | `org.springframework.context.annotation`     | `SecurityConfig`                 | Declares a method that returns a Spring-managed bean.                      |
| `@Transactional`              | `org.springframework.transaction.annotation` | (if used in services)            | Ensures the method runs within a transaction boundary.                     |

---

**Note:** Lombok annotations like `@Data`, `@Builder`, etc. help reduce boilerplate and must be supported by installing Lombok plugin in your IDE.
