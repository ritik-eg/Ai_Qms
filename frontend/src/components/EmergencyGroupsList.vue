<template>
  <div class="emergency-groups-list">
    <!-- Filters Section -->
    <div class="filters-section">
      <div class="filters-header">
        <i class="fas fa-chevron-down"></i>
        <h3>Filters</h3>
      </div>
      <div class="filter-tabs">
        <button
          :class="['filter-tab', { active: activeFilter === 'all' }]"
          @click="activeFilter = 'all'"
        >
          All
        </button>
        <button
          :class="['filter-tab', { active: activeFilter === 'notification' }]"
          @click="activeFilter = 'notification'"
        >
          Notification groups
        </button>
        <button
          :class="['filter-tab', { active: activeFilter === 'alarm' }]"
          @click="activeFilter = 'alarm'"
        >
          Alarm groups
        </button>
      </div>
    </div>

    <!-- Actions Bar -->
    <div class="actions-bar">
      <button class="create-new-btn" @click="navigateToCreateNew">
        <i class="fas fa-plus"></i>
        Create new group
      </button>
      <div class="actions-right">
        <div class="search-box">
          <i class="fas fa-search"></i>
          <input
            type="text"
            v-model="searchText"
            placeholder="Enter search text"
            class="search-input"
          />
        </div>
        <button class="export-btn">
          <i class="fas fa-file-excel"></i>
          Export to Excel
        </button>
      </div>
    </div>

    <!-- Data Table -->
    <div class="table-container">
      <table class="groups-table">
        <thead>
          <tr>
            <th class="col-checkbox">
              <input type="checkbox" @change="toggleSelectAll" />
            </th>
            <th class="col-options">Options</th>
            <th class="col-number sortable" @click="sortBy('number')">
              Number
              <i class="fas fa-sort"></i>
            </th>
            <th class="col-name sortable" @click="sortBy('name')">
              Name
              <i class="fas fa-sort"></i>
            </th>
            <th class="col-type sortable" @click="sortBy('type')">
              Type
              <i class="fas fa-sort"></i>
            </th>
            <th class="col-members sortable" @click="sortBy('members')">
              Members
              <i class="fas fa-sort"></i>
            </th>
            <th class="col-notification sortable" @click="sortBy('lastNotification')">
              Last notification
              <i class="fas fa-sort"></i>
            </th>
            <th class="col-revised sortable" @click="sortBy('revised')">
              Revised
              <i class="fas fa-sort"></i>
            </th>
          </tr>
        </thead>
        <tbody>
          <tr
            v-for="group in filteredGroups"
            :key="group.number"
            :class="['group-row', group.statusColor]"
          >
            <td class="col-checkbox">
              <input type="checkbox" v-model="group.selected" />
            </td>
            <td class="col-options">
              <button class="icon-btn" @click="viewGroup(group)">
                <i class="fas fa-search"></i>
              </button>
            </td>
            <td class="col-number">{{ group.number }}</td>
            <td class="col-name">
              <span class="group-name-link" @click="viewGroup(group)">
                {{ group.name }}
              </span>
            </td>
            <td class="col-type">{{ group.type }}</td>
            <td class="col-members">
              <i class="fas fa-users"></i>
              {{ group.members }}
            </td>
            <td class="col-notification">{{ group.lastNotification }}</td>
            <td class="col-revised">{{ group.revised }}</td>
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
  name: 'EmergencyGroupsList',
  data() {
    return {
      activeFilter: 'all',
      searchText: '',
      currentPage: 1,
      itemsPerPage: 25,
      sortColumn: '',
      sortDirection: 'asc',
      groups: [
        {
          number: '0055',
          name: 'After Fix emergency Test',
          type: '',
          members: '8',
          lastNotification: '28/10/2025',
          revised: '',
          selected: false,
          statusColor: 'red'
        },
        {
          number: '0045',
          name: 'Aishwarya_QA Group',
          type: '',
          members: '10',
          lastNotification: '28/10/2025',
          revised: '',
          selected: false,
          statusColor: 'red'
        },
        {
          number: '0047',
          name: 'Dev emergency group',
          type: 'testing emergency',
          members: '6',
          lastNotification: '27/10/2025',
          revised: '',
          selected: false,
          statusColor: 'red'
        },
        {
          number: '0057',
          name: 'Emergecny Testing 3.0.1',
          type: '',
          members: '10',
          lastNotification: '30/07/2025',
          revised: '',
          selected: false,
          statusColor: 'red'
        },
        {
          number: '0054',
          name: 'Emergency Test',
          type: '',
          members: '4',
          lastNotification: '14/07/2025',
          revised: '',
          selected: false,
          statusColor: 'blue'
        },
        {
          number: '0048',
          name: 'Emergency testing',
          type: 'testing emergency',
          members: '8',
          lastNotification: '30/07/2025',
          revised: '',
          selected: false,
          statusColor: 'red'
        },
        {
          number: '0036',
          name: 'Gulab jamuns are empty!!!!!1',
          type: '',
          members: '4',
          lastNotification: '14/10/2025',
          revised: '',
          selected: false,
          statusColor: 'red'
        },
        {
          number: '999',
          name: 'hello',
          type: 'Testing group',
          members: 'No members',
          lastNotification: '',
          revised: '',
          selected: false,
          statusColor: 'blue'
        },
        {
          number: '0043',
          name: 'kk group',
          type: '',
          members: '6',
          lastNotification: '27/10/2025',
          revised: '',
          selected: false,
          statusColor: 'red'
        },
        {
          number: '0044',
          name: 'kk user group',
          type: '',
          members: '2',
          lastNotification: '28/05/2025',
          revised: '',
          selected: false,
          statusColor: 'blue'
        },
        {
          number: '0015',
          name: 'Landax-Labours',
          type: '',
          members: '7',
          lastNotification: '12/10/2025',
          revised: '',
          selected: false,
          statusColor: 'red'
        },
        {
          number: '0065',
          name: 'Media alaram test',
          type: '',
          members: '2',
          lastNotification: '27/10/2025',
          revised: '',
          selected: false,
          statusColor: 'red'
        },
        {
          number: '0050',
          name: 'New Alaram Please response',
          type: '',
          members: '4',
          lastNotification: '20/06/2025',
          revised: '',
          selected: false,
          statusColor: 'red'
        },
        {
          number: '0052',
          name: 'new Emergency Test',
          type: '',
          members: '4',
          lastNotification: '',
          revised: '',
          selected: false,
          statusColor: 'blue'
        }
      ]
    }
  },
  computed: {
    filteredGroups() {
      let filtered = this.groups

      // Apply search filter
      if (this.searchText) {
        filtered = filtered.filter(group =>
          group.name.toLowerCase().includes(this.searchText.toLowerCase()) ||
          group.number.includes(this.searchText) ||
          group.type.toLowerCase().includes(this.searchText.toLowerCase())
        )
      }

      // Apply pagination
      const start = (this.currentPage - 1) * this.itemsPerPage
      const end = start + parseInt(this.itemsPerPage)
      return filtered.slice(start, end)
    },
    totalItems() {
      return this.groups.length
    },
    totalPages() {
      return Math.ceil(this.totalItems / this.itemsPerPage)
    },
    paginationStart() {
      return (this.currentPage - 1) * this.itemsPerPage + 1
    },
    paginationEnd() {
      const end = this.currentPage * this.itemsPerPage
      return end > this.totalItems ? this.totalItems : end
    }
  },
  methods: {
    navigateToCreateNew() {
      this.$router.push('/emergency-groups/new')
    },
    viewGroup(group) {
      // Navigate to activate alarm page instead of detail page
      this.$router.push(`/emergency-groups/${group.number}/activate`)
    },
    toggleSelectAll(event) {
      this.groups.forEach(group => {
        group.selected = event.target.checked
      })
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
  }
}
</script>

