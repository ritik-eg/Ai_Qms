<template>
  <div class="activate-alarm-container">
    <div class="form-content">
      <!-- Header Section -->
      <div class="form-header">
        <h2>{{ groupData.name }}</h2>
        <p class="instruction-text">Click on button below to activate alarm (then you can select alarm methods and edit alarm messages)</p>
        <button class="activate-alarm-btn" @click="activateAlarm">
          <i class="fas fa-bell"></i> Go to activate alarm
        </button>
      </div>

      <!-- Main Form -->
      <div class="form-body">
        <div class="form-left">
          <!-- Basic Information Section -->
          <div class="form-section">
            <div class="section-header" @click="toggleSection('basic')">
              <i :class="['fas', sections.basic ? 'fa-chevron-down' : 'fa-chevron-right']"></i>
              <h3>Basic information</h3>
            </div>
            <div v-show="sections.basic" class="section-content">
              <div class="form-group">
                <label>Type</label>
                <div class="form-display">{{ groupData.type || '-' }}</div>
              </div>

              <div class="form-group">
                <label>Name</label>
                <div class="form-display">{{ groupData.name }}</div>
              </div>

              <div class="form-group">
                <label>Number</label>
                <div class="form-display">{{ groupData.number }}</div>
              </div>

              <div class="form-group">
                <label>Notification type</label>
                <div class="notification-display">
                  <span :class="['notification-badge', groupData.notificationType]">
                    <i :class="['fas', groupData.notificationType === 'alarm' ? 'fa-bell' : 'fa-paper-plane']"></i>
                    {{ groupData.notificationType === 'alarm' ? 'Alarm' : 'Dispatch' }}
                  </span>
                </div>
              </div>

              <div class="form-group">
                <label>Responsible</label>
                <div class="form-display">{{ groupData.responsible }}</div>
              </div>

              <div class="form-group" v-if="groupData.dateRevised">
                <label>Date revised</label>
                <div class="form-display">{{ groupData.dateRevised }}</div>
              </div>

              <div class="form-group" v-if="groupData.notes">
                <label>Notes</label>
                <div class="form-display notes-display">{{ groupData.notes }}</div>
              </div>

              <div class="form-group" v-if="groupData.alarmStatuses && groupData.alarmStatuses.length > 0">
                <label>Alarm statuses</label>
                <div class="tag-container">
                  <span
                    v-for="(status, index) in groupData.alarmStatuses"
                    :key="index"
                    class="tag"
                  >
                    {{ status }}
                  </span>
                </div>
              </div>
            </div>
          </div>
        </div>

        <div class="form-right">
          <!-- Message Information -->
          <div class="message-info-section">
            <h3>Message information</h3>

            <div class="form-group">
              <label>E-mail topic</label>
              <div class="form-display">{{ messageData.emailTopic || '-' }}</div>
            </div>

            <div class="form-group">
              <label>E-mail content</label>
              <div class="form-display message-content">{{ messageData.emailContent || '-' }}</div>
            </div>

            <div class="form-group">
              <label>SMS text</label>
              <div class="form-display message-content">{{ messageData.smsText || '-' }}</div>
            </div>
          </div>

          <!-- Documents Section -->
          <div class="documents-section" v-if="documents && documents.length > 0">
            <div class="documents-header">
              <h3>Documents</h3>
              <div class="view-icons">
                <i class="fas fa-th-large"></i>
                <i class="fas fa-list"></i>
              </div>
            </div>

            <div class="document-list">
              <div
                v-for="(doc, index) in documents"
                :key="index"
                class="document-item"
              >
                <i :class="getDocIcon(doc.type)"></i>
                <span class="doc-name">{{ doc.name }}</span>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Footer Actions -->
      <div class="form-footer">
        <div class="footer-left">
          <a href="#" class="back-link" @click.prevent="goBack">Back to old design</a>
          <button class="more-btn" type="button">
            More <i class="fas fa-chevron-down"></i>
          </button>
          <button class="cancel-btn" type="button" @click="cancel">Cancel</button>
        </div>
        <div class="footer-right">
          <button class="close-btn" type="button" @click="closeForm">
            <i class="fas fa-times"></i> Close form
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'ActivateAlarm',
  data() {
    return {
      sections: {
        basic: true
      },
      groupData: {
        name: 'Dev emergency group',
        type: 'testing emergency',
        number: '0047',
        notificationType: 'alarm',
        responsible: 'Sujeena Shetty',
        dateRevised: '',
        notes: 'Please respond',
        alarmStatuses: []
      },
      messageData: {
        emailTopic: '',
        emailContent: '',
        smsText: ''
      },
      documents: []
    }
  },
  methods: {
    toggleSection(section) {
      this.sections[section] = !this.sections[section]
    },
    activateAlarm() {
      // Show confirmation
      if (confirm('Are you sure you want to activate this alarm?')) {
        // Create alarm object
        const newAlarm = {
          id: Date.now(),
          group: this.groupData.name,
          activatedDate: new Date().toLocaleString('en-GB'),
          activatedBy: 'Current User', // Replace with actual user
          methods: {
            email: true,
            phone: false,
            sms: true,
            app: false
          },
          deactivated: '',
          deactivatedBy: '',
          statistics: {
            confirmed: 0,
            comments: 0,
            failed: 0
          }
        }

        // Show success message
        alert('Alarm activated')

        // Navigate to active alarms page with the new alarm
        this.$router.push({
          name: 'ActiveAlarms',
          params: { newAlarm: JSON.stringify(newAlarm) }
        })
      }
    },
    getDocIcon(type) {
      const icons = {
        image: 'fas fa-image',
        pdf: 'fas fa-file-pdf',
        doc: 'fas fa-file-word'
      }
      return icons[type] || 'fas fa-file'
    },
    goBack() {
      this.$router.go(-1)
    },
    cancel() {
      this.$router.push('/emergency-groups')
    },
    closeForm() {
      this.$router.push('/emergency-groups')
    }
  },
  mounted() {
    // Load group data based on route params
    const groupId = this.$route.params.id
    if (groupId) {
      // Here you would fetch the group data from API
      // For now, we'll use mock data
      console.log('Loading group:', groupId)
    }
  }
}
</script>

