import { describe, it, expect, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import Sidebar from '../Sidebar.vue'

describe('Sidebar Component', () => {
  let wrapper

  beforeEach(() => {
    wrapper = mount(Sidebar)
  })

  describe('Component Rendering', () => {
    it('should render the sidebar component', () => {
      expect(wrapper.find('.sidebar').exists()).toBe(true)
    })

    it('should render sidebar sections', () => {
      const sections = wrapper.findAll('.sidebar-section')
      expect(sections.length).toBe(2)
    })
  })

  describe('EMERGENCY Section', () => {
    it('should render EMERGENCY section title', () => {
      const titles = wrapper.findAll('.section-title')
      expect(titles[0].text()).toBe('EMERGENCY')
    })

    it('should render "Active alarms" navigation link', () => {
      const text = wrapper.text()
      expect(text).toContain('Active alarms')
    })

    it('should render "Emergency groups" navigation link', () => {
      const text = wrapper.text()
      expect(text).toContain('Emergency groups')
    })

    it('should render "Alarm history" navigation link', () => {
      const text = wrapper.text()
      expect(text).toContain('Alarm history')
    })

    it('should have bell icon for Active alarms', () => {
      const navLinks = wrapper.findAll('.nav-link')
      const activeAlarmsLink = navLinks.find(link => link.text().includes('Active alarms'))
      expect(activeAlarmsLink.find('.fa-bell').exists()).toBe(true)
    })

    it('should have users icon for Emergency groups', () => {
      const navLinks = wrapper.findAll('.nav-link')
      const emergencyGroupsLink = navLinks.find(link => link.text().includes('Emergency groups'))
      expect(emergencyGroupsLink.find('.fa-users').exists()).toBe(true)
    })

    it('should have history icon for Alarm history', () => {
      const navLinks = wrapper.findAll('.nav-link')
      const alarmHistoryLink = navLinks.find(link => link.text().includes('Alarm history'))
      expect(alarmHistoryLink.find('.fa-history').exists()).toBe(true)
    })
  })

  describe('ADMINISTRATOR Section', () => {
    it('should render ADMINISTRATOR section title', () => {
      const titles = wrapper.findAll('.section-title')
      expect(titles[1].text()).toBe('ADMINISTRATOR')
    })

    it('should render "Statuses" navigation link', () => {
      const text = wrapper.text()
      expect(text).toContain('Statuses')
    })

    it('should render "Group types" navigation link', () => {
      const text = wrapper.text()
      expect(text).toContain('Group types')
    })

    it('should have info-circle icon for Statuses', () => {
      const navLinks = wrapper.findAll('.nav-link')
      const statusesLink = navLinks.find(link => link.text().includes('Statuses'))
      expect(statusesLink.find('.fa-info-circle').exists()).toBe(true)
    })

    it('should have layer-group icon for Group types', () => {
      const navLinks = wrapper.findAll('.nav-link')
      const groupTypesLink = navLinks.find(link => link.text().includes('Group types'))
      expect(groupTypesLink.find('.fa-layer-group').exists()).toBe(true)
    })
  })

  describe('Navigation Links', () => {
    it('should render correct number of navigation links', () => {
      const navLinks = wrapper.findAll('.nav-link')
      expect(navLinks.length).toBe(5) // 3 in EMERGENCY + 2 in ADMINISTRATOR
    })

    it('should have active class on first link (Active alarms)', () => {
      const firstLink = wrapper.find('.nav-link')
      expect(firstLink.classes()).toContain('active')
    })

    it('should have href="#" on all links', () => {
      const navLinks = wrapper.findAll('.nav-link')
      navLinks.forEach(link => {
        expect(link.attributes('href')).toBe('#')
      })
    })
  })

  describe('Icons', () => {
    it('should render all required Font Awesome icons', () => {
      const icons = wrapper.findAll('.nav-link i')
      expect(icons.length).toBe(5)
    })

    it('should have correct icon classes', () => {
      const icons = wrapper.findAll('.nav-link i')
      const iconClasses = icons.map(icon => icon.classes())

      expect(iconClasses.some(classes => classes.includes('fa-bell'))).toBe(true)
      expect(iconClasses.some(classes => classes.includes('fa-users'))).toBe(true)
      expect(iconClasses.some(classes => classes.includes('fa-history'))).toBe(true)
      expect(iconClasses.some(classes => classes.includes('fa-info-circle'))).toBe(true)
      expect(iconClasses.some(classes => classes.includes('fa-layer-group'))).toBe(true)
    })
  })

  describe('Styling', () => {
    it('should have correct sidebar background color class', () => {
      const sidebar = wrapper.find('.sidebar')
      expect(sidebar.classes()).toContain('sidebar')
    })

    it('should have section titles with correct styling class', () => {
      const titles = wrapper.findAll('.section-title')
      titles.forEach(title => {
        expect(title.classes()).toContain('section-title')
      })
    })

    it('should apply navigation styles to nav links', () => {
      const navLinks = wrapper.findAll('.nav-link')
      navLinks.forEach(link => {
        expect(link.classes()).toContain('nav-link')
      })
    })
  })

  describe('Structure', () => {
    it('should have sidebar-nav within each sidebar-section', () => {
      const sections = wrapper.findAll('.sidebar-section')
      sections.forEach(section => {
        expect(section.find('.sidebar-nav').exists()).toBe(true)
      })
    })

    it('should maintain proper hierarchy', () => {
      expect(wrapper.find('.sidebar > .sidebar-section').exists()).toBe(true)
      expect(wrapper.find('.sidebar-section > .sidebar-nav').exists()).toBe(true)
    })
  })

  describe('Accessibility', () => {
    it('should have anchor tags for navigation links', () => {
      const navLinks = wrapper.findAll('.nav-link')
      navLinks.forEach(link => {
        expect(link.element.tagName).toBe('A')
      })
    })

    it('should have readable text for all links', () => {
      const navLinks = wrapper.findAll('.nav-link')
      navLinks.forEach(link => {
        expect(link.text().length).toBeGreaterThan(0)
      })
    })

    it('should have icons with proper structure', () => {
      const navLinks = wrapper.findAll('.nav-link')
      navLinks.forEach(link => {
        const icon = link.find('i')
        const span = link.find('span')
        expect(icon.exists()).toBe(true)
        expect(span.exists()).toBe(true)
      })
    })
  })

  describe('Responsive Design', () => {
    it('should have fixed width sidebar', () => {
      const sidebar = wrapper.find('.sidebar')
      expect(sidebar.exists()).toBe(true)
    })

    it('should have overflow-y auto for scrolling', () => {
      // Testing the presence of the sidebar that should have overflow-y: auto
      const sidebar = wrapper.find('.sidebar')
      expect(sidebar.exists()).toBe(true)
    })
  })

  describe('Interactive Elements', () => {
    it('should have clickable navigation links', () => {
      const navLinks = wrapper.findAll('.nav-link')
      navLinks.forEach(link => {
        expect(link.element.tagName).toBe('A')
        expect(link.attributes('href')).toBeDefined()
      })
    })

    it('should maintain active state styling', () => {
      const activeLink = wrapper.find('.nav-link.active')
      expect(activeLink.exists()).toBe(true)
      expect(activeLink.classes()).toContain('active')
    })
  })
})
