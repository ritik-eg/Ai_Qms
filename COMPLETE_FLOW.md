# Complete Emergency Management System Flow

## 🎯 Overview
This document describes the complete user flow for the Emergency Management System, from viewing groups to activating alarms.

## 📋 Complete User Journey

### Flow Diagram
```
1. Active Alarms (Landing Page)
   ↓
2. Emergency Groups List
   ├→ 3a. Create New Group
   │   └→ (Save) → Back to Emergency Groups List
   │
   └→ 3b. View Group Details → Activate Alarm Page
       └→ (Click "Go to activate alarm" button)
          └→ Alert: "Alarm activated"
             └→ Active Alarms Page (shows new alarm)
```

## 🔀 Detailed Flow

### 1. **Landing Page - Active Alarms** (`/active-alarms`)
**Component**: `ActiveAlarms.vue`

#### What User Sees:
- List of currently active alarms in a table format
- Each alarm shows:
  - ID number
  - Group name
  - Activation date and time
  - Activated by (user name)
  - Methods used (Email, Phone, SMS, App icons)
  - Deactivation status
  - Statistics (Confirmed, Comments, Failed)

#### User Actions:
- Search for alarms
- View alarm details (click search icon)
- Navigate to Emergency Groups (click sidebar link)

---

### 2. **Emergency Groups List** (`/emergency-groups`)
**Component**: `EmergencyGroupsList.vue`

#### What User Sees:
- Filters section (All, Notification groups, Alarm groups)
- Search bar
- "Create new group" button
- "Export to Excel" button
- Table showing all groups:
  - Checkbox for selection
  - Search icon (Options column)
  - Number, Name, Type
  - Members count
  - Last notification date
  - Revised date
  - Color-coded left border (red/blue)
- Pagination controls

#### User Actions:
1. **Click "Create new group"** → Goes to Create New Group page
2. **Click search icon on any group** → Goes to Activate Alarm page for that group
3. **Search/filter groups** → Filters table in real-time
4. **Select groups** → Bulk selection with checkboxes

---

### 3a. **Create New Group** (`/emergency-groups/new`)
**Component**: `CreateNewGroup.vue`

#### What User Sees:
- Form with two columns:
  - **Left**: Basic Information
    - Type dropdown
    - Name field (required *)
    - Number field
    - Notification type buttons (Alarm/Dispatch)
    - Responsible person dropdown
    - Date revised picker
    - Notes textarea
    - Alarm statuses with add/remove
  - **Right**: Message Information
    - E-mail topic
    - E-mail content
    - SMS text

#### User Actions:
1. **Fill form fields** → Enter group details
2. **Click "Add Status"** → Prompt to add alarm status tag
3. **Click "Cancel"** → Confirmation dialog → Back to Emergency Groups List
4. **Click "Save"** → Validates & saves → Shows success message
5. **Click "Save and close form"** → Validates, saves, and navigates back to list

#### Validation:
- Name field is required
- Shows alert if validation fails

---

### 3b. **Activate Alarm Page** (`/emergency-groups/:id/activate`)
**Component**: `ActivateAlarm.vue`

#### What User Sees:
- Group name as title (e.g., "Dev emergency group")
- Instruction text: "Click on button below to activate alarm..."
- **Red "Go to activate alarm" button** (prominent)
- Basic Information section (read-only display):
  - Type, Name, Number
  - Notification type badge
  - Responsible person
  - Date revised
  - Notes
  - Alarm statuses (as tags)
- Message Information section (read-only):
  - E-mail topic
  - E-mail content
  - SMS text
- Documents section (if any)

#### User Actions:
1. **Click "Go to activate alarm"** button:
   - Shows confirmation dialog: "Are you sure you want to activate this alarm?"
   - If YES:
     - Shows alert: **"Alarm activated"**
     - Creates new alarm record
     - Navigates to Active Alarms page
     - New alarm appears at top of list
   - If NO:
     - Stays on current page

2. **Click "Cancel"** → Back to Emergency Groups List
3. **Click "Close form"** → Back to Emergency Groups List

---

### 4. **Back to Active Alarms** (`/active-alarms`)
**After Activation**

#### What User Sees:
- Newly activated alarm appears in the table
- Alarm details include:
  - New ID (timestamp-based)
  - Group name from activated group
  - Current date/time as activation time
  - Current user as "Activated by"
  - Selected methods (Email, SMS)
  - Empty deactivation fields
  - Statistics starting at 0

---

## 🗺️ Route Structure

```javascript
/                                    → Redirects to /active-alarms
/active-alarms                       → Active Alarms List
/emergency-groups                    → Emergency Groups List
/emergency-groups/new                → Create New Group Form
/emergency-groups/:id                → Group Detail (EmergencyGroupForm)
/emergency-groups/:id/activate       → Activate Alarm Page
```

## 🔗 Navigation Links

### Sidebar Links:
- **Active alarms** → `/active-alarms` (router-link)
- **Emergency groups** → `/emergency-groups` (router-link)
- **Alarm history** → `#` (placeholder)

