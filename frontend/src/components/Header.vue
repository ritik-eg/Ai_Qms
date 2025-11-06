<template>
  <div class="header">
    <!-- Top Navigation Bar -->
    <div class="nav-bar">
      <div class="nav-left">
        <div class="logo-section">
          <div class="logo-circle">
            <i class="fas fa-circle-notch"></i>
          </div>
        </div>
        <nav class="nav-menu">
          <a href="#" class="nav-item">Document Library</a>
          <a href="#" class="nav-item">Non-conformity and Improvement</a>
          <div class="nav-item dropdown">
            Processes <i class="fas fa-chevron-down"></i>
          </div>
          <a href="#" class="nav-item">Projects</a>
          <a href="#" class="nav-item">Risk Assessments</a>
          <div class="nav-item dropdown">
            Administration <i class="fas fa-chevron-down"></i>
          </div>
          <div class="nav-item dropdown">
            Link Navigator <i class="fas fa-chevron-down"></i>
          </div>
          <div class="nav-item dropdown">
            Additional Modules <i class="fas fa-chevron-down"></i>
          </div>
        </nav>
      </div>
      <div class="nav-right">
        <div class="nav-item dropdown">
          <i class="fas fa-map-marker-alt"></i> State <i class="fas fa-chevron-down"></i>
        </div>
        <div class="nav-item dropdown">
          Weather <i class="fas fa-chevron-down"></i>
        </div>
        <button class="icon-btn"><i class="fas fa-th"></i></button>
        <button class="icon-btn"><i class="fas fa-search"></i></button>
        <button class="icon-btn"><i class="fas fa-bell"></i></button>
        <button class="icon-btn"><i class="fas fa-clock"></i></button>
        <button class="icon-btn"><i class="fas fa-eye"></i></button>
        <button class="icon-btn"><i class="fas fa-cog"></i></button>
        <button class="icon-btn user-icon"><i class="fas fa-user"></i></button>
        <button class="update-btn">Update</button>
      </div>
    </div>

    <!-- Alarm Banner -->
    <div class="alarm-banner">
      <div class="alarm-content">
        <i class="fas fa-bell"></i>
        <span class="alarm-text">2 active alarms in your groups!</span>
      </div>
      <div class="alarm-groups">
        <span>Aishwarya_QA Group</span>
        <span class="separator">|</span>
        <span>After Fix emergency Test</span>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'Header'
}
</script>

<style scoped>
.header {
  background-color: #fff;
  box-shadow: 0 2px 4px rgba(0,0,0,0.1);
}

.nav-bar {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  min-height: 60px;
  background-color: #5c6c7d;
  color: white;
  overflow-x: auto;
  overflow-y: hidden;
}

.nav-left {
  display: flex;
  align-items: center;
  gap: 10px;
  min-width: 0;
  flex: 1;
  overflow-x: auto;
}

.logo-section {
  display: flex;
  align-items: center;
  flex-shrink: 0;
}

.logo-circle {
  width: 40px;
  height: 40px;
  background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
  border-radius: 8px;
  display: flex;
  align-items: center;
  justify-content: center;
  color: white;
  font-size: 20px;
}

.nav-menu {
  display: flex;
  align-items: center;
  gap: 3px;
  overflow-x: auto;
  overflow-y: hidden;
  flex: 1;
  min-width: 0;
  padding: 5px 0;
}

/* Hide scrollbar but keep functionality */
.nav-menu::-webkit-scrollbar {
  height: 0;
}

.nav-menu {
  scrollbar-width: none;
}

.nav-item {
  padding: 8px 10px;
  color: white !important;
  text-decoration: none;
  font-size: 13px;
  cursor: pointer;
  white-space: nowrap;
  transition: background-color 0.2s;
  border-radius: 4px;
  flex-shrink: 0;
}

.nav-item:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.nav-item.dropdown {
  display: flex;
  align-items: center;
  gap: 5px;
  color: white !important;
}

.nav-right {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-shrink: 0;
  padding-left: 10px;
}

.icon-btn {
  background: none;
  border: none;
  color: white !important;
  font-size: 16px;
  cursor: pointer;
  padding: 8px;
  border-radius: 4px;
  transition: background-color 0.2s;
  flex-shrink: 0;
}

.icon-btn:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.user-icon {
  background-color: rgba(255, 255, 255, 0.2);
  border-radius: 50%;
}

.update-btn {
  background-color: transparent;
  color: white !important;
  border: 1px solid white;
  padding: 6px 14px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 13px;
  transition: all 0.2s;
  white-space: nowrap;
  flex-shrink: 0;
}

.update-btn:hover {
  background-color: white;
  color: #5c6c7d !important;
}

.alarm-banner {
  background-color: #dc3545;
  color: white;
  padding: 12px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  flex-wrap: wrap;
  gap: 10px;
}

.alarm-content {
  display: flex;
  align-items: center;
  gap: 10px;
  font-weight: 600;
}

.alarm-content i {
  font-size: 18px;
}

.alarm-groups {
  display: flex;
  align-items: center;
  gap: 10px;
  font-size: 14px;
  flex-wrap: wrap;
}

.separator {
  opacity: 0.7;
}

/* Responsive adjustments */
@media (max-width: 1400px) {
  .nav-item {
    font-size: 12px;
    padding: 6px 8px;
  }

  .nav-menu {
    gap: 2px;
  }
}

@media (max-width: 1024px) {
  .nav-bar {
    padding: 0 10px;
  }

  .nav-left {
    gap: 5px;
  }

  .nav-right {
    gap: 5px;
  }

  .icon-btn {
    font-size: 14px;
    padding: 6px;
  }
}
</style>
