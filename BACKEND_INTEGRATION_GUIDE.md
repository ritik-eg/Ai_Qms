# Backend Integration Guide for Frontend

## 🔗 Connecting Vue.js Frontend to Spring Boot Backend

This guide helps you integrate the Spring Boot backend with your Vue.js frontend.

---

## 📋 Prerequisites

1. ✅ Backend running on `http://localhost:8080`
2. ✅ Frontend running on `http://localhost:5173` (Vite default)
3. ✅ PostgreSQL database created and running

---

## 🚀 Step-by-Step Integration

### Step 1: Start Backend

```bash
cd backend
./gradlew bootRun
```

Verify backend is running:
```bash
curl http://localhost:8080/api/v1/statistics/dashboard
```

### Step 2: Configure Frontend API Base URL

In your Vue.js project, create/update the API configuration:

**File: `frontend/src/config/api.js`**
```javascript
export const API_BASE_URL = 'http://localhost:8080/api/v1'

export const API_ENDPOINTS = {
  // Emergency Groups
  EMERGENCY_GROUPS: '/emergency-groups',
  EMERGENCY_GROUP_BY_ID: (id) => `/emergency-groups/${id}`,

  // Active Alarms
  ACTIVE_ALARMS: '/active-alarms',
  ACTIVE_ALARM_BY_ID: (id) => `/active-alarms/${id}`,
  ACTIVATE_ALARM: (groupId) => `/emergency-groups/${groupId}/activate-alarm`,
  DEACTIVATE_ALARM: (id) => `/active-alarms/${id}/deactivate`,
  ACTIVE_ALARM_COUNT: '/active-alarms/count',

  // Alarm History
  ALARM_HISTORY: '/alarm-history',
  GROUP_ALARM_HISTORY: (groupId) => `/emergency-groups/${groupId}/alarm-history`,

  // Statistics
  DASHBOARD_STATS: '/statistics/dashboard'
}
```

### Step 3: Create API Service

**File: `frontend/src/services/api.js`**
```javascript
import axios from 'axios'
import { API_BASE_URL } from '@/config/api'

// Create axios instance
const api = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    'Content-Type': 'application/json'
  }
})

// Response interceptor
api.interceptors.response.use(
  response => response.data, // Return only data
  error => {
    console.error('API Error:', error)
    return Promise.reject(error)
  }
)

export default api
```

### Step 4: Create Service Modules

**File: `frontend/src/services/emergencyGroupService.js`**
```javascript
import api from './api'
import { API_ENDPOINTS } from '@/config/api'

export const emergencyGroupService = {
  // Get all emergency groups
  async getAll(params = {}) {
    const { page = 0, size = 10, type, search } = params
    return api.get(API_ENDPOINTS.EMERGENCY_GROUPS, {
      params: { page, size, type, search }
    })
  },

  // Get emergency group by ID
  async getById(id) {
    return api.get(API_ENDPOINTS.EMERGENCY_GROUP_BY_ID(id))
  },

  // Create emergency group
  async create(data) {
    return api.post(API_ENDPOINTS.EMERGENCY_GROUPS, data)
  },

  // Update emergency group
  async update(id, data) {
    return api.put(API_ENDPOINTS.EMERGENCY_GROUP_BY_ID(id), data)
  },

  // Delete emergency group
  async delete(id) {
    return api.delete(API_ENDPOINTS.EMERGENCY_GROUP_BY_ID(id))
  }
}
```

**File: `frontend/src/services/alarmService.js`**
```javascript
import api from './api'
import { API_ENDPOINTS } from '@/config/api'

export const alarmService = {
  // Get all active alarms
  async getAll(params = {}) {
    const { page = 0, size = 10, status, groupId } = params
    return api.get(API_ENDPOINTS.ACTIVE_ALARMS, {
      params: { page, size, status, groupId }
    })
  },

  // Get active alarm by ID
  async getById(id) {
    return api.get(API_ENDPOINTS.ACTIVE_ALARM_BY_ID(id))
  },

  // Activate alarm
  async activate(groupId, data) {
    return api.post(API_ENDPOINTS.ACTIVATE_ALARM(groupId), data)
  },

  // Deactivate alarm
  async deactivate(id, data) {
    return api.post(API_ENDPOINTS.DEACTIVATE_ALARM(id), data)
  },

  // Get active alarm count
  async getCount() {
    return api.get(API_ENDPOINTS.ACTIVE_ALARM_COUNT)
  },

  // Get alarm history
  async getHistory(params = {}) {
    const { page = 0, size = 10, groupId, startDate, endDate } = params
    return api.get(API_ENDPOINTS.ALARM_HISTORY, {
      params: { page, size, groupId, startDate, endDate }
    })
  },

  // Get history by group
  async getGroupHistory(groupId) {
    return api.get(API_ENDPOINTS.GROUP_ALARM_HISTORY(groupId))
  }
}
```

