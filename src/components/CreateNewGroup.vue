<template>
  <div class="create-new-group-container">
    <div class="form-content">
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
                <label for="type">Type</label>
                <select id="type" v-model="formData.type" class="form-control">
                  <option value="">Select type...</option>
                  <option value="emergency">Emergency</option>
                  <option value="drill">Drill</option>
                  <option value="notification">Notification</option>
                </select>
              </div>

              <div class="form-group">
                <label for="name">Name <span class="required">*</span></label>
                <input
                  type="text"
                  id="name"
                  v-model="formData.name"
                  class="form-control"
                  placeholder="Enter group name"
                />
              </div>

              <div class="form-group">
                <label for="number">Number</label>
                <input
                  type="text"
                  id="number"
                  v-model="formData.number"
                  class="form-control"
                  placeholder="Enter number"
                />
              </div>

              <div class="form-group">
                <label>Notification type</label>
                <div class="notification-buttons">
                  <button
                    :class="['notification-btn', { active: formData.notificationType === 'alarm' }]"
                    @click="formData.notificationType = 'alarm'"
                    type="button"
                  >
                    <i class="fas fa-bell"></i> Alarm
                  </button>
                  <button
                    :class="['notification-btn', { active: formData.notificationType === 'dispatch' }]"
                    @click="formData.notificationType = 'dispatch'"
                    type="button"
                  >
                    <i class="fas fa-paper-plane"></i> Dispatch
                  </button>
                </div>
              </div>

              <div class="form-group">
                <label for="responsible">Responsible</label>
                <select id="responsible" v-model="formData.responsible" class="form-control">
                  <option value="">Select responsible person...</option>
                  <option value="user1">User 1</option>
                  <option value="user2">User 2</option>
                  <option value="user3">User 3</option>
                </select>
              </div>

              <div class="form-group">
                <label for="dateRevised">Date revised</label>
                <input
                  type="date"
                  id="dateRevised"
                  v-model="formData.dateRevised"
                  class="form-control"
                  placeholder="yyyy-MM-dd"
                />
              </div>

              <div class="form-group">
                <label for="notes">
                  Notes
                  <i class="fas fa-edit edit-icon"></i>
                </label>
                <textarea
                  id="notes"
                  v-model="formData.notes"
                  class="form-control"
                  rows="4"
                  placeholder="Enter notes..."
                ></textarea>
              </div>

              <div class="form-group">
                <label>Alarm statuses</label>
                <div class="tag-container">
                  <span
                    v-for="(status, index) in formData.alarmStatuses"
                    :key="index"
                    class="tag"
                  >
                    {{ status }}
                    <i class="fas fa-times" @click="removeStatus(index)"></i>
                  </span>
                </div>
                <button type="button" class="add-status-btn" @click="showAddStatusModal">
                  <i class="fas fa-plus"></i> Add Status
                </button>
              </div>
            </div>
          </div>
        </div>

        <div class="form-right">
          <!-- Message Information -->
          <div class="message-info-section">
            <h3>Message information</h3>

            <div class="form-group">
              <label for="emailTopic">E-mail topic</label>
              <input
                type="text"
                id="emailTopic"
                v-model="messageData.emailTopic"
                class="form-control"
                placeholder="Enter email topic"
              />
            </div>

            <div class="form-group">
              <label for="emailContent">
                E-mail content
                <i class="fas fa-edit edit-icon"></i>
              </label>
              <textarea
                id="emailContent"
                v-model="messageData.emailContent"
                class="form-control"
                rows="4"
                placeholder="Enter email content"
              ></textarea>
            </div>

            <div class="form-group">
              <label for="smsText">SMS text</label>
              <textarea
                id="smsText"
                v-model="messageData.smsText"
                class="form-control"
                rows="4"
                placeholder="Enter SMS text"
              ></textarea>
            </div>
          </div>
        </div>
      </div>

      <!-- Footer Actions -->
      <div class="form-footer">
        <div class="footer-left">
          <a href="#" class="back-link" @click.prevent="goBack">Back to old design</a>
          <button class="cancel-btn" type="button" @click="cancel">Cancel</button>
        </div>
        <div class="footer-right">
          <button class="save-btn" type="button" @click="save">
            <i class="fas fa-save"></i> Save
          </button>
          <button class="save-close-btn" type="button" @click="saveAndClose">
            <i class="fas fa-save"></i> Save and close form
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'CreateNewGroup',
  data() {
    return {
      sections: {
        basic: true
      },
      formData: {
        type: '',
        name: '',
        number: '',
        notificationType: 'alarm',
        responsible: '',
        dateRevised: '',
        notes: '',
        alarmStatuses: []
      },
      messageData: {
        emailTopic: '',
        emailContent: '',
        smsText: ''
      }
    }
  },
  methods: {
    toggleSection(section) {
      this.sections[section] = !this.sections[section]
    },
    removeStatus(index) {
      this.formData.alarmStatuses.splice(index, 1)
    },
    showAddStatusModal() {
      const status = prompt('Enter alarm status:')
      if (status) {
        this.formData.alarmStatuses.push(status)
      }
    },
    goBack() {
      this.$router.go(-1)
    },
    cancel() {
      if (confirm('Are you sure you want to cancel? All unsaved changes will be lost.')) {
        this.$router.push('/emergency-groups')
      }
    },
    save() {
      if (!this.formData.name) {
        alert('Please enter a group name')
        return
      }

      console.log('Saving group:', {
        ...this.formData,
        ...this.messageData
      })

      alert('Group saved successfully!')
    },
    saveAndClose() {
      if (!this.formData.name) {
        alert('Please enter a group name')
        return
      }

      console.log('Saving and closing group:', {
        ...this.formData,
        ...this.messageData
      })

      alert('Group saved successfully!')
      this.$router.push('/emergency-groups')
    }
  }
}
</script>

