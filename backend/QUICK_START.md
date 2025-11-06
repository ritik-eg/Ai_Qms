# Quick Start Guide - Emergency Management System Backend

## 🚀 Get Started in 5 Minutes

### Step 1: Setup Database

```bash
# Install PostgreSQL (if not already installed)
# macOS
brew install postgresql@14
brew services start postgresql@14

# Create database
psql postgres
CREATE DATABASE emergency_db;
\q
```

### Step 2: Configure Application

Update `src/main/resources/application.properties` with your database credentials:

```properties
spring.datasource.username=your_postgres_username
spring.datasource.password=your_postgres_password
```

### Step 3: Build & Run

```bash
# Build the project
./gradlew clean build

# Run the application
./gradlew bootRun
```

The application will start on **http://localhost:8080**

### Step 4: Access Swagger UI

Open your browser: **http://localhost:8080/swagger-ui.html**

### Step 5: Test the API

#### Option 1: Using Swagger UI
1. Navigate to http://localhost:8080/swagger-ui.html
2. Expand any endpoint
3. Click "Try it out"
4. Fill in the parameters
5. Click "Execute"

#### Option 2: Using cURL

```bash
# Get all emergency groups
curl http://localhost:8080/api/v1/emergency-groups

# Create a new group
curl -X POST http://localhost:8080/api/v1/emergency-groups \
  -H "Content-Type: application/json" \
  -d '{
    "name": "Fire Response Team",
    "number": "FIRE-001",
    "type": "Fire Emergency",
    "notificationType": "ALARM",
    "memberCount": 25,
    "createdBy": "admin"
  }'

# Activate an alarm
curl -X POST http://localhost:8080/api/v1/emergency-groups/1/activate-alarm \
  -H "Content-Type: application/json" \
  -d '{
    "activatedBy": "admin",
    "methods": ["EMAIL", "SMS"]
  }'

# Get dashboard statistics
curl http://localhost:8080/api/v1/statistics/dashboard
```

#### Option 3: Import Postman Collection
1. Open Postman
2. Import `POSTMAN_COLLECTION.json`
3. Start testing!

---

## 📊 Seed Data

The application automatically creates 5 sample emergency groups on first run:
- Fire Response Team Alpha (FIRE-001)
- Medical Response Unit 1 (MED-001)
- Security Team Bravo (SEC-001)
- Disaster Response Team (DIS-001)
- Test Group 1 (TEST-001)

---

## 🔍 Verify Installation

### Check Database Tables

```sql
psql -d emergency_db

-- List all tables
\dt

-- Check emergency groups
SELECT * FROM emergency_groups;

-- Check Flyway migration history
SELECT * FROM flyway_schema_history;
```

### Check Application Health

```bash
# Get all groups
curl http://localhost:8080/api/v1/emergency-groups | jq

# Get dashboard stats
curl http://localhost:8080/api/v1/statistics/dashboard | jq
```

---

## 🛠️ Troubleshooting

### Issue: Port 8080 already in use

**Solution:** Change port in `application.properties`
```properties
server.port=8081
```

### Issue: Database connection failed

**Solution:** Verify PostgreSQL is running
```bash
# macOS
brew services list

# Start if not running
brew services start postgresql@14
```

### Issue: Flyway migration errors

**Solution:** Clean and rebuild
```bash
./gradlew clean flywayClean flywayMigrate build
```

### Issue: MapStruct compilation errors

**Solution:** Rebuild with annotation processing
```bash
./gradlew clean build --refresh-dependencies
```

---

## 📚 Next Steps

1. **Read Full Documentation**: Check `README.md` and `API_DOCUMENTATION.md`
2. **Explore API**: Use Swagger UI at http://localhost:8080/swagger-ui.html
3. **Test Endpoints**: Import Postman collection
4. **Integrate Frontend**: Update frontend API base URL to http://localhost:8080

---

## 🔗 Important URLs

- **Application**: http://localhost:8080
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **API Docs (JSON)**: http://localhost:8080/api-docs
- **Health Check**: http://localhost:8080/api/v1/statistics/dashboard

---

## 💡 Tips

1. **Enable SQL Logging**: Already enabled in `application.properties`
   ```properties
   spring.jpa.show-sql=true
   spring.jpa.properties.hibernate.format_sql=true
   ```

2. **Hot Reload**: Use Spring Boot DevTools for faster development
   ```bash
   ./gradlew bootRun
   # Make changes and save - application will auto-reload
   ```

3. **Database GUI**: Use tools like pgAdmin or DBeaver to view database

4. **API Testing**: Use Postman, Insomnia, or Bruno for API testing

---

## 📞 Need Help?

- Check `API_DOCUMENTATION.md` for detailed API specs
- Review `README.md` for full setup instructions
- Check Swagger UI for interactive API documentation
- Review application logs in console output

---

**Happy Coding! 🚀**