**File: `frontend/src/services/statisticsService.js`**
```javascript
import api from './api'
import { API_ENDPOINTS } from '@/config/api'

export const statisticsService = {
  async getDashboardStats() {
    return api.get(API_ENDPOINTS.DASHBOARD_STATS)
  }
}
```

---

## 🎨 Usage Examples in Vue Components

### Example 1: List Emergency Groups

```vue
<template>
  <div>
    <h2>Emergency Groups</h2>
    <div v-if="loading">Loading...</div>
    <div v-else-if="error">{{ error }}</div>
    <div v-else>
      <div v-for="group in groups" :key="group.id">
        <h3>{{ group.name }}</h3>
        <p>Number: {{ group.number }}</p>
        <p>Type: {{ group.type }}</p>
      </div>

      <!-- Pagination -->
      <button @click="prevPage" :disabled="currentPage === 0">Previous</button>
      <span>Page {{ currentPage + 1 }} of {{ totalPages }}</span>
      <button @click="nextPage" :disabled="currentPage >= totalPages - 1">Next</button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { emergencyGroupService } from '@/services/emergencyGroupService'

const groups = ref([])
const loading = ref(false)
const error = ref(null)
const currentPage = ref(0)
const totalPages = ref(0)
const pageSize = 10

const fetchGroups = async () => {
  loading.value = true
  error.value = null
  try {
    const response = await emergencyGroupService.getAll({
      page: currentPage.value,
      size: pageSize
    })
    groups.value = response.data.content
    totalPages.value = response.data.totalPages
  } catch (err) {
    error.value = 'Failed to load emergency groups'
    console.error(err)
  } finally {
    loading.value = false
  }
}

const nextPage = () => {
  currentPage.value++
  fetchGroups()
}

const prevPage = () => {
  currentPage.value--
  fetchGroups()
}

onMounted(() => {
  fetchGroups()
})
</script>
```

### Example 2: Create Emergency Group

```vue
<template>
  <div>
    <h2>Create Emergency Group</h2>
    <form @submit.prevent="handleSubmit">
      <div>
        <label>Name:</label>
        <input v-model="form.name" required />
      </div>
      <div>
        <label>Number:</label>
        <input v-model="form.number" />
      </div>
      <div>
        <label>Type:</label>
        <select v-model="form.type">
          <option value="Fire Emergency">Fire Emergency</option>
          <option value="Medical Emergency">Medical Emergency</option>
          <option value="Security Alert">Security Alert</option>
        </select>
      </div>
      <div>
        <label>Notification Type:</label>
        <select v-model="form.notificationType">
          <option value="ALARM">ALARM</option>
          <option value="DISPATCH">DISPATCH</option>
        </select>
      </div>
      <div>
        <label>Member Count:</label>
        <input v-model.number="form.memberCount" type="number" />
      </div>
      <button type="submit" :disabled="submitting">
        {{ submitting ? 'Creating...' : 'Create' }}
      </button>
    </form>
    <div v-if="successMessage" class="success">{{ successMessage }}</div>
    <div v-if="errorMessage" class="error">{{ errorMessage }}</div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { emergencyGroupService } from '@/services/emergencyGroupService'

const form = ref({
  name: '',
  number: '',
  type: 'Fire Emergency',
  notificationType: 'ALARM',
  memberCount: 0,
  createdBy: 'admin'
})

const submitting = ref(false)
const successMessage = ref('')
const errorMessage = ref('')

const handleSubmit = async () => {
  submitting.value = true
  successMessage.value = ''
  errorMessage.value = ''

  try {
    const response = await emergencyGroupService.create(form.value)
    successMessage.value = response.message || 'Emergency group created successfully'
    // Reset form
    form.value = {
      name: '',
      number: '',
      type: 'Fire Emergency',
      notificationType: 'ALARM',
      memberCount: 0,
      createdBy: 'admin'
    }
  } catch (err) {
    errorMessage.value = err.response?.data?.message || 'Failed to create emergency group'
  } finally {
    submitting.value = false
  }
}
</script>
```

### Example 3: Activate Alarm

```vue
<template>
  <div>
    <h2>Activate Alarm</h2>
    <form @submit.prevent="handleActivate">
      <div>
        <label>Activated By:</label>
        <input v-model="activateForm.activatedBy" required />
      </div>
      <div>
        <label>Notification Methods:</label>
        <div>
          <label><input type="checkbox" v-model="methods" value="EMAIL" /> Email</label>
          <label><input type="checkbox" v-model="methods" value="SMS" /> SMS</label>
          <label><input type="checkbox" v-model="methods" value="PUSH" /> Push</label>
          <label><input type="checkbox" v-model="methods" value="DISPATCH" /> Dispatch</label>
        </div>
      </div>
      <button type="submit" :disabled="submitting || methods.length === 0">
        Activate Alarm
      </button>
    </form>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { alarmService } from '@/services/alarmService'

const props = defineProps({
  groupId: {
    type: Number,
    required: true
  }
})

const activateForm = ref({
  activatedBy: 'admin'
})

const methods = ref(['EMAIL', 'SMS'])
const submitting = ref(false)

const handleActivate = async () => {
  submitting.value = true
  try {
    const response = await alarmService.activate(props.groupId, {
      activatedBy: activateForm.value.activatedBy,
      methods: methods.value
    })
    alert(response.message || 'Alarm activated successfully')
  } catch (err) {
    alert(err.response?.data?.message || 'Failed to activate alarm')
  } finally {
    submitting.value = false
  }
}
</script>
```

