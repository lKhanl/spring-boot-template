# Spring Boot Template

## Overview

This is a template for a Spring Boot application. You can fork this repository to create your own Spring Boot
application. This template includes the following features:

- Spring Boot (2.7.8)
- Spring Data JPA
- Spring Security
- Spring Web
- Spring DevTools
- Lombok
- PostgreSQL
- Swagger
- JUnit 5
- Maven
- Git
- Docker
- GitHub Actions

## Getting Started

### Prerequisites

- Java 17
- Maven 3
- Git
- IntelliJ IDEA (optional)
- Postman (optional)
- Postgres Server

### Installation

1. Clone the repository
2. Create a database in Postgres
3. Create a `application-prod.properties` file in the resources directory of the project
4. Add the following environment variables to this file:

```
spring.datasource.url=jdbc:postgresql://localhost:5432/{database_name}
spring.datasource.username={username}
spring.datasource.password={password}
spring.jpa.hibernate.ddl-auto=update
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
```

5. Run the application