<style scoped>
.emergency-groups-list {
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

.filters-header {
  display: flex;
  align-items: center;
  gap: 10px;
  margin-bottom: 15px;
}

.filters-header i {
  color: #666;
  font-size: 14px;
}

.filters-header h3 {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin: 0;
}

.filter-tabs {
  display: flex;
  gap: 5px;
}

.filter-tab {
  padding: 8px 16px;
  background-color: white;
  border: 1px solid #ced4da;
  border-radius: 4px;
  font-size: 14px;
  color: #495057;
  cursor: pointer;
  transition: all 0.2s;
}

.filter-tab:hover {
  background-color: #e9ecef;
}

.filter-tab.active {
  background-color: #e7f3ff;
  border-color: #0066cc;
  color: #0066cc;
}

.actions-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
  flex-wrap: wrap;
  gap: 15px;
}

.create-new-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background-color: #17a2b8;
  color: white;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  transition: background-color 0.2s;
}

.create-new-btn:hover {
  background-color: #138496;
}

.actions-right {
  display: flex;
  align-items: center;
  gap: 15px;
}

.search-box {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 8px 12px;
  background-color: white;
  border: 1px solid #ced4da;
  border-radius: 4px;
}

.search-box i {
  color: #6c757d;
  font-size: 14px;
}