### Example 4: Dashboard Statistics

```vue
<template>
  <div class="dashboard">
    <h1>Dashboard</h1>
    <div v-if="loading">Loading statistics...</div>
    <div v-else class="stats-grid">
      <div class="stat-card">
        <h3>Total Groups</h3>
        <p class="stat-value">{{ stats.totalGroups }}</p>
      </div>
      <div class="stat-card">
        <h3>Active Alarms</h3>
        <p class="stat-value">{{ stats.activeAlarms }}</p>
      </div>
      <div class="stat-card">
        <h3>Total Activations</h3>
        <p class="stat-value">{{ stats.totalActivations }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import { statisticsService } from '@/services/statisticsService'

const stats = ref({
  totalGroups: 0,
  activeAlarms: 0,
  totalActivations: 0
})
const loading = ref(false)

const fetchStats = async () => {
  loading.value = true
  try {
    const response = await statisticsService.getDashboardStats()
    stats.value = response.data
  } catch (err) {
    console.error('Failed to load statistics', err)
  } finally {
    loading.value = false
  }
}

onMounted(() => {
  fetchStats()
})
</script>

<style scoped>
.stats-grid {
  display: grid;
  grid-template-columns: repeat(3, 1fr);
  gap: 20px;
}

.stat-card {
  padding: 20px;
  border: 1px solid #ddd;
  border-radius: 8px;
}

.stat-value {
  font-size: 2em;
  font-weight: bold;
  color: #42b983;
}
</style>
```

---

## 🔍 Testing the Integration

### 1. Test Backend Directly

```bash
# Get all groups
curl http://localhost:8080/api/v1/emergency-groups

# Get dashboard stats
curl http://localhost:8080/api/v1/statistics/dashboard

# Create a group
curl -X POST http://localhost:8080/api/v1/emergency-groups \
  -H "Content-Type: application/json" \
  -d '{"name":"Test Group","number":"TEST-001","memberCount":10,"createdBy":"admin"}'
```

### 2. Test from Browser Console

```javascript
// Test in browser console (with frontend running)
fetch('http://localhost:8080/api/v1/emergency-groups')
  .then(res => res.json())
  .then(data => console.log(data))
```

---

## 🐛 Common Issues & Solutions

### Issue 1: CORS Error

**Symptom:** Browser console shows CORS policy error

**Solution:** Backend already configured for CORS. Ensure frontend is running on:
- `http://localhost:5173` (Vite default)
- `http://localhost:3000` (React/Vue CLI default)

If using different port, update `backend/src/main/java/com/emergency/config/WebConfig.java`:
```java
.allowedOrigins("http://localhost:YOUR_PORT")
```

### Issue 2: Connection Refused

**Symptom:** `ERR_CONNECTION_REFUSED` or `ECONNREFUSED`

**Solution:**
1. Verify backend is running: `curl http://localhost:8080/api/v1/statistics/dashboard`
2. Check backend logs for errors
3. Ensure PostgreSQL database is running

### Issue 3: 404 Not Found

**Symptom:** API returns 404

**Solution:**
1. Verify API endpoint URL matches backend routes
2. Check API_BASE_URL includes `/api/v1`
3. Review backend console logs

### Issue 4: 500 Internal Server Error

**Symptom:** API returns 500 error

**Solution:**
1. Check backend console for stack trace
2. Verify database is accessible
3. Check application.properties configuration

---

## 📦 Response Format

All API responses follow this structure:

### Success Response
```json
{
  "success": true,
  "message": "Operation successful",
  "data": { /* actual data */ }
}
```

### Error Response
```json
{
  "success": false,
  "message": "Error message here",
  "data": null
}
```

### Accessing Data in Frontend
```javascript
const response = await emergencyGroupService.getAll()
// Access data
const groups = response.data.content
const totalPages = response.data.totalPages
```

---

## 🎯 Next Steps

1. ✅ Start backend server
2. ✅ Create API service files in frontend
3. ✅ Update existing Vue components to use API services
4. ✅ Test each feature (CRUD operations, alarm activation)
5. ✅ Handle errors and loading states
6. ✅ Add success/error notifications

---

## 📞 Need Help?

- Backend API Documentation: `backend/API_DOCUMENTATION.md`
- Swagger UI: http://localhost:8080/swagger-ui.html
- Postman Collection: `backend/POSTMAN_COLLECTION.json`

---

**Status: ✅ Ready for Integration**

The backend is fully functional and ready to be connected to your Vue.js frontend!