<style scoped>
.activate-alarm-container {
  padding: 20px;
  background-color: #ffffff;
}

.form-content {
  max-width: 1400px;
  margin: 0 auto;
}

.form-header {
  margin-bottom: 30px;
}

.form-header h2 {
  font-size: 24px;
  color: #333;
  margin-bottom: 10px;
}

.instruction-text {
  color: #666;
  font-size: 14px;
  margin-bottom: 15px;
}

.activate-alarm-btn {
  background-color: #dc3545;
  color: white;
  border: none;
  padding: 12px 24px;
  border-radius: 4px;
  font-size: 14px;
  font-weight: 500;
  cursor: pointer;
  display: flex;
  align-items: center;
  gap: 8px;
  transition: background-color 0.2s;
}

.activate-alarm-btn:hover {
  background-color: #c82333;
}

.form-body {
  display: grid;
  grid-template-columns: 1fr 1fr;
  gap: 30px;
  margin-bottom: 30px;
}

.form-section {
  background-color: #fff;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  margin-bottom: 20px;
  overflow: hidden;
}

.section-header {
  display: flex;
  align-items: center;
  gap: 10px;
  padding: 15px;
  background-color: #f8f9fa;
  cursor: pointer;
  border-bottom: 1px solid #dee2e6;
}

.section-header h3 {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin: 0;
}

.section-header i {
  font-size: 12px;
  color: #666;
}

.section-content {
  padding: 20px;
}

.form-group {
  margin-bottom: 20px;
}

.form-group label {
  display: block;
  font-size: 14px;
  font-weight: 500;
  color: #333;
  margin-bottom: 8px;
}

.form-display {
  padding: 10px 12px;
  background-color: #f8f9fa;
  border: 1px solid #dee2e6;
  border-radius: 4px;
  font-size: 14px;
  color: #495057;
  min-height: 40px;
}

.notes-display,
.message-content {
  min-height: 80px;
  white-space: pre-wrap;
}

.notification-display {
  padding: 5px 0;
}

.notification-badge {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  border-radius: 4px;
  font-size: 14px;
  font-weight: 500;
}

.notification-badge.alarm {
  background-color: #e7f3ff;
  color: #0066cc;
  border: 1px solid #0066cc;
}

.notification-badge.dispatch {
  background-color: #e8f5e9;
  color: #2e7d32;
  border: 1px solid #2e7d32;
}

.tag-container {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.tag {
  display: inline-flex;
  align-items: center;
  padding: 6px 12px;
  background-color: #e9ecef;
  border-radius: 4px;
  font-size: 13px;
  color: #495057;
}

.message-info-section,
.documents-section {
  background-color: #fff;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
}

.message-info-section h3,
.documents-section h3 {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-bottom: 20px;
}

.documents-header {
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 20px;
}

.documents-header h3 {
  margin: 0;
}

.view-icons {
  display: flex;
  gap: 10px;
}

.view-icons i {
  color: #666;
  cursor: pointer;
  padding: 5px;
}

.view-icons i:hover {
  color: #333;
}

.document-list {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

.document-item {
  display: flex;
  align-items: center;
  gap: 12px;
  padding: 12px;
  border: 1px solid #dee2e6;
  border-radius: 4px;
  transition: background-color 0.2s;
}

.document-item:hover {
  background-color: #f8f9fa;
}

.document-item > i {
  font-size: 20px;
  color: #6c757d;
  width: 30px;
  text-align: center;
}

.doc-name {
  flex: 1;
  font-size: 14px;
  color: #333;
}

.form-footer {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 20px 0;
  border-top: 1px solid #dee2e6;
}

.footer-left,
.footer-right {
  display: flex;
  align-items: center;
  gap: 15px;
}

.back-link {
  color: #0066cc;
  text-decoration: none;
  font-size: 14px;
}

.back-link:hover {
  text-decoration: underline;
}

.more-btn,
.cancel-btn,
.close-btn {
  padding: 10px 20px;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
  display: flex;
  align-items: center;
  gap: 8px;
}

.more-btn,
.cancel-btn {
  background-color: white;
  color: #495057;
  border: 1px solid #ced4da;
}

.more-btn:hover,
.cancel-btn:hover {
  background-color: #f8f9fa;
}

.close-btn {
  background-color: #17a2b8;
  color: white;
  border: none;
}

.close-btn:hover {
  background-color: #138496;
}

@media (max-width: 1200px) {
  .form-body {
    grid-template-columns: 1fr;
  }
}
</style>