.search-input {
  border: none;
  outline: none;
  font-size: 14px;
  min-width: 200px;
}

.search-input::placeholder {
  color: #adb5bd;
}

.export-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  background-color: white;
  color: #495057;
  border: 1px solid #ced4da;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
}

.export-btn:hover {
  background-color: #f8f9fa;
}

.export-btn i {
  color: #28a745;
}

.table-container {
  overflow-x: auto;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  margin-bottom: 20px;
}

.groups-table {
  width: 100%;
  border-collapse: collapse;
  background-color: white;
}

.groups-table thead {
  background-color: #f8f9fa;
}

.groups-table th {
  padding: 12px;
  text-align: left;
  font-size: 14px;
  font-weight: 600;
  color: #495057;
  border-bottom: 2px solid #dee2e6;
  white-space: nowrap;
}

.groups-table th.sortable {
  cursor: pointer;
  user-select: none;
}

.groups-table th.sortable:hover {
  background-color: #e9ecef;
}

.groups-table th i {
  margin-left: 5px;
  color: #6c757d;
  font-size: 12px;
}

.groups-table td {
  padding: 12px;
  font-size: 14px;
  color: #495057;
  border-bottom: 1px solid #dee2e6;
}

.group-row {
  border-left: 4px solid transparent;
  transition: background-color 0.2s;
}

.group-row:hover {
  background-color: #f8f9fa;
}

.group-row.red {
  border-left-color: #dc3545;
}

.group-row.blue {
  border-left-color: #17a2b8;
}

.col-checkbox {
  width: 40px;
}

.col-options {
  width: 80px;
}

.col-number {
  width: 100px;
}

.col-name {
  min-width: 200px;
}

.col-type {
  min-width: 150px;
}

.col-members {
  width: 120px;
}

.col-notification {
  width: 150px;
}

.col-revised {
  width: 120px;
}

.col-members i {
  margin-right: 5px;
  color: #6c757d;
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

.group-name-link {
  color: #0066cc;
  cursor: pointer;
  text-decoration: none;
  transition: all 0.2s;
}

.group-name-link:hover {
  text-decoration: underline;
  color: #0052a3;
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

@media (max-width: 768px) {
  .actions-bar {
    flex-direction: column;
    align-items: stretch;
  }

  .actions-right {
    flex-direction: column;
    align-items: stretch;
  }

  .search-input {
    min-width: 100%;
  }

  .pagination {
    flex-direction: column;
    gap: 15px;
  }
}
</style>