### Button Navigation:
- **Create new group** → `/emergency-groups/new`
- **Search icon** (in table) → `/emergency-groups/:id/activate`
- **Go to activate alarm** → Activates alarm → `/active-alarms`
- **Cancel/Close** → `/emergency-groups`
- **Save and close** → `/emergency-groups`

## 📊 Data Flow

### Creating a Group:
```
User fills form
  ↓
Clicks "Save and close form"
  ↓
Validates (name required)
  ↓
Saves to database (mock: console.log)
  ↓
Shows success alert
  ↓
Navigates to /emergency-groups
  ↓
New group appears in list
```

### Activating an Alarm:
```
User clicks group in list
  ↓
Opens /emergency-groups/:id/activate
  ↓
User clicks "Go to activate alarm"
  ↓
Shows confirmation dialog
  ↓
User confirms
  ↓
Creates alarm object:
  {
    id: timestamp,
    group: groupName,
    activatedDate: currentDateTime,
    activatedBy: currentUser,
    methods: { email: true, sms: true, ... },
    statistics: { confirmed: 0, comments: 0, failed: 0 }
  }
  ↓
Shows "Alarm activated" alert
  ↓
Navigates to /active-alarms with alarm data
  ↓
New alarm appears in Active Alarms table
```

## 🎨 Visual Indicators

### Color Coding:
- **Red left border**: Active/priority groups (in Emergency Groups list)
- **Blue left border**: Normal groups
- **Red left border**: All active alarms (in Active Alarms list)

### Status Badges:
- **Alarm** badge: Blue background, bell icon
- **Dispatch** badge: Green background, paper plane icon

### Method Icons:
- **Email**: Envelope icon
- **Phone**: Mobile icon
- **SMS**: Comment icon
- **App**: Tablet icon

## 🔔 Alarm Activation Details

### What Happens:
1. User confirmation dialog
2. Alert message: "Alarm activated"
3. New alarm record created
4. Navigation to Active Alarms
5. Alarm visible in list immediately

### Alarm Properties:
- **ID**: Unique timestamp-based
- **Group**: Name from the emergency group
- **Activated Date**: Current date/time (locale format)
- **Activated By**: Current user (default: "Current User")
- **Methods**: Email and SMS enabled by default
- **Statistics**: All start at 0

## 🔄 State Management

### Current Implementation:
- Each component manages its own state
- Data passed via router params when needed
- No global state management (Pinia/Vuex)

### Data Persistence:
- Mock data in component `data()`
- Ready for API integration
- Console logs for save operations

## 🚀 Running the Complete Flow

```bash
# Start the application
npm run dev

# Open http://localhost:5173
# App opens at /active-alarms

# Navigate through the flow:
1. View active alarms
2. Click "Emergency groups" in sidebar
3. Click "Create new group"
4. Fill form and save
5. Click search icon on any group
6. Click "Go to activate alarm"
7. Confirm activation
8. See alarm in Active Alarms list
```

## ✅ Testing Checklist

### Emergency Groups List:
- [ ] Search filters groups correctly
- [ ] Filters work (All, Notification, Alarm)
- [ ] Create new group navigates correctly
- [ ] Clicking search icon opens activate page
- [ ] Pagination works
- [ ] Export button is visible

### Create New Group:
- [ ] All form fields accept input
- [ ] Name field shows required indicator
- [ ] Notification type buttons toggle
- [ ] Add status creates new tag
- [ ] Remove status deletes tag
- [ ] Cancel shows confirmation
- [ ] Save validates name field
- [ ] Save and close navigates back

### Activate Alarm Page:
- [ ] Group details display correctly
- [ ] Basic information is read-only
- [ ] Message information displays
- [ ] "Go to activate alarm" button is prominent
- [ ] Confirmation dialog appears on click
- [ ] Alert shows "Alarm activated"
- [ ] Navigates to Active Alarms
- [ ] Cancel/Close buttons work

### Active Alarms:
- [ ] All alarms display in table
- [ ] Search filters alarms
- [ ] Method icons display correctly
- [ ] Statistics show correctly
- [ ] Pagination works
- [ ] New alarm appears after activation

## 📝 Notes

### Future Enhancements:
1. **Backend Integration**: Connect to real API
2. **Real-time Updates**: WebSocket for live alarm updates
3. **Authentication**: User login and permissions
4. **Notifications**: Push notifications for new alarms
5. **Deactivation Flow**: Allow users to deactivate alarms
6. **Audit Trail**: Track all actions with timestamps
7. **Advanced Filters**: More filter options
8. **Bulk Actions**: Activate multiple groups at once

### Known Limitations:
1. No actual API calls (mock data)
2. No persistent storage
3. User name hardcoded ("Current User")
4. No real-time updates
5. No email/SMS sending
6. No authentication/authorization

## 🎓 Summary

This Emergency Management System provides a complete workflow for:
1. **Viewing** active alarms
2. **Managing** emergency groups (create, list, view)
3. **Activating** alarms with confirmation
4. **Tracking** alarm status and statistics

All pages are fully functional with proper navigation, validation, and user feedback. The system is ready for backend integration and can be extended with additional features as needed.