<style scoped>
.create-new-group-container {
  padding: 20px;
  background-color: #ffffff;
}

.form-content {
  max-width: 1400px;
  margin: 0 auto;
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
  display: flex;
  align-items: center;
  gap: 8px;
  font-size: 14px;
  font-weight: 500;
  color: #333;
  margin-bottom: 8px;
}

.required {
  color: #dc3545;
}

.edit-icon {
  color: #17a2b8;
  cursor: pointer;
  font-size: 12px;
}

.form-control {
  width: 100%;
  padding: 10px 12px;
  border: 1px solid #ced4da;
  border-radius: 4px;
  font-size: 14px;
  transition: border-color 0.2s;
}

.form-control:focus {
  outline: none;
  border-color: #80bdff;
  box-shadow: 0 0 0 0.2rem rgba(0,123,255,.25);
}

select.form-control {
  cursor: pointer;
}

textarea.form-control {
  resize: vertical;
  font-family: inherit;
}

.notification-buttons {
  display: flex;
  gap: 10px;
}

.notification-btn {
  flex: 1;
  padding: 10px 16px;
  border: 1px solid #ced4da;
  background-color: white;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 8px;
  transition: all 0.2s;
}

.notification-btn:hover {
  background-color: #f8f9fa;
}

.notification-btn.active {
  background-color: #e7f3ff;
  border-color: #0066cc;
  color: #0066cc;
}

.tag-container {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
  margin-bottom: 10px;
}

.tag {
  display: inline-flex;
  align-items: center;
  gap: 8px;
  padding: 6px 12px;
  background-color: #e9ecef;
  border-radius: 4px;
  font-size: 13px;
  color: #495057;
}

.tag i {
  cursor: pointer;
  opacity: 0.6;
  transition: opacity 0.2s;
}

.tag i:hover {
  opacity: 1;
}

.add-status-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 8px 16px;
  background-color: white;
  color: #0066cc;
  border: 1px solid #0066cc;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
}

.add-status-btn:hover {
  background-color: #e7f3ff;
}

.message-info-section {
  background-color: #fff;
  border: 1px solid #dee2e6;
  border-radius: 8px;
  padding: 20px;
  margin-bottom: 20px;
}

.message-info-section h3 {
  font-size: 16px;
  font-weight: 600;
  color: #333;
  margin-bottom: 20px;
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

.cancel-btn {
  background-color: white;
  color: #495057;
  border: 1px solid #ced4da;
  padding: 10px 20px;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
}

.cancel-btn:hover {
  background-color: #f8f9fa;
}

.save-btn,
.save-close-btn {
  display: flex;
  align-items: center;
  gap: 8px;
  padding: 10px 20px;
  border: none;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
}

.save-btn {
  background-color: #28a745;
  color: white;
}

.save-btn:hover {
  background-color: #218838;
}

.save-close-btn {
  background-color: #17a2b8;
  color: white;
}

.save-close-btn:hover {
  background-color: #138496;
}

@media (max-width: 1200px) {
  .form-body {
    grid-template-columns: 1fr;
  }
}
</style>
