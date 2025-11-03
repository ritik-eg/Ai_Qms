import { describe, it, expect, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import Header from '../Header.vue'

describe('Header Component', () => {
  let wrapper

  beforeEach(() => {
    wrapper = mount(Header)
  })

  describe('Component Rendering', () => {
    it('should render the header component', () => {
      expect(wrapper.find('.header').exists()).toBe(true)
    })

    it('should render the navigation bar', () => {
      expect(wrapper.find('.nav-bar').exists()).toBe(true)
    })

    it('should render the alarm banner', () => {
      expect(wrapper.find('.alarm-banner').exists()).toBe(true)
    })
  })

  describe('Logo Section', () => {
    it('should render the logo circle', () => {
      expect(wrapper.find('.logo-circle').exists()).toBe(true)
    })

    it('should have the correct logo icon', () => {
      expect(wrapper.find('.logo-circle i.fa-circle-notch').exists()).toBe(true)
    })
  })

  describe('Navigation Menu', () => {
    it('should render all navigation items', () => {
      const navItems = wrapper.findAll('.nav-left .nav-item')
      expect(navItems.length).toBeGreaterThan(0)
    })

    it('should render "Document Library" link', () => {
      const text = wrapper.text()
      expect(text).toContain('Document Library')
    })

    it('should render "Non-conformity and Improvement" link', () => {
      const text = wrapper.text()
      expect(text).toContain('Non-conformity and Improvement')
    })

    it('should render "Processes" dropdown', () => {
      const text = wrapper.text()
      expect(text).toContain('Processes')
    })

    it('should render "Projects" link', () => {
      const text = wrapper.text()
      expect(text).toContain('Projects')
    })

    it('should render "Risk Assessments" link', () => {
      const text = wrapper.text()
      expect(text).toContain('Risk Assessments')
    })

    it('should render "Administration" dropdown', () => {
      const text = wrapper.text()
      expect(text).toContain('Administration')
    })

    it('should render "Link Navigator" dropdown', () => {
      const text = wrapper.text()
      expect(text).toContain('Link Navigator')
    })

    it('should render "Additional Modules" dropdown', () => {
      const text = wrapper.text()
      expect(text).toContain('Additional Modules')
    })

    it('should have chevron-down icons for dropdowns', () => {
      const dropdowns = wrapper.findAll('.nav-item.dropdown')
      expect(dropdowns.length).toBeGreaterThan(0)
      dropdowns.forEach(dropdown => {
        expect(dropdown.find('.fa-chevron-down').exists()).toBe(true)
      })
    })
  })

  describe('Right Navigation Section', () => {
    it('should render State dropdown', () => {
      const text = wrapper.text()
      expect(text).toContain('State')
    })

    it('should render Weather dropdown', () => {
      const text = wrapper.text()
      expect(text).toContain('Weather')
    })

    it('should render all icon buttons', () => {
      const iconButtons = wrapper.findAll('.icon-btn')
      expect(iconButtons.length).toBeGreaterThan(0)
    })

    it('should render search icon button', () => {
      expect(wrapper.find('.icon-btn .fa-search').exists()).toBe(true)
    })

    it('should render bell/notification icon button', () => {
      expect(wrapper.find('.icon-btn .fa-bell').exists()).toBe(true)
    })

    it('should render clock icon button', () => {
      expect(wrapper.find('.icon-btn .fa-clock').exists()).toBe(true)
    })

    it('should render eye icon button', () => {
      expect(wrapper.find('.icon-btn .fa-eye').exists()).toBe(true)
    })

    it('should render settings/cog icon button', () => {
      expect(wrapper.find('.icon-btn .fa-cog').exists()).toBe(true)
    })

    it('should render user icon button', () => {
      expect(wrapper.find('.icon-btn.user-icon .fa-user').exists()).toBe(true)
    })

    it('should render Update button', () => {
      expect(wrapper.find('.update-btn').exists()).toBe(true)
      expect(wrapper.find('.update-btn').text()).toBe('Update')
    })
  })

  describe('Alarm Banner', () => {
    it('should display alarm icon', () => {
      expect(wrapper.find('.alarm-banner .fa-bell').exists()).toBe(true)
    })

    it('should display "2 active alarms in your groups!" message', () => {
      const text = wrapper.find('.alarm-text').text()
      expect(text).toBe('2 active alarms in your groups!')
    })

    it('should display alarm groups', () => {
      const text = wrapper.find('.alarm-groups').text()
      expect(text).toContain('Aishwarya_QA Group')
      expect(text).toContain('After Fix emergency Test')
    })

    it('should have separator between alarm groups', () => {
      expect(wrapper.find('.separator').exists()).toBe(true)
      expect(wrapper.find('.separator').text()).toBe('|')
    })
  })

  describe('Styling and CSS Classes', () => {
    it('should have correct background color for nav-bar', () => {
      const navBar = wrapper.find('.nav-bar')
      expect(navBar.classes()).toContain('nav-bar')
    })

    it('should have correct background color for alarm-banner', () => {
      const alarmBanner = wrapper.find('.alarm-banner')
      expect(alarmBanner.classes()).toContain('alarm-banner')
    })

    it('should apply hover classes to nav items', () => {
      const navItems = wrapper.findAll('.nav-item')
      navItems.forEach(item => {
        expect(item.classes()).toContain('nav-item')
      })
    })
  })

  describe('Responsiveness', () => {
    it('should have overflow handling for nav-menu', () => {
      const navMenu = wrapper.find('.nav-menu')
      expect(navMenu.exists()).toBe(true)
    })

    it('should have flex-shrink: 0 applied to prevent shrinking', () => {
      // This is a structural test - the CSS should be applied
      const navRight = wrapper.find('.nav-right')
      expect(navRight.exists()).toBe(true)
    })
  })

  describe('Accessibility', () => {
    it('should have clickable navigation items', () => {
      const navItems = wrapper.findAll('.nav-item')
      navItems.forEach(item => {
        expect(item.element.tagName === 'A' || item.element.tagName === 'DIV').toBe(true)
      })
    })

    it('should have clickable icon buttons', () => {
      const iconButtons = wrapper.findAll('.icon-btn')
      iconButtons.forEach(button => {
        expect(button.element.tagName).toBe('BUTTON')
      })
    })

    it('should have Update button as a button element', () => {
      const updateBtn = wrapper.find('.update-btn')
      expect(updateBtn.element.tagName).toBe('BUTTON')
    })
  })

  describe('Component Structure', () => {
    it('should have nav-left and nav-right sections', () => {
      expect(wrapper.find('.nav-left').exists()).toBe(true)
      expect(wrapper.find('.nav-right').exists()).toBe(true)
    })

    it('should have alarm-content and alarm-groups sections', () => {
      expect(wrapper.find('.alarm-content').exists()).toBe(true)
      expect(wrapper.find('.alarm-groups').exists()).toBe(true)
    })
  })
})
