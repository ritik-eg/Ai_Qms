<template>
  <div class="active-alarms-container">
    <!-- Filters Section -->
    <div class="filters-section">
      <h3>Filters</h3>
      <div class="filter-content">
        <!-- Add filter options here if needed -->
      </div>
    </div>

    <!-- Search Bar -->
    <div class="search-section">
      <div class="search-box">
        <i class="fas fa-search"></i>
        <input
          type="text"
          v-model="searchText"
          placeholder="Enter search text"
          class="search-input"
        />
      </div>
    </div>

    <!-- Active Alarms Title -->
    <div class="section-title">
      <h2>Active alarms</h2>
    </div>

    <!-- Alarms Table -->
    <div class="table-container">
      <table class="alarms-table">
        <thead>
          <tr>
            <th class="col-id sortable" @click="sortBy('id')">
              ID
              <i class="fas fa-sort"></i>
            </th>
            <th class="col-options">Options</th>
            <th class="col-group sortable" @click="sortBy('group')">
              Group
              <i class="fas fa-sort"></i>
            </th>
            <th class="col-activated sortable" @click="sortBy('activatedDate')">
              Activated date
              <i class="fas fa-sort"></i>
            </th>
            <th class="col-activated-by">Activated by</th>
            <th class="col-methods">Methods</th>
            <th class="col-deactivated sortable" @click="sortBy('deactivated')">
              Alarm deactivated
              <i class="fas fa-sort"></i>
            </th>
            <th class="col-deactivated-by">Deactivated by</th>
            <th class="col-statistics">Statistics</th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="alarm in filteredAlarms"
            :key="alarm.id"
            class="alarm-row"
          >
            <td class="col-id">{{ alarm.id }}</td>
            <td class="col-options">
              <button class="icon-btn" @click="viewAlarm(alarm)">
                <i class="fas fa-search"></i>
              </button>
            </td>
            <td class="col-group">{{ alarm.group }}</td>
            <td class="col-activated">
              <i class="fas fa-bell alarm-icon"></i>
              {{ alarm.activatedDate }}
            </td>
            <td class="col-activated-by">{{ alarm.activatedBy }}</td>
            <td class="col-methods">
              <div class="method-icons">
                <i v-if="alarm.methods.email" class="fas fa-envelope" title="Email"></i>
                <i v-if="alarm.methods.phone" class="fas fa-mobile-alt" title="Phone"></i>
                <i v-if="alarm.methods.sms" class="fas fa-comment" title="SMS"></i>
                <i v-if="alarm.methods.app" class="fas fa-tablet-alt" title="App"></i>
              </div>
            </td>
            <td class="col-deactivated">{{ alarm.deactivated || '-' }}</td>
            <td class="col-deactivated-by">{{ alarm.deactivatedBy || '-' }}</td>
            <td class="col-statistics">
              <div class="statistics">
                <span class="stat-item">
                  <i class="fas fa-check-circle"></i> {{ alarm.statistics.confirmed }}
                </span>
                <span class="stat-item">
                  <i class="fas fa-comment-dots"></i> {{ alarm.statistics.comments }}
                </span>
                <span class="stat-item">
                  <i class="fas fa-times-circle"></i> {{ alarm.statistics.failed }}
                </span>
              </div>
            </td>
          </tr>
          <tr v-if="filteredAlarms.length === 0">
            <td colspan="9" class="no-data">No active alarms found</td>
          </tr>
        </tbody>
      </table>
    </div>

    <!-- Pagination -->
    <div class="pagination">
      <div class="pagination-info">
        {{ paginationStart }} - {{ paginationEnd }} of {{ totalItems }} items
      </div>
      <div class="pagination-controls">
        <select v-model="itemsPerPage" class="items-per-page">
          <option value="25">25</option>
          <option value="50">50</option>
          <option value="100">100</option>
        </select>
        <button class="page-btn" @click="goToFirstPage" :disabled="currentPage === 1">
          <i class="fas fa-angle-double-left"></i>
        </button>
        <button class="page-btn" @click="previousPage" :disabled="currentPage === 1">
          <i class="fas fa-angle-left"></i>
        </button>
        <span class="page-number">{{ currentPage }}</span>
        <button class="page-btn" @click="nextPage" :disabled="currentPage === totalPages">
          <i class="fas fa-angle-right"></i>
        </button>
        <button class="page-btn" @click="goToLastPage" :disabled="currentPage === totalPages">
          <i class="fas fa-angle-double-right"></i>
        </button>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ActiveAlarms',
  data() {
    return {
      searchText: '',
      currentPage: 1,
      itemsPerPage: 25,
      sortColumn: '',
      sortDirection: 'asc',
      alarms: [
        {
          id: 638,
          group: 'After Fix emergency Test',
          activatedDate: '28/10/2025, 10:13',
          activatedBy: 'prasanna acharya',
          methods: {
            email: true,
            phone: true,
            sms: true,
            app: false
          },
          deactivated: '',
          deactivatedBy: '',
          statistics: {
            confirmed: 1,
            comments: 1,
            failed: 0
          }
        },
        {
          id: 637,
          group: 'Aishwarya_QA Group',
          activatedDate: '28/10/2025, 07:37',
          activatedBy: 'prasanna acharya',
          methods: {
            email: true,
            phone: true,
            sms: true,
            app: true
          },
          deactivated: '',
          deactivatedBy: '',
          statistics: {
            confirmed: 0,
            comments: 1,
            failed: 0
          }
        }
      ]
    }
  },
  computed: {
    filteredAlarms() {
      let filtered = this.alarms

      // Apply search filter
      if (this.searchText) {
        filtered = filtered.filter(alarm =>
          alarm.group.toLowerCase().includes(this.searchText.toLowerCase()) ||
          alarm.id.toString().includes(this.searchText) ||
          alarm.activatedBy.toLowerCase().includes(this.searchText.toLowerCase())
        )
      }

      // Apply pagination
      const start = (this.currentPage - 1) * this.itemsPerPage
      const end = start + parseInt(this.itemsPerPage)
      return filtered.slice(start, end)
    },
    totalItems() {
      return this.alarms.length
    },
    totalPages() {
      return Math.ceil(this.totalItems / this.itemsPerPage)
    },
    paginationStart() {
      return this.alarms.length === 0 ? 0 : (this.currentPage - 1) * this.itemsPerPage + 1
    },
    paginationEnd() {
      const end = this.currentPage * this.itemsPerPage
      return end > this.totalItems ? this.totalItems : end
    }
  },
  methods: {
    viewAlarm(alarm) {
      // Navigate to alarm detail/activation page
      this.$router.push(`/active-alarms/${alarm.id}`)
    },
    sortBy(column) {
      if (this.sortColumn === column) {
        this.sortDirection = this.sortDirection === 'asc' ? 'desc' : 'asc'
      } else {
        this.sortColumn = column
        this.sortDirection = 'asc'
      }
    },
    goToFirstPage() {
      this.currentPage = 1
    },
    previousPage() {
      if (this.currentPage > 1) {
        this.currentPage--
      }
    },
    nextPage() {
      if (this.currentPage < this.totalPages) {
        this.currentPage++
      }
    },
    goToLastPage() {
      this.currentPage = this.totalPages
    }
  },
  mounted() {
    // Check if there's a newly activated alarm passed from the activation page
    if (this.$route.params.newAlarm) {
      const newAlarm = JSON.parse(this.$route.params.newAlarm)
      this.alarms.unshift(newAlarm)
    }
  }
}
</script>

