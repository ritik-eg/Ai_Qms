# Emergency Management System - Backend

A comprehensive Spring Boot application for managing emergency groups and alarm systems with PostgreSQL database.

## 🚀 Technology Stack

- **Framework**: Spring Boot 3.2.0
- **Java Version**: 21
- **Database**: PostgreSQL
- **Build Tool**: Gradle (Groovy)
- **Documentation**: Swagger/OpenAPI 3.0
- **Migration**: Flyway
- **Mapping**: MapStruct

## 📋 Features

- ✅ Emergency group management (CRUD operations)
- ✅ Active alarm activation and deactivation
- ✅ Alarm history tracking
- ✅ Dashboard statistics
- ✅ Pagination and filtering
- ✅ Soft delete for emergency groups
- ✅ Global exception handling
- ✅ API versioning
- ✅ Swagger UI documentation
- ✅ CORS configuration for frontend integration

## 🗄️ Database Schema

### Tables
1. **emergency_groups** - Stores emergency group information
2. **active_alarms** - Tracks currently active and deactivated alarms
3. **alarm_history** - Historical record of all alarm actions
4. **alarm_methods** - Notification methods for each alarm

## 📦 Prerequisites

- Java 21 or higher
- PostgreSQL 14 or higher
- Gradle 8.x (wrapper included)

## 🔧 Setup Instructions

### 1. Clone the Repository

```bash
git clone <repository-url>
cd backend
```

### 2. Configure Database

Create a PostgreSQL database:

```sql
CREATE DATABASE emergency_db;
```

Update database credentials in `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/emergency_db
spring.datasource.username=your_username
spring.datasource.password=your_password
```

### 3. Build the Project

```bash
./gradlew clean build
```

### 4. Run the Application

```bash
./gradlew bootRun
```

Or run the JAR file:

```bash
java -jar build/libs/emergency-management-system-1.0.0.jar
```

The application will start on `http://localhost:8080`

### 5. Access Swagger UI

Open your browser and navigate to:

```
http://localhost:8080/swagger-ui.html
```

## 📚 API Endpoints

### Emergency Groups

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/emergency-groups` | Get all emergency groups (paginated) |
| GET | `/api/v1/emergency-groups/{id}` | Get emergency group by ID |
| POST | `/api/v1/emergency-groups` | Create new emergency group |
| PUT | `/api/v1/emergency-groups/{id}` | Update emergency group |
| DELETE | `/api/v1/emergency-groups/{id}` | Delete emergency group (soft delete) |

### Active Alarms

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/active-alarms` | Get all active alarms (paginated) |
| GET | `/api/v1/active-alarms/{id}` | Get active alarm by ID |
| POST | `/api/v1/emergency-groups/{groupId}/activate-alarm` | Activate alarm for a group |
| POST | `/api/v1/active-alarms/{id}/deactivate` | Deactivate an active alarm |
| GET | `/api/v1/active-alarms/count` | Get count of active alarms |

### Alarm History

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/alarm-history` | Get alarm history with filters |
| GET | `/api/v1/emergency-groups/{groupId}/alarm-history` | Get alarm history for specific group |

### Statistics

| Method | Endpoint | Description |
|--------|----------|-------------|
| GET | `/api/v1/statistics/dashboard` | Get dashboard statistics |

## 🔍 API Examples

### Create Emergency Group

```bash
curl -X POST http://localhost:8080/api/v1/emergency-groups \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Fire Response Team",
    "number": "FIRE-001",
    "type": "Fire Emergency",
    "notificationType": "ALARM",
    "responsible": "John Doe",
    "memberCount": 25,
    "createdBy": "admin"
  }'
```

### Activate Alarm

```bash
curl -X POST http://localhost:8080/api/v1/emergency-groups/1/activate-alarm \
  -H "Content-Type: application/json" \
  -d '{
    "activatedBy": "admin",
    "methods": ["EMAIL", "SMS", "PUSH"]
  }'
```

### Deactivate Alarm

```bash
curl -X POST http://localhost:8080/api/v1/active-alarms/1/deactivate \
  -H "Content-Type: application/json" \
  -d '{
    "deactivatedBy": "admin",
    "notes": "Emergency resolved"
  }'
```

### Get Dashboard Statistics

```bash
curl http://localhost:8080/api/v1/statistics/dashboard
```

## 🧪 Testing

Run tests using:

```bash
./gradlew test
```

## 📁 Project Structure

```
backend/
├── src/
│   ├── main/
│   │   ├── java/com/emergency/
│   │   │   ├── config/          # Configuration classes
│   │   │   ├── controller/      # REST controllers
│   │   │   ├── dto/             # Data Transfer Objects
│   │   │   ├── entity/          # JPA entities
│   │   │   ├── exception/       # Custom exceptions
│   │   │   ├── mapper/          # MapStruct mappers
│   │   │   ├── repository/      # JPA repositories
│   │   │   └── service/         # Business logic services
│   │   └── resources/
│   │       ├── application.properties
│   │       └── db/migration/    # Flyway migration scripts
│   └── test/                    # Test classes
├── build.gradle
├── settings.gradle
└── README.md
```

## 🔐 Security Notes

- Currently, the API is open without authentication
- For production, implement Spring Security with JWT
- Configure HTTPS/SSL
- Add rate limiting
- Implement API key authentication

## 🌐 CORS Configuration

CORS is configured to allow requests from:
- `http://localhost:5173` (Vue.js dev server)
- `http://localhost:3000` (React dev server)
- `http://localhost:8080`

Modify `WebConfig.java` to add more origins as needed.

## 🐛 Troubleshooting

### Database Connection Issues

1. Ensure PostgreSQL is running
2. Verify database credentials
3. Check if the database exists

### Port Already in Use

Change the port in `application.properties`:

```properties
server.port=8081
```

### Flyway Migration Errors

Clean and rebuild:

```bash
./gradlew clean flywayClean flywayMigrate build
```

## 📝 License

MIT License

## 👥 Contributors

- Emergency Management Team

## 📞 Support

For issues and questions, please create an issue in the repository.
