# Test Documentation - Emergency Management System

## Overview
This document provides comprehensive information about the test suite for the Emergency Management System Vue.js application.

## Testing Framework
- **Test Runner**: Vitest 1.3.1
- **Component Testing**: Vue Test Utils 2.4.4
- **DOM Environment**: Happy DOM 13.3.8

## Test Coverage

### 1. Header Component Tests
**File**: `src/components/__tests__/Header.spec.js`

#### Test Categories:
- **Component Rendering** (3 tests)
  - Verifies header, navigation bar, and alarm banner render correctly

- **Logo Section** (2 tests)
  - Validates logo circle and icon rendering

- **Navigation Menu** (9 tests)
  - Tests all navigation items (Document Library, Non-conformity, Processes, etc.)
  - Validates dropdown chevron icons

- **Right Navigation Section** (9 tests)
  - Tests State and Weather dropdowns
  - Validates all icon buttons (search, bell, clock, eye, settings, user)
  - Tests Update button

- **Alarm Banner** (4 tests)
  - Validates alarm icon and message display
  - Tests alarm groups display with separator

- **Styling and CSS Classes** (3 tests)
  - Verifies correct CSS classes applied

- **Responsiveness** (2 tests)
  - Tests overflow handling and flex-shrink properties

- **Accessibility** (3 tests)
  - Validates clickable elements and proper HTML structure

- **Component Structure** (2 tests)
  - Tests proper section organization

**Total Tests**: 37

---

### 2. Sidebar Component Tests
**File**: `src/components/__tests__/Sidebar.spec.js`

#### Test Categories:
- **Component Rendering** (2 tests)
  - Validates sidebar and section rendering

- **EMERGENCY Section** (6 tests)
  - Tests section title and all navigation links
  - Validates icons (bell, users, history)

- **ADMINISTRATOR Section** (5 tests)
  - Tests section title and navigation links
  - Validates icons (info-circle, layer-group)

- **Navigation Links** (3 tests)
  - Tests link count and active states
  - Validates href attributes

- **Icons** (2 tests)
  - Tests all Font Awesome icons

- **Styling** (3 tests)
  - Validates CSS classes

- **Structure** (2 tests)
  - Tests proper hierarchy

- **Accessibility** (3 tests)
  - Tests anchor tags and readable text

- **Responsive Design** (2 tests)
  - Tests fixed width and scrolling

- **Interactive Elements** (2 tests)
  - Tests clickable links and active states

**Total Tests**: 30

---

### 3. EmergencyGroupForm Component Tests
**File**: `src/components/__tests__/EmergencyGroupForm.spec.js`

#### Test Categories:
- **Component Rendering** (4 tests)
  - Validates form container, header, body, and footer

- **Form Header** (4 tests)
  - Tests title, instruction text, and activate alarm button

- **Basic Information Section** (5 tests)
  - Tests collapsible section functionality
  - Validates chevron icon toggling

- **Form Fields** (8 tests)
  - Tests all input fields (Type, Name, Number, Responsible, Date, Notes)
  - Validates required indicators

- **Notification Type Buttons** (5 tests)
  - Tests Alarm and Dispatch buttons
  - Validates active state toggling

- **Alarm Status Tags** (5 tests)
  - Tests tag rendering and removal functionality

- **Message Information Section** (4 tests)
  - Tests email topic, email content, and SMS text fields
  - Validates edit icons

- **Documents Section** (10 tests)
  - Tests document view icons
  - Validates "Connect from library" section
  - Tests document list and icons
  - Validates menu icons

- **Form Footer Actions** (7 tests)
  - Tests all footer buttons (Back, More, Cancel, Save, Save and close)

- **Data Reactivity** (3 tests)
  - Tests two-way data binding

- **Form Layout** (2 tests)
  - Validates two-column layout

- **Styling and CSS Classes** (2 tests)
  - Tests CSS class application

- **Methods** (7 tests)
  - Tests toggleSection, removeStatus, and getDocIcon methods

- **Accessibility** (3 tests)
  - Tests label-input associations and form structure

**Total Tests**: 69

---

### 4. App Component Integration Tests
**File**: `src/__tests__/App.spec.js`

#### Test Categories:
- **Component Integration** (4 tests)
  - Validates all child components render

- **Layout Structure** (4 tests)
  - Tests main container and content wrapper

- **Component Hierarchy** (1 test)
  - Validates proper nesting

- **Header Integration** (3 tests)
  - Tests alarm banner and navigation integration

- **Sidebar Integration** (4 tests)
  - Tests navigation sections and active links

- **EmergencyGroupForm Integration** (7 tests)
  - Tests form sections and footer actions

- **Full Application Flow** (4 tests)
  - Tests complete system rendering

- **CSS and Styling** (3 tests)
  - Validates layout and background colors

- **Component Communication** (2 tests)
  - Tests component isolation

- **Data Flow** (2 tests)
  - Tests state management

