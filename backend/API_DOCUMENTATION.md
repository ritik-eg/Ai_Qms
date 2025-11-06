# Emergency Management System - API Documentation

## Base URL
```
http://localhost:8080/api/v1
```

## Response Format

All API responses follow this structure:

```json
{
  "success": true,
  "message": "Operation successful",
  "data": { }
}
```

## HTTP Status Codes

- `200 OK` - Successful GET, PUT, POST operations
- `201 Created` - Successful resource creation
- `204 No Content` - Successful DELETE operation
- `400 Bad Request` - Validation errors
- `404 Not Found` - Resource not found
- `409 Conflict` - Duplicate resource or business logic conflict
- `500 Internal Server Error` - Server error

---

## Emergency Groups API

### 1. Get All Emergency Groups

**Endpoint:** `GET /emergency-groups`

**Query Parameters:**
- `page` (optional, default: 0) - Page number
- `size` (optional, default: 10) - Page size
- `type` (optional) - Filter by group type
- `search` (optional) - Search by name, number, or type

**Response:**
```json
{
  "success": true,
  "data": {
    "content": [
      {
        "id": 1,
        "type": "Fire Emergency",
        "name": "Fire Response Team Alpha",
        "number": "FIRE-001",
        "notificationType": "ALARM",
        "responsible": "John Smith",
        "dateRevised": "2025-01-01",
        "notes": "Primary fire response unit",
        "emailTopic": "Fire Emergency Alert",
        "emailContent": "Fire emergency detected",
        "smsText": "FIRE ALERT",
        "memberCount": 25,
        "lastNotificationDate": null,
        "createdAt": "2025-01-06T10:00:00",
        "updatedAt": "2025-01-06T10:00:00",
        "createdBy": "system"
      }
    ],
    "pageable": {
      "pageNumber": 0,
      "pageSize": 10
    },
    "totalPages": 1,
    "totalElements": 5
  }
}
```

---

### 2. Get Emergency Group by ID

**Endpoint:** `GET /emergency-groups/{id}`

