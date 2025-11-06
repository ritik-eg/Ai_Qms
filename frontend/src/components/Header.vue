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
  height: 60px;
  background-color: #5c6c7d;
  color: white;
}

.nav-left {
  display: flex;
  align-items: center;
  gap: 20px;
}

.logo-section {
  display: flex;
  align-items: center;
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
  gap: 5px;
}

.nav-item {
  padding: 8px 12px;
  color: white;
  text-decoration: none;
  font-size: 14px;
  cursor: pointer;
  white-space: nowrap;
  transition: background-color 0.2s;
  border-radius: 4px;
}

.nav-item:hover {
  background-color: rgba(255, 255, 255, 0.1);
}

.nav-item.dropdown {
  display: flex;
  align-items: center;
  gap: 5px;
}

.nav-right {
  display: flex;
  align-items: center;
  gap: 10px;
}

.icon-btn {
  background: none;
  border: none;
  color: white;
  font-size: 18px;
  cursor: pointer;
  padding: 8px;
  border-radius: 4px;
  transition: background-color 0.2s;
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
  color: white;
  border: 1px solid white;
  padding: 6px 16px;
  border-radius: 4px;
  cursor: pointer;
  font-size: 14px;
  transition: all 0.2s;
}

.update-btn:hover {
  background-color: white;
  color: #5c6c7d;
}

.alarm-banner {
  background-color: #dc3545;
  color: white;
  padding: 12px 20px;
  display: flex;
  justify-content: space-between;
  align-items: center;
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
}

.separator {
  opacity: 0.7;
}
</style>