<style scoped>
.active-alarms-container {
  padding: 20px;
  background-color: #ffffff;
}

.filters-section {
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  padding: 15px;
  margin-bottom: 20px;
}

.filters-section h3 {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-bottom: 10px;
}

.search-section {
  margin-bottom: 20px;
}

.search-box {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 10px 15px;
  background-color: white;
  border: 1px solid #ced4da;
  border-radius: 4px;
  max-width: 400px;
}

.search-box i {
  color: #6c757d;
  font-size: 16px;
}

.search-input {
  border: none;
  outline: none;
  font-size: 14px;
  flex: 1;
}

.search-input::placeholder {
  color: #adb5bd;
}

.section-title {
  margin-bottom: 20px;
}

.section-title h2 {
  font-size: 20px;
  font-weight: 600;
  color: #333;
}

.table-container {
  overflow-x: auto;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  margin-bottom: 20px;
}

.alarms-table {
  width: 100%;
  border-collapse: collapse;
  background-color: white;
}

.alarms-table thead {
  background-color: #f8f9fa;
}

.alarms-table th {
  padding: 12px;
  text-align: left;
  font-size: 14px;
  font-weight: 600;
  color: #495057;
  border-bottom: 2px solid #dee2e6;
  white-space: nowrap;
}

.alarms-table th.sortable {
  cursor: pointer;
  user-select: none;
}

