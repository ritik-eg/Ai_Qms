# Emergency Management System - Vue.js Frontend

A comprehensive emergency management system built with Vue.js 3 and Vite, featuring alarm notifications, emergency group management, and document handling.

## Features

- **Top Navigation Bar** with multiple menu items and icons
- **Active Alarms Banner** showing real-time alarm status
- **Sidebar Navigation** with Emergency and Administrator sections
- **Emergency Group Form** with:
  - Basic information fields
  - Notification type selection (Alarm/Dispatch)
  - Alarm status management
  - Date and notes fields
- **Message Information Panel** for email and SMS content
- **Documents Section** for managing emergency-related documents
- **Form Actions** including Save, Save and Close, and Cancel

## Project Structure

```
Ai_Qms/
├── src/
│   ├── components/
│   │   ├── Header.vue              # Top navigation and alarm banner
│   │   ├── Sidebar.vue             # Left sidebar navigation
│   │   └── EmergencyGroupForm.vue  # Main form with message info and documents
│   ├── App.vue                     # Main application component
│   ├── main.js                     # Application entry point
│   └── style.css                   # Global styles
├── index.html                      # HTML template
├── package.json                    # Project dependencies
├── vite.config.js                  # Vite configuration
└── README.md                       # This file
```

## Installation

1. Install dependencies:
```bash
npm install
```

## Running the Application

### Development Mode
```bash
npm run dev
```
The application will start on `http://localhost:5173`

### Build for Production
```bash
npm run build
```

### Preview Production Build
```bash
npm run preview
```

## Technologies Used

- **Vue.js 3** - Progressive JavaScript framework
- **Vite** - Next generation frontend tooling
- **Font Awesome 6** - Icon library
- **CSS3** - Modern styling

## Components Overview

### Header Component
- Top navigation bar with menu items
- Alarm notification banner
- User actions and settings icons

### Sidebar Component
- Emergency section (Active alarms, Emergency groups, Alarm history)
- Administrator section (Statuses, Group types)

### EmergencyGroupForm Component
- Collapsible Basic Information section
- Form fields: Type, Name, Number, Notification Type, Responsible, Date, Notes
- Alarm status tags with removal functionality
- Message Information panel (Email topic, Email content, SMS text)
- Documents section with file listing

## Customization

### Styling
- Global styles are in [src/style.css](src/style.css)
- Component-specific styles are scoped within each `.vue` file

### Data
- Form data and messages can be modified in the `data()` function of [EmergencyGroupForm.vue](src/components/EmergencyGroupForm.vue)

## Browser Support

- Chrome (latest)
- Firefox (latest)
- Safari (latest)
- Edge (latest)

## License

MIT
