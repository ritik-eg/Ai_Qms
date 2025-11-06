<template>
  <div class="app">
    <Header />
    <div class="main-container">
      <Sidebar />
      <div class="content-wrapper">
        <EmergencyGroupForm />
      </div>
    </div>
  </div>
</template>

<script>
import Header from './components/Header.vue'
import Sidebar from './components/Sidebar.vue'
import EmergencyGroupForm from './components/EmergencyGroupForm.vue'

export default {
  name: 'App',
  components: {
    Header,
    Sidebar,
    EmergencyGroupForm
  }
}
</script>

<style scoped>
.app {
  min-height: 100vh;
  display: flex;
  flex-direction: column;
  background-color: #f5f5f5;
}

.main-container {
  display: flex;
  flex: 1;
  overflow: hidden;
}

.content-wrapper {
  flex: 1;
  overflow-y: auto;
  background-color: #ffffff;
}
</style>
