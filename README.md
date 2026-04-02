# Koi Auction Backend (Legacy)

## Project Status

> **Note:** This is an old (legacy) project and is **no longer actively maintained**.
> The repository is kept for reference and learning purposes.
> New bugs, security issues, or business requirement changes may not be addressed.

## Overview

This is the backend service for a Koi fish auction platform, built with Spring Boot.
The system includes major features such as:

- User registration, authentication, and authorization (JWT + Google OAuth2)
- Auction management, bidding, and participation flows
- Wallet operations, VNPay payment integration, and withdrawal processing
- Blog management
- Real-time chat and notifications (WebSocket)
- Firebase integration

## Tech Stack

- Java 21
- Spring Boot 3.3.4
- Spring Web, Spring Security, Spring Data JPA
- Spring WebSocket
- Spring Mail
- Spring OAuth2 Client
- Quartz Scheduler
- Microsoft SQL Server (driver `mssql-jdbc`)
- JWT (`jjwt`)
- Firebase Admin SDK
- OpenAPI/Swagger (`springdoc-openapi`)
- Maven Wrapper (`mvnw`, `mvnw.cmd`)

## Environment Requirements

- JDK 21
- Maven (optional if you use Maven Wrapper)
- SQL Server
- SMTP account (if email features are used)
- VNPay sandbox/production credentials
- Firebase service account
- Google OAuth2 client credentials (if Google login is used)

## Configuration Before Running

### 1. Create main application config

Copy the example file:

- `src/main/resources/application.properties.example` -> `src/main/resources/application.properties`

Update important properties:

- `spring.datasource.url`, `spring.datasource.username`, `spring.datasource.password`
- `spring.mail.*`
- `vnpay.*`
- `spring.security.oauth2.client.registration.google.*`

### 2. Create Firebase credentials file

Copy the example file:

- `src/main/resources/firebase.json.example` -> `src/main/resources/firebase.json`

Fill in all Firebase service account fields in the new file.

## Run Locally

### Windows (PowerShell / CMD)

```bash
.\mvnw.cmd spring-boot:run
```

### macOS / Linux

```bash
./mvnw spring-boot:run
```

By default, the application runs at `http://localhost:8080`.

## API Documentation

After the app starts successfully, open Swagger UI:

- `http://localhost:8080/swagger-ui/index.html`

OpenAPI JSON:

- `http://localhost:8080/v3/api-docs`

## WebSocket

- STOMP endpoint: `/ws` (SockJS)
- App destination prefix: `/app`
- Broker prefix: `/topic`

## Main API Groups

- `/api/security/*`: authentication, registration, login, Google login
- `/api/auction/*`: auction business flows
- `/api/bid/*`: bidding
- `/api/wallet/*`: wallet, deposits/withdrawals, VNPay callback
- `/api/blogs/*`: blog management
- `/api/chat/*`: chat
- `/api/dashboard/*`: admin dashboard

## Main Project Structure

```text
src/
  main/
    java/fall24/swp391/g1se1868/koiauction/
      config/
      controller/
      model/
      repository/
      scheduler/
      security/
      service/
    resources/
      application.properties.example
      firebase.json.example
  test/
    java/fall24/swp391/g1se1868/koiauction/
```

## Notes For New Contributors

- This is a legacy codebase, so some parts may not be fully optimized or cleaned up.
- If you plan to continue development, it is recommended to fork this repository and maintain your own branch strategy.
- Review security posture and upgrade dependencies before using it in production.

## Useful Commands

```bash
# Run tests
./mvnw test

# Build artifact
./mvnw clean package
```