- **User Interface Elements** (3 tests)
  - Tests navigation, inputs, and buttons

- **Accessibility** (3 tests)
  - Tests semantic structure and labels

- **Responsive Design** (3 tests)
  - Tests flexible layout

- **Error Handling** (2 tests)
  - Tests graceful error handling

- **Performance** (1 test)
  - Tests mount efficiency

- **Complete User Journey** (6 tests)
  - Tests full user workflows

**Total Tests**: 52

---

## Total Test Suite Statistics

| Component | Test Count |
|-----------|-----------|
| Header | 37 |
| Sidebar | 30 |
| EmergencyGroupForm | 69 |
| App (Integration) | 52 |
| **TOTAL** | **188** |

---

## Running Tests

### Run All Tests
```bash
npm test
```

### Run Tests in Watch Mode
```bash
npm test -- --watch
```

### Run Tests with UI
```bash
npm run test:ui
```

### Generate Coverage Report
```bash
npm run test:coverage
```

### Run Specific Test File
```bash
npm test Header.spec.js
```

### Run Tests for Specific Component
```bash
npm test -- Sidebar
```

---

## Test Coverage Goals

| Category | Target Coverage |
|----------|----------------|
| Statements | 80%+ |
| Branches | 75%+ |
| Functions | 80%+ |
| Lines | 80%+ |

---

## Test Structure

Each test file follows this structure:

```javascript
import { describe, it, expect, beforeEach } from 'vitest'
import { mount } from '@vue/test-utils'
import ComponentName from '../ComponentName.vue'

describe('ComponentName', () => {
  let wrapper

  beforeEach(() => {
    wrapper = mount(ComponentName)
  })

  describe('Feature Category', () => {
    it('should test specific behavior', () => {
      // Arrange
      // Act
      // Assert
      expect(something).toBe(expected)
    })
  })
})
```

---

## Testing Best Practices

### 1. Component Testing
- Test component rendering
- Test user interactions
- Test data reactivity
- Test props and events
- Test computed properties and methods

### 2. Integration Testing
- Test component communication
- Test data flow between components
- Test full user workflows
- Test application state management

### 3. Accessibility Testing
- Test proper HTML structure
- Test ARIA attributes
- Test keyboard navigation
- Test focus management

### 4. Responsive Testing
- Test layout at different viewport sizes
- Test overflow behavior
- Test mobile-specific features

---

## Common Test Patterns

### Testing Component Rendering
```javascript
it('should render the component', () => {
  expect(wrapper.find('.component-class').exists()).toBe(true)
})
```

### Testing User Interactions
```javascript
it('should handle button click', async () => {
  const button = wrapper.find('.button')
  await button.trigger('click')
  await wrapper.vm.$nextTick()

  expect(wrapper.vm.someState).toBe(expectedValue)
})
```

### Testing Data Binding
```javascript
it('should update data on input change', async () => {
  const input = wrapper.find('input')
  await input.setValue('new value')
  await wrapper.vm.$nextTick()

  expect(wrapper.vm.formData.field).toBe('new value')
})
```

### Testing Props
```javascript
it('should receive and display props', () => {
  const wrapper = mount(Component, {
    props: { message: 'Hello' }
  })

  expect(wrapper.text()).toContain('Hello')
})
```

---

## Mocking Strategies

### Mocking Child Components
```javascript
const wrapper = mount(ParentComponent, {
  global: {
    stubs: {
      ChildComponent: true
    }
  }
})
```

### Mocking API Calls
```javascript
vi.mock('../api', () => ({
  fetchData: vi.fn(() => Promise.resolve({ data: 'mock data' }))
}))
```

---

## Continuous Integration

Tests should be run:
- Before every commit (pre-commit hook)
- On every pull request
- Before deployment
- On scheduled intervals (nightly builds)

---

## Troubleshooting

### Common Issues

1. **Tests timing out**
   - Increase timeout in vitest.config.js
   - Check for async operations without await

2. **Component not rendering**
   - Verify all dependencies are imported
   - Check for missing global configurations

3. **False positives**
   - Use waitFor for async updates
   - Ensure proper cleanup in beforeEach/afterEach

---

## Contributing to Tests

When adding new features:
1. Write tests first (TDD approach)
2. Ensure all new code has test coverage
3. Update this documentation
4. Run full test suite before committing

---

## Contact

For questions about testing:
- Review this documentation
- Check existing test examples
- Consult Vitest documentation: https://vitest.dev
- Consult Vue Test Utils documentation: https://test-utils.vuejs.org

---

## Version History

| Version | Date | Changes |
|---------|------|---------|
| 1.0.0 | 2025-11-03 | Initial test suite with 188 tests |

---

## Future Improvements

- [ ] Add E2E tests with Playwright
- [ ] Add visual regression testing
- [ ] Add performance benchmarks
- [ ] Add API integration tests
- [ ] Increase code coverage to 90%+
- [ ] Add mutation testing
- [ ] Add contract testing for API
