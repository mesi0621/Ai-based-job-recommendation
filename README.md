# AIJobMatch Platform

AI-powered job matching platform that connects job seekers with relevant opportunities through intelligent resume analysis, skill extraction, and personalized job recommendations.

## Features

- User authentication with JWT and OAuth support
- AI-powered resume analysis and parsing
- Intelligent job matching and recommendations
- Skill gap analysis
- Job application tracking
- Admin job management
- Search and filtering capabilities

## Technology Stack

- Java 17
- Spring Boot 3.2.0
- Spring Data JPA
- Spring Security
- PostgreSQL
- JWT Authentication
- Apache PDFBox & POI for document processing
- jqwik for property-based testing

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- PostgreSQL 14+

## Setup

1. Create PostgreSQL database:
```sql
CREATE DATABASE aijobmatch;
```

2. Update database credentials in `src/main/resources/application.properties`

3. Build the project:
```bash
mvn clean install
```

4. Run the application:
```bash
mvn spring-boot:run
```

The application will start on `http://localhost:8080`

## Testing

Run all tests:
```bash
mvn test
```

## Project Structure

```
src/
├── main/
│   ├── java/com/aijobmatch/
│   │   ├── controller/     # REST controllers
│   │   ├── service/        # Business logic
│   │   ├── repository/     # Data access
│   │   ├── model/          # Domain entities
│   │   ├── dto/            # Data transfer objects
│   │   ├── config/         # Configuration classes
│   │   └── exception/      # Exception handling
│   └── resources/
│       └── application.properties
└── test/
    └── java/com/aijobmatch/
```

## API Documentation

Once running, access Swagger UI at: `http://localhost:8080/swagger-ui.html`

## License

Proprietary
