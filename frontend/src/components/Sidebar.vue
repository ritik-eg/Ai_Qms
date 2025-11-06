<template>
  <aside class="sidebar">
    <div class="sidebar-section">
      <div class="section-title">EMERGENCY</div>
      <nav class="sidebar-nav">
        <router-link to="/active-alarms" class="nav-link" active-class="active">
          <i class="fas fa-bell"></i>
          <span>Active alarms</span>
        </router-link>
        <router-link to="/emergency-groups" class="nav-link" active-class="active">
          <i class="fas fa-users"></i>
          <span>Emergency groups</span>
        </router-link>
        <a href="#" class="nav-link">
          <i class="fas fa-history"></i>
          <span>Alarm history</span>
        </a>
      </nav>
    </div>

    <div class="sidebar-section">
      <div class="section-title">ADMINISTRATOR</div>
      <nav class="sidebar-nav">
        <a href="#" class="nav-link">
          <i class="fas fa-info-circle"></i>
          <span>Statuses</span>
        </a>
        <a href="#" class="nav-link">
          <i class="fas fa-layer-group"></i>
          <span>Group types</span>
        </a>
      </nav>
    </div>
  </aside>
</template>

<script>
export default {
  name: 'Sidebar'
}
</script>

<style scoped>
.sidebar {
  width: 250px;
  background-color: #f8f9fa;
  border-right: 1px solid #dee2e6;
  padding: 20px 0;
  overflow-y: auto;
}

.sidebar-section {
  margin-bottom: 30px;
}

.section-title {
  color: #6c757d;
  font-size: 11px;
  font-weight: 600;
  letter-spacing: 0.5px;
  padding: 0 20px;
  margin-bottom: 10px;
}

.sidebar-nav {
  display: flex;
  flex-direction: column;
}

.nav-link {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px 20px;
  color: #333;
  text-decoration: none;
  font-size: 14px;
  transition: all 0.2s;
  border-left: 3px solid transparent;
}

.nav-link:hover {
  background-color: #e9ecef;
}

.nav-link.active {
  background-color: #e7f3ff;
  border-left-color: #0066cc;
  color: #0066cc;
}

.nav-link i {
  font-size: 16px;
  width: 20px;
  text-align: center;
}
</style>
