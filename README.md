# Java Enterprise Platform

A production-oriented backend portfolio project for enterprise Java roles.

## Stack

- Java 17 and Spring Boot 3
- Spring Web, Spring Data JPA, Spring Security
- PostgreSQL
- OpenAPI / Swagger UI
- Docker and Docker Compose
- JUnit 5, Maven, GitHub Actions, and CodeQL

## First module: Customer API

The initial service provides a clean foundation for managing customers:

- `GET /api/v1/customers`
- `POST /api/v1/customers`
- `GET /api/v1/customers/{id}`
- `GET /actuator/health`

## Run locally

```bash
docker compose up --build
```

Then open:

- API: `http://localhost:8080/api/v1/customers`
- Swagger UI: `http://localhost:8080/swagger-ui/index.html`
- Health: `http://localhost:8080/actuator/health`

## Architecture

```
HTTP API -> Controller -> Service -> Repository -> PostgreSQL
```

The project is designed to grow into a modular enterprise platform with authentication, auditability, events, and operational observability.