**Path Parameters:**
- `id` - Emergency group ID

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "type": "Fire Emergency",
    "name": "Fire Response Team Alpha",
    "number": "FIRE-001",
    "notificationType": "ALARM",
    "responsible": "John Smith",
    "memberCount": 25
  }
}
```

**Error Response (404):**
```json
{
  "success": false,
  "message": "Emergency group not found with id: 999"
}
```

---

### 3. Create Emergency Group

**Endpoint:** `POST /emergency-groups`

**Request Body:**
```json
{
  "name": "Fire Response Team",
  "number": "FIRE-001",
  "type": "Fire Emergency",
  "notificationType": "ALARM",
  "responsible": "John Doe",
  "dateRevised": "2025-01-06",
  "notes": "Primary response unit",
  "emailTopic": "Fire Emergency Alert",
  "emailContent": "Fire emergency detected. Please respond immediately.",
  "smsText": "FIRE ALERT: Respond immediately",
  "memberCount": 25,
  "createdBy": "admin"
}
```

**Validation Rules:**
- `name` - Required, max 255 characters
- `number` - Optional, max 100 characters, must be unique
- Other fields are optional

**Response (201):**
```json
{
  "success": true,
  "message": "Emergency group created successfully",
  "data": {
    "id": 1,
    "name": "Fire Response Team",
    "number": "FIRE-001",
    "createdAt": "2025-01-06T10:00:00"
  }
}
```

**Error Response (409):**
```json
{
  "success": false,
  "message": "Emergency group with number FIRE-001 already exists"
}
```

---

### 4. Update Emergency Group

**Endpoint:** `PUT /emergency-groups/{id}`

**Path Parameters:**
- `id` - Emergency group ID

**Request Body:**
```json
{
  "name": "Updated Fire Response Team",
  "memberCount": 30,
  "notes": "Updated notes"
}
```

**Note:** Only provided fields will be updated (partial update)

**Response (200):**
```json
{
  "success": true,
  "message": "Emergency group updated successfully",
  "data": {
    "id": 1,
    "name": "Updated Fire Response Team",
    "memberCount": 30,
    "updatedAt": "2025-01-06T11:00:00"
  }
}
```

---

### 5. Delete Emergency Group

**Endpoint:** `DELETE /emergency-groups/{id}`

**Path Parameters:**
- `id` - Emergency group ID

**Response (204):**
No content (soft delete performed)

---

## Active Alarms API

### 6. Get All Active Alarms

**Endpoint:** `GET /active-alarms`

**Query Parameters:**
- `page` (optional, default: 0)
- `size` (optional, default: 10)
- `groupId` (optional) - Filter by emergency group ID
- `status` (optional) - Filter by status (ACTIVE/DEACTIVATED)

**Response:**
```json
{
  "success": true,
  "data": {
    "content": [
      {
        "id": 1,
        "emergencyGroupId": 1,
        "emergencyGroupName": "Fire Response Team Alpha",
        "activatedDate": "2025-01-06T10:30:00",
        "activatedBy": "admin",
        "methods": ["EMAIL", "SMS", "PUSH"],
        "alarmDeactivated": null,
        "deactivatedBy": null,
        "status": "ACTIVE",
        "statisticsAcknowledged": 0,
        "statisticsTotal": 25
      }
    ],
    "totalElements": 1
  }
}
```

---

### 7. Get Active Alarm by ID

**Endpoint:** `GET /active-alarms/{id}`

**Response:**
```json
{
  "success": true,
  "data": {
    "id": 1,
    "emergencyGroupId": 1,
    "emergencyGroupName": "Fire Response Team Alpha",
    "activatedDate": "2025-01-06T10:30:00",
    "activatedBy": "admin",
    "methods": ["EMAIL", "SMS", "PUSH"],
    "status": "ACTIVE"
  }
}
```

---

### 8. Activate Alarm

**Endpoint:** `POST /emergency-groups/{groupId}/activate-alarm`

**Path Parameters:**
- `groupId` - Emergency group ID

**Request Body:**
```json
{
  "activatedBy": "admin",
  "methods": ["EMAIL", "SMS", "PUSH", "DISPATCH"]
}
```

**Validation Rules:**
- `activatedBy` - Required
- `methods` - Required, at least one method

**Response (201):**
```json
{
  "success": true,
  "message": "Alarm activated successfully",
  "data": {
    "id": 1,
    "emergencyGroupId": 1,
    "emergencyGroupName": "Fire Response Team Alpha",
    "activatedDate": "2025-01-06T10:30:00",
    "activatedBy": "admin",
    "methods": ["EMAIL", "SMS", "PUSH"],
    "status": "ACTIVE"
  }
}
```

**Business Logic:**
- Creates active alarm record
- Updates emergency group's `lastNotificationDate`
- Creates alarm history entry with action "ACTIVATED"
- Checks if alarm is already active for the group (throws 409 if exists)

**Error Response (409):**
```json
{
  "success": false,
  "message": "An active alarm already exists for this emergency group"
}
```

---

### 9. Deactivate Alarm

**Endpoint:** `POST /active-alarms/{id}/deactivate`

**Path Parameters:**
- `id` - Active alarm ID

**Request Body:**
```json
{
  "deactivatedBy": "admin",
  "notes": "Emergency resolved, all clear"
}
```

**Response (200):**
```json
{
  "success": true,
  "message": "Alarm deactivated successfully",
  "data": {
    "id": 1,
    "emergencyGroupId": 1,
    "status": "DEACTIVATED",
    "alarmDeactivated": "2025-01-06T11:00:00",
    "deactivatedBy": "admin"
  }
}
```

**Business Logic:**
- Sets alarm status to "DEACTIVATED"
- Records deactivation timestamp and user
- Creates alarm history entry with action "DEACTIVATED"

---

### 10. Get Active Alarm Count

**Endpoint:** `GET /active-alarms/count`

**Response:**
```json
{
  "success": true,
  "data": {
    "count": 3
  }
}
```

---

## Alarm History API

### 11. Get Alarm History

**Endpoint:** `GET /alarm-history`

**Query Parameters:**
- `page` (optional, default: 0)
- `size` (optional, default: 10)
- `groupId` (optional) - Filter by emergency group ID
- `startDate` (optional) - Filter by start date (ISO 8601 format)
- `endDate` (optional) - Filter by end date (ISO 8601 format)

**Response:**
```json
{
  "success": true,
  "data": {
    "content": [
      {
        "id": 1,
        "alarmId": 1,
        "emergencyGroupId": 1,
        "emergencyGroupName": "Fire Response Team Alpha",
        "action": "ACTIVATED",
        "actionDate": "2025-01-06T10:30:00",
        "actionBy": "admin",
        "notes": "Alarm activated with methods: EMAIL, SMS, PUSH"
      }
    ]
  }
}
```

---

### 12. Get Alarm History by Group ID

**Endpoint:** `GET /emergency-groups/{groupId}/alarm-history`

**Path Parameters:**
- `groupId` - Emergency group ID

**Response:**
```json
{
  "success": true,
  "data": [
    {
      "id": 1,
      "action": "ACTIVATED",
      "actionDate": "2025-01-06T10:30:00",
      "actionBy": "admin"
    },
    {
      "id": 2,
      "action": "DEACTIVATED",
      "actionDate": "2025-01-06T11:00:00",
      "actionBy": "admin"
    }
  ]
}
```

---

## Statistics API

### 13. Get Dashboard Statistics

**Endpoint:** `GET /statistics/dashboard`

**Response:**
```json
{
  "success": true,
  "data": {
    "totalGroups": 25,
    "activeAlarms": 3,
    "totalActivations": 150,
    "totalAcknowledgements": 0
  }
}
```

---

## Error Handling

### Validation Error (400)

```json
{
  "success": false,
  "message": "Validation failed",
  "data": {
    "name": "Name is required",
    "methods": "At least one notification method is required"
  }
}
```

### Resource Not Found (404)

```json
{
  "success": false,
  "message": "Emergency group not found with id: 999"
}
```

### Conflict Error (409)

```json
{
  "success": false,
  "message": "Emergency group with number FIRE-001 already exists"
}
```

### Server Error (500)

```json
{
  "success": false,
  "message": "An unexpected error occurred: [error details]"
}
```

---

## Testing with cURL

### Create and Activate Example

```bash
# 1. Create emergency group
curl -X POST http://localhost:8080/api/v1/emergency-groups \
  -H "Content-Type: application/json" \
  -d '{"name":"Test Group","number":"TEST-001","memberCount":10,"createdBy":"admin"}'

# 2. Activate alarm (use ID from step 1)
curl -X POST http://localhost:8080/api/v1/emergency-groups/1/activate-alarm \
  -H "Content-Type: application/json" \
  -d '{"activatedBy":"admin","methods":["EMAIL","SMS"]}'

# 3. Get active alarms
curl http://localhost:8080/api/v1/active-alarms?status=ACTIVE

# 4. Deactivate alarm (use alarm ID from step 2)
curl -X POST http://localhost:8080/api/v1/active-alarms/1/deactivate \
  -H "Content-Type: application/json" \
  -d '{"deactivatedBy":"admin","notes":"Test complete"}'
```
