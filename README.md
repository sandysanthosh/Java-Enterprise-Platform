# Java Enterprise Platform

A production-oriented backend portfolio project for enterprise Java roles.

## Stack

- Java 17 and Spring Boot 3
- Spring Web, Spring Data JPA, Spring Security, JWT
- PostgreSQL
- OpenAPI / Swagger UI
- Docker and Docker Compose
- JUnit 5, Maven, GitHub Actions, and CodeQL

## Customer API and access roles

| Endpoint | user | admin |
| --- | --- | --- |
| POST /api/auth/login | Public | Public |
| GET /api/v1/customers | Allowed | Allowed |
| GET /api/v1/customers/{id} | Allowed | Allowed |
| POST /api/v1/customers | Forbidden | Allowed |

Request a JWT:

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"changeit-admin"}'
```

Use the returned token:

```bash
curl http://localhost:8080/api/v1/customers \
  -H "Authorization: Bearer <access-token>"
```

For local development only, the demo accounts are `admin` / `changeit-admin` and `user` / `changeit-user`. Set `JWT_SECRET`, `ADMIN_PASSWORD`, and `USER_PASSWORD` to secure values in every deployed environment.

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
HTTP API -> JWT filter -> Controller -> Service -> Repository -> PostgreSQL
```

The project is designed to grow into a modular enterprise platform with auditability, events, and operational observability.