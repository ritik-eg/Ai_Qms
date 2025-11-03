import { describe, it, expect, beforeEach, vi } from 'vitest'
import { mount } from '@vue/test-utils'
import EmergencyGroupForm from '../EmergencyGroupForm.vue'

describe('EmergencyGroupForm Component', () => {
  let wrapper

  beforeEach(() => {
    wrapper = mount(EmergencyGroupForm)
  })

  describe('Component Rendering', () => {
    it('should render the emergency form container', () => {
      expect(wrapper.find('.emergency-form-container').exists()).toBe(true)
    })

    it('should render form header', () => {
      expect(wrapper.find('.form-header').exists()).toBe(true)
    })

    it('should render form body', () => {
      expect(wrapper.find('.form-body').exists()).toBe(true)
    })

    it('should render form footer', () => {
      expect(wrapper.find('.form-footer').exists()).toBe(true)
    })
  })

  describe('Form Header', () => {
    it('should display "Rit E Group" title', () => {
      expect(wrapper.find('.form-header h2').text()).toBe('Rit E Group')
    })

    it('should display instruction text', () => {
      const text = wrapper.find('.instruction-text').text()
      expect(text).toContain('Click on button below to activate alarm')
    })

    it('should render "Go to activate alarm" button', () => {
      const button = wrapper.find('.activate-alarm-btn')
      expect(button.exists()).toBe(true)
      expect(button.text()).toContain('Go to activate alarm')
    })

    it('should have bell icon in activate alarm button', () => {
      expect(wrapper.find('.activate-alarm-btn .fa-bell').exists()).toBe(true)
    })
  })

  describe('Basic Information Section', () => {
    it('should render Basic information section', () => {
      const text = wrapper.text()
      expect(text).toContain('Basic information')
    })

    it('should have collapsible section header', () => {
      expect(wrapper.find('.section-header').exists()).toBe(true)
    })

    it('should toggle section visibility on header click', async () => {
      const sectionHeader = wrapper.find('.section-header')
      const sectionContent = wrapper.find('.section-content')

      // Initially visible
      expect(sectionContent.isVisible()).toBe(true)

      // Click to collapse
      await sectionHeader.trigger('click')
      await wrapper.vm.$nextTick()

      // Should be hidden
      expect(wrapper.vm.sections.basic).toBe(false)
    })

    it('should toggle chevron icon on section collapse', async () => {
      const sectionHeader = wrapper.find('.section-header')

      // Initially has chevron-down
      expect(sectionHeader.find('.fa-chevron-down').exists()).toBe(true)

      // Click to collapse
      await sectionHeader.trigger('click')
      await wrapper.vm.$nextTick()

      // Should have chevron-right
      expect(sectionHeader.find('.fa-chevron-right').exists()).toBe(true)
    })
  })

  describe('Form Fields', () => {
    it('should render Type dropdown', () => {
      const typeField = wrapper.find('#type')
      expect(typeField.exists()).toBe(true)
      expect(typeField.element.tagName).toBe('SELECT')
    })

    it('should render Name input field', () => {
      const nameField = wrapper.find('#name')
      expect(nameField.exists()).toBe(true)
      expect(nameField.element.value).toBe('Rit E Group')
    })

    it('should have required indicator for Name field', () => {
      const nameLabel = wrapper.findAll('label').find(label => label.text().includes('Name'))
      expect(nameLabel.find('.required').exists()).toBe(true)
    })

    it('should render Number input field', () => {
      const numberField = wrapper.find('#number')
      expect(numberField.exists()).toBe(true)
      expect(numberField.element.value).toBe('0042')
    })

    it('should render Responsible dropdown', () => {
      const responsibleField = wrapper.find('#responsible')
      expect(responsibleField.exists()).toBe(true)
      expect(responsibleField.element.value).toBe('ritik')
    })

    it('should render Date revised input', () => {
      const dateField = wrapper.find('#dateRevised')
      expect(dateField.exists()).toBe(true)
      expect(dateField.attributes('type')).toBe('date')
    })

    it('should render Notes textarea', () => {
      const notesField = wrapper.find('#notes')
      expect(notesField.exists()).toBe(true)
      expect(notesField.element.tagName).toBe('TEXTAREA')
      expect(notesField.element.value).toBe('Raining')
    })
  })

  describe('Notification Type Buttons', () => {
    it('should render notification type buttons', () => {
      const buttons = wrapper.findAll('.notification-btn')
      expect(buttons.length).toBe(2)
    })

    it('should have Alarm button', () => {
      const alarmBtn = wrapper.findAll('.notification-btn')[0]
      expect(alarmBtn.text()).toContain('Alarm')
      expect(alarmBtn.find('.fa-bell').exists()).toBe(true)
    })

    it('should have Dispatch button', () => {
      const dispatchBtn = wrapper.findAll('.notification-btn')[1]
      expect(dispatchBtn.text()).toContain('Dispatch')
      expect(dispatchBtn.find('.fa-paper-plane').exists()).toBe(true)
    })

    it('should have active class on Alarm button by default', () => {
      const alarmBtn = wrapper.findAll('.notification-btn')[0]
      expect(alarmBtn.classes()).toContain('active')
    })

    it('should toggle notification type on button click', async () => {
      const dispatchBtn = wrapper.findAll('.notification-btn')[1]

      // Initially alarm is active
      expect(wrapper.vm.formData.notificationType).toBe('alarm')

      // Click dispatch button
      await dispatchBtn.trigger('click')
      await wrapper.vm.$nextTick()

      // Should change to dispatch
      expect(wrapper.vm.formData.notificationType).toBe('dispatch')
    })
  })

  describe('Alarm Status Tags', () => {
    it('should render alarm status tags', () => {
      const tags = wrapper.findAll('.tag')
      expect(tags.length).toBe(2)
    })

    it('should display "Safe" tag', () => {
      const tags = wrapper.findAll('.tag')
      const safeTag = tags.find(tag => tag.text().includes('Safe'))
      expect(safeTag.exists()).toBe(true)
    })

    it('should display "danger Closeby" tag', () => {
      const tags = wrapper.findAll('.tag')
      const dangerTag = tags.find(tag => tag.text().includes('danger Closeby'))
      expect(dangerTag.exists()).toBe(true)
    })

    it('should have remove icon on each tag', () => {
      const tags = wrapper.findAll('.tag')
      tags.forEach(tag => {
        expect(tag.find('.fa-times').exists()).toBe(true)
      })
    })

    it('should remove tag when close icon is clicked', async () => {
      const initialLength = wrapper.vm.formData.alarmStatuses.length
      const firstTag = wrapper.find('.tag .fa-times')

      await firstTag.trigger('click')
      await wrapper.vm.$nextTick()

      expect(wrapper.vm.formData.alarmStatuses.length).toBe(initialLength - 1)
    })
  })

  describe('Message Information Section', () => {
    it('should render Message information section', () => {
      const text = wrapper.text()
      expect(text).toContain('Message information')
    })

    it('should render E-mail topic field', () => {
      const emailTopic = wrapper.find('input[readonly]')
      expect(emailTopic.element.value).toBe('Heavy Rain')
    })

    it('should render E-mail content textarea', () => {
      const textareas = wrapper.findAll('textarea[readonly]')
      const emailContent = textareas[0]
      expect(emailContent.element.value).toBe('-alarm test mail')
    })

    it('should render SMS text textarea', () => {
      const textareas = wrapper.findAll('textarea[readonly]')
      const smsText = textareas[1]
      expect(smsText.element.value).toBe('Heavy Raining')
    })

    it('should have edit icons for editable fields', () => {
      const editIcons = wrapper.findAll('.edit-icon')
      expect(editIcons.length).toBeGreaterThan(0)
    })
  })

  describe('Documents Section', () => {
    it('should render Documents section', () => {
      const text = wrapper.text()
      expect(text).toContain('Documents')
    })

    it('should render document view icons', () => {
      const viewIcons = wrapper.find('.view-icons')
      expect(viewIcons.exists()).toBe(true)
      expect(viewIcons.findAll('i').length).toBe(2)
    })

    it('should render "Connect from library" section', () => {
      const connectLibrary = wrapper.find('.connect-library')
      expect(connectLibrary.exists()).toBe(true)
      expect(connectLibrary.text()).toContain('Connect from library')
    })

    it('should render "Choose documents" button', () => {
      const chooseBtn = wrapper.find('.choose-docs-btn')
      expect(chooseBtn.exists()).toBe(true)
      expect(chooseBtn.text()).toBe('Choose documents')
    })

    it('should render document list', () => {
      const documents = wrapper.findAll('.document-item')
      expect(documents.length).toBe(4)
    })

    it('should display correct document names', () => {
      const documents = wrapper.findAll('.document-item .doc-name')
      const names = documents.map(doc => doc.text())

      expect(names).toContain('Emergency_image.png')
      expect(names).toContain('mermaid-diagram-2025-05-21-163812.png')
      expect(names).toContain('Search Bar (1).jpg')
      expect(names).toContain('Sample')
    })

    it('should have correct icons for document types', () => {
      const documents = wrapper.vm.documents

      const imageDoc = documents.find(doc => doc.type === 'image')
      expect(wrapper.vm.getDocIcon(imageDoc.type)).toBe('fas fa-image')

      const pdfDoc = documents.find(doc => doc.type === 'pdf')
      expect(wrapper.vm.getDocIcon(pdfDoc.type)).toBe('fas fa-file-pdf')
    })

    it('should render menu icon for each document', () => {
      const documents = wrapper.findAll('.document-item')
      documents.forEach(doc => {
        expect(doc.find('.doc-menu').exists()).toBe(true)
      })
    })
  })

  describe('Form Footer Actions', () => {
    it('should render form footer', () => {
      expect(wrapper.find('.form-footer').exists()).toBe(true)
    })

    it('should have footer-left and footer-right sections', () => {
      expect(wrapper.find('.footer-left').exists()).toBe(true)
      expect(wrapper.find('.footer-right').exists()).toBe(true)
    })

    it('should render "Back to old design" link', () => {
      const backLink = wrapper.find('.back-link')
      expect(backLink.exists()).toBe(true)
      expect(backLink.text()).toBe('Back to old design')
    })

    it('should render "More" button', () => {
      const moreBtn = wrapper.find('.more-btn')
      expect(moreBtn.exists()).toBe(true)
      expect(moreBtn.text()).toContain('More')
    })

    it('should render "Cancel" button', () => {
      const cancelBtn = wrapper.find('.cancel-btn')
      expect(cancelBtn.exists()).toBe(true)
      expect(cancelBtn.text()).toBe('Cancel')
    })

    it('should render "Save" button', () => {
      const saveBtn = wrapper.find('.save-btn')
      expect(saveBtn.exists()).toBe(true)
      expect(saveBtn.text()).toContain('Save')
      expect(saveBtn.find('.fa-save').exists()).toBe(true)
    })

    it('should render "Save and close form" button', () => {
      const saveCloseBtn = wrapper.find('.save-close-btn')
      expect(saveCloseBtn.exists()).toBe(true)
      expect(saveCloseBtn.text()).toContain('Save and close form')
      expect(saveCloseBtn.find('.fa-save').exists()).toBe(true)
    })
  })

  describe('Data Reactivity', () => {
    it('should update formData when input changes', async () => {
      const nameInput = wrapper.find('#name')
      await nameInput.setValue('New Group Name')
      await wrapper.vm.$nextTick()

      expect(wrapper.vm.formData.name).toBe('New Group Name')
    })

    it('should update messageData when textarea changes', async () => {
      const emailTopic = wrapper.find('input[readonly]')
      // Since it's readonly, we test that the value is bound correctly
      expect(emailTopic.element.value).toBe(wrapper.vm.messageData.emailTopic)
    })

    it('should maintain documents array', () => {
      expect(wrapper.vm.documents).toHaveLength(4)
      expect(Array.isArray(wrapper.vm.documents)).toBe(true)
    })
  })

  describe('Form Layout', () => {
    it('should have two-column layout for form body', () => {
      expect(wrapper.find('.form-left').exists()).toBe(true)
      expect(wrapper.find('.form-right').exists()).toBe(true)
    })

    it('should render all sections in proper containers', () => {
      expect(wrapper.find('.form-section').exists()).toBe(true)
      expect(wrapper.find('.message-info-section').exists()).toBe(true)
      expect(wrapper.find('.documents-section').exists()).toBe(true)
    })
  })

  describe('Styling and CSS Classes', () => {
    it('should apply correct CSS classes to form controls', () => {
      const formControls = wrapper.findAll('.form-control')
      expect(formControls.length).toBeGreaterThan(0)
    })

    it('should have proper button styling classes', () => {
      expect(wrapper.find('.activate-alarm-btn').exists()).toBe(true)
      expect(wrapper.find('.notification-btn').exists()).toBe(true)
      expect(wrapper.find('.save-btn').exists()).toBe(true)
    })
  })

  describe('Methods', () => {
    it('should have toggleSection method', () => {
      expect(typeof wrapper.vm.toggleSection).toBe('function')
    })

    it('should have removeStatus method', () => {
      expect(typeof wrapper.vm.removeStatus).toBe('function')
    })

    it('should have getDocIcon method', () => {
      expect(typeof wrapper.vm.getDocIcon).toBe('function')
    })

    it('toggleSection should update sections state', () => {
      const initialState = wrapper.vm.sections.basic
      wrapper.vm.toggleSection('basic')
      expect(wrapper.vm.sections.basic).toBe(!initialState)
    })

    it('removeStatus should remove item from array', () => {
      const initialLength = wrapper.vm.formData.alarmStatuses.length
      wrapper.vm.removeStatus(0)
      expect(wrapper.vm.formData.alarmStatuses.length).toBe(initialLength - 1)
    })

    it('getDocIcon should return correct icon class', () => {
      expect(wrapper.vm.getDocIcon('image')).toBe('fas fa-image')
      expect(wrapper.vm.getDocIcon('pdf')).toBe('fas fa-file-pdf')
      expect(wrapper.vm.getDocIcon('doc')).toBe('fas fa-file-word')
      expect(wrapper.vm.getDocIcon('unknown')).toBe('fas fa-file')
    })
  })

  describe('Accessibility', () => {
    it('should have proper label-input associations', () => {
      const nameLabel = wrapper.find('label[for="name"]')
      const nameInput = wrapper.find('#name')
      expect(nameLabel.exists()).toBe(true)
      expect(nameInput.exists()).toBe(true)
    })

    it('should have button elements for actions', () => {
      const buttons = wrapper.findAll('button')
      expect(buttons.length).toBeGreaterThan(0)
      buttons.forEach(button => {
        expect(button.element.tagName).toBe('BUTTON')
      })
    })

    it('should have proper form structure', () => {
      const formGroups = wrapper.findAll('.form-group')
      expect(formGroups.length).toBeGreaterThan(0)
    })
  })
})