.alarms-table th.sortable:hover {
  background-color: #e9ecef;
}

.alarms-table th i {
  margin-left: 5px;
  color: #6c757d;
  font-size: 12px;
}

.alarms-table td {
  padding: 12px;
  font-size: 14px;
  color: #495057;
  border-bottom: 1px solid #dee2e6;
}

.alarm-row {
  border-left: 4px solid #dc3545;
  transition: background-color 0.2s;
}

.alarm-row:hover {
  background-color: #f8f9fa;
}

.col-id {
  width: 80px;
}

.col-options {
  width: 80px;
}

.col-group {
  min-width: 200px;
}

.col-activated {
  min-width: 180px;
}

.col-activated-by {
  min-width: 150px;
}

.col-methods {
  width: 120px;
}

.col-deactivated {
  min-width: 150px;
}

.col-deactivated-by {
  min-width: 150px;
}

.col-statistics {
  min-width: 150px;
}

.icon-btn {
  background: none;
  border: none;
  color: #6c757d;
  cursor: pointer;
  padding: 5px;
  transition: color 0.2s;
}

.icon-btn:hover {
  color: #0066cc;
}

.alarm-icon {
  color: #dc3545;
  margin-right: 5px;
}

.method-icons {
  display: flex;
  gap: 10px;
}

.method-icons i {
  color: #6c757d;
  font-size: 16px;
}

.statistics {
  display: flex;
  gap: 15px;
}

.stat-item {
  display: flex;
  align-items: center;
  gap: 5px;
  font-size: 13px;
}

.stat-item i {
  font-size: 14px;
}

.stat-item:nth-child(1) i {
  color: #28a745;
}

.stat-item:nth-child(2) i {
  color: #17a2b8;
}

.stat-item:nth-child(3) i {
  color: #dc3545;
}

.no-data {
  text-align: center;
  padding: 40px;
  color: #6c757d;
  font-style: italic;
}

.pagination {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 15px 0;
}

.pagination-info {
  font-size: 14px;
  color: #495057;
}

.pagination-controls {
  display: flex;
  align-items: center;
  gap: 10px;
}

.items-per-page {
  padding: 6px 10px;
  border: 1px solid #ced4da;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
}

.page-btn {
  padding: 6px 10px;
  background-color: white;
  border: 1px solid #ced4da;
  border-radius: 4px;
  color: #495057;
  cursor: pointer;
  transition: all 0.2s;
}

.page-btn:hover:not(:disabled) {
  background-color: #e9ecef;
}

.page-btn:disabled {
  opacity: 0.5;
  cursor: not-allowed;
}

.page-number {
  padding: 6px 12px;
  font-size: 14px;
  font-weight: 500;
  color: #495057;
}
</style>
