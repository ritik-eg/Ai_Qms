<template>
  <div class="emergency-form-container">
    <div class="form-content">
      <!-- Header Section -->
      <div class="form-header">
        <h2>Rit E Group</h2>
        <p class="instruction-text">Click on button below to activate alarm (then you can select alarm methods and edit alarm messages)</p>
        <button class="activate-alarm-btn">
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
                <label for="type">Type</label>
                <select id="type" v-model="formData.type" class="form-control">
                  <option value="">Select type...</option>
                  <option value="emergency">Emergency</option>
                  <option value="drill">Drill</option>
                </select>
              </div>

              <div class="form-group">
                <label for="name">Name <span class="required">*</span></label>
                <input
                  type="text"
                  id="name"
                  v-model="formData.name"
                  class="form-control"
                  placeholder="Rit E Group"
                />
              </div>

              <div class="form-group">
                <label for="number">Number</label>
                <input
                  type="text"
                  id="number"
                  v-model="formData.number"
                  class="form-control"
                  placeholder="0042"
                />
              </div>

              <div class="form-group">
                <label>Notification type</label>
                <div class="notification-buttons">
                  <button
                    :class="['notification-btn', { active: formData.notificationType === 'alarm' }]"
                    @click="formData.notificationType = 'alarm'"
                  >
                    <i class="fas fa-bell"></i> Alarm
                  </button>
                  <button
                    :class="['notification-btn', { active: formData.notificationType === 'dispatch' }]"
                    @click="formData.notificationType = 'dispatch'"
                  >
                    <i class="fas fa-paper-plane"></i> Dispatch
                  </button>
                </div>
              </div>

              <div class="form-group">
                <label for="responsible">Responsible</label>
                <select id="responsible" v-model="formData.responsible" class="form-control">
                  <option value="">Select responsible...</option>
                  <option value="ritik">Ritik Ranjan</option>
                  <option value="other">Other</option>
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
              <input
                type="text"
                v-model="messageData.emailTopic"
                class="form-control"
                readonly
              />
            </div>

            <div class="form-group">
              <label>
                E-mail content
                <i class="fas fa-edit edit-icon"></i>
              </label>
              <textarea
                v-model="messageData.emailContent"
                class="form-control"
                rows="4"
                readonly
              ></textarea>
            </div>

            <div class="form-group">
              <label>SMS text</label>
              <textarea
                v-model="messageData.smsText"
                class="form-control"
                rows="4"
                readonly
              ></textarea>
            </div>
          </div>

          <!-- Documents Section -->
          <div class="documents-section">
            <div class="documents-header">
              <h3>Documents</h3>
              <div class="view-icons">
                <i class="fas fa-th-large"></i>
                <i class="fas fa-list"></i>
              </div>
            </div>

            <div class="documents-body">
              <div class="connect-library">
                <i class="fas fa-folder"></i>
                <p>Connect from library</p>
                <button class="choose-docs-btn">Choose documents</button>
              </div>

              <div class="document-list">
                <div
                  v-for="(doc, index) in documents"
                  :key="index"
                  class="document-item"
                >
                  <i :class="getDocIcon(doc.type)"></i>
                  <span class="doc-name">{{ doc.name }}</span>
                  <i class="fas fa-ellipsis-v doc-menu"></i>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>

      <!-- Footer Actions -->
      <div class="form-footer">
        <div class="footer-left">
          <a href="#" class="back-link">Back to old design</a>
          <button class="more-btn">
            More <i class="fas fa-chevron-down"></i>
          </button>
          <button class="cancel-btn">Cancel</button>
        </div>
        <div class="footer-right">
          <button class="save-btn">
            <i class="fas fa-save"></i> Save
          </button>
          <button class="save-close-btn">
            <i class="fas fa-save"></i> Save and close form
          </button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
  name: 'EmergencyGroupForm',
  data() {
    return {
      sections: {
        basic: true
      },
      formData: {
        type: '',
        name: 'Rit E Group',
        number: '0042',
        notificationType: 'alarm',
        responsible: 'ritik',
        dateRevised: '',
        notes: 'Raining',
        alarmStatuses: ['Safe', 'danger Closeby']
      },
      messageData: {
        emailTopic: 'Heavy Rain',
        emailContent: '-alarm test mail',
        smsText: 'Heavy Raining'
      },
      documents: [
        { name: 'Emergency_image.png', type: 'image' },
        { name: 'mermaid-diagram-2025-05-21-163812.png', type: 'image' },
        { name: 'Search Bar (1).jpg', type: 'image' },
        { name: 'Sample', type: 'pdf' }
      ]
    }
  },
  methods: {
    toggleSection(section) {
      this.sections[section] = !this.sections[section]
    },
    removeStatus(index) {
      this.formData.alarmStatuses.splice(index, 1)
    },
    getDocIcon(type) {
      const icons = {
        image: 'fas fa-image',
        pdf: 'fas fa-file-pdf',
        doc: 'fas fa-file-word'
      }
      return icons[type] || 'fas fa-file'
    }
  }
}
</script>

<style scoped>
.emergency-form-container {
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
  padding: 10px 20px;
  border-radius: 4px;
  font-size: 14px;
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

.connect-library {
  text-align: center;
  padding: 30px;
  border: 2px dashed #dee2e6;
  border-radius: 8px;
  margin-bottom: 20px;
}

.connect-library i {
  font-size: 40px;
  color: #adb5bd;
  margin-bottom: 10px;
}

.connect-library p {
  color: #6c757d;
  font-size: 14px;
  margin-bottom: 15px;
}

.choose-docs-btn {
  background-color: white;
  color: #0066cc;
  border: 1px solid #0066cc;
  padding: 8px 16px;
  border-radius: 4px;
  font-size: 14px;
  cursor: pointer;
  transition: all 0.2s;
}

.choose-docs-btn:hover {
  background-color: #0066cc;
  color: white;
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

.document-item > i:first-child {
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

.doc-menu {
  color: #6c757d;
  cursor: pointer;
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

.more-btn:hover,
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
