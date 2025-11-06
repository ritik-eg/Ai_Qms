# Alarm Activation Flow - Testing Guide

## 🎯 Objective
Test the complete alarm activation flow from Emergency Groups list to Active Alarms.

## 📋 Step-by-Step Testing

### Step 1: Start Application
```bash
npm run dev
```
Open: `http://localhost:5173`

---

### Step 2: Navigate to Emergency Groups
1. Click **"Emergency groups"** in the left sidebar
2. **Expected**: Should see table with 14 groups
3. **Expected**: Each row has a search icon (magnifying glass) in the "Options" column

---

### Step 3: Click on Any Group
1. Find any group (e.g., "Dev emergency group" - number 0047)
2. Click the **search icon** (magnifying glass) in the "Options" column
3. **Expected**: Navigate to activation page `/emergency-groups/0047/activate`

---

### Step 4: Verify Activation Page
**Should see:**
- ✓ Group name as title (e.g., "Dev emergency group")
- ✓ Instruction text: "Click on button below to activate alarm..."
- ✓ Red button: "Go to activate alarm" with bell icon
- ✓ Basic information section (collapsed/expanded)
  - Type: testing emergency
  - Name: Dev emergency group
  - Number: 0047
  - Notification type: Alarm (blue badge)
  - Responsible: Sujeena Shetty
  - Notes: Please respond
- ✓ Message information section (right side)
  - E-mail topic (empty or filled)
  - E-mail content (empty or filled)
  - SMS text (empty or filled)
- ✓ Documents section (if any)
- ✓ Footer buttons: "Back to old design", "More", "Cancel", "Close form"

---

### Step 5: Activate the Alarm
1. Click the **"Go to activate alarm"** button (red button)
2. **Expected**: Confirmation dialog appears
   - Message: "Are you sure you want to activate this alarm?"
   - Buttons: OK / Cancel

---

### Step 6: Confirm Activation
1. Click **"OK"** in the confirmation dialog
2. **Expected**: Alert appears with message: **"Alarm activated"** ✓
3. Click **"OK"** on the alert

---

### Step 7: Verify Navigation
1. **Expected**: Automatically navigate to `/active-alarms`
2. **Expected**: Active Alarms page loads

---

### Step 8: Verify New Alarm
**Should see new alarm in the table with:**
- ✓ ID: Unique number (timestamp-based)
- ✓ Group: "Dev emergency group" (or whichever group you activated)
- ✓ Activated date: Current date and time
- ✓ Activated by: "Current User"
- ✓ Methods: Email and SMS icons visible
- ✓ Deactivated: Empty
- ✓ Deactivated by: Empty
- ✓ Statistics: 1, 0, 0 (or similar)

---

## 🔍 Detailed Verification Checklist

### ✅ Emergency Groups List Page
- [ ] Search icon visible in Options column
- [ ] Clicking search icon navigates to activate page
- [ ] URL changes to `/emergency-groups/:id/activate`

### ✅ Activate Alarm Page
- [ ] Group name displays correctly
- [ ] "Go to activate alarm" button is prominent and red
- [ ] Bell icon shows in button
- [ ] Basic information displays group details
- [ ] All fields are read-only (not editable)
- [ ] Notification type shows as badge
- [ ] Message information displays
- [ ] Footer buttons work

### ✅ Activation Process
- [ ] Click "Go to activate alarm" shows confirmation
- [ ] Confirmation message: "Are you sure..."
- [ ] Clicking OK shows "Alarm activated" alert
- [ ] Clicking Cancel closes dialog without action
- [ ] Alert message is exactly: "Alarm activated"

### ✅ Post-Activation
- [ ] Navigate to Active Alarms automatically
- [ ] New alarm appears at top of list
- [ ] All alarm details populated correctly
- [ ] Red left border on alarm row
- [ ] Method icons display
- [ ] Statistics initialized

---

## 🐛 Troubleshooting

### Issue: Search icon not working
**Solution**: Verify router is imported and configured in main.js

### Issue: Activation page not loading
**Solution**: Check route definition in `src/router/index.js`
```javascript
{
  path: '/emergency-groups/:id/activate',
  name: 'ActivateAlarm',
  component: ActivateAlarm
}
```

### Issue: Alert not showing
**Solution**: Check ActivateAlarm.vue `activateAlarm` method:
```javascript
alert('Alarm activated')  // Should be present
```

### Issue: Not navigating to Active Alarms
**Solution**: Check navigation code:
```javascript
this.$router.push({
  name: 'ActiveAlarms',
  params: { newAlarm: JSON.stringify(newAlarm) }
})
```

---

## 📊 Test Results Template

### Test Run Date: _______________
### Tester Name: _______________

| Step | Status | Notes |
|------|--------|-------|
| Navigate to Emergency Groups | ⬜ Pass ⬜ Fail | |
| Click search icon on group | ⬜ Pass ⬜ Fail | |
| Activation page loads | ⬜ Pass ⬜ Fail | |
| Group details display | ⬜ Pass ⬜ Fail | |
| "Go to activate alarm" button works | ⬜ Pass ⬜ Fail | |
| Confirmation dialog appears | ⬜ Pass ⬜ Fail | |
| "Alarm activated" alert shows | ⬜ Pass ⬜ Fail | |
| Navigate to Active Alarms | ⬜ Pass ⬜ Fail | |
| New alarm appears in list | ⬜ Pass ⬜ Fail | |

**Overall Result**: ⬜ Pass ⬜ Fail

**Issues Found**:
-
-
-

---

## 🎬 Quick Test Scenario

**Scenario**: Activate "Dev emergency group" alarm

```
1. Open app → http://localhost:5173
2. Click "Emergency groups" (sidebar)
3. Find "Dev emergency group" (0047)
4. Click search icon
5. Click "Go to activate alarm"
6. Click "OK" on confirmation
7. Click "OK" on "Alarm activated" alert
8. Verify alarm in Active Alarms list
```

**Expected Time**: ~30 seconds

**Success Criteria**:
✓ No errors in console
✓ "Alarm activated" alert appears
✓ New alarm visible in Active Alarms
✓ All fields populated correctly

---

## 📝 Code References

### EmergencyGroupsList.vue
```javascript
viewGroup(group) {
  // Line that navigates to activation page
  this.$router.push(`/emergency-groups/${group.number}/activate`)
}
```

### ActivateAlarm.vue
```javascript
activateAlarm() {
  if (confirm('Are you sure you want to activate this alarm?')) {
    // Creates alarm object
    const newAlarm = { ... }

    // Shows alert
    alert('Alarm activated')  // ← This is the key alert

    // Navigates to Active Alarms
    this.$router.push({
      name: 'ActiveAlarms',
      params: { newAlarm: JSON.stringify(newAlarm) }
    })
  }
}
```

### Router Configuration
```javascript
{
  path: '/emergency-groups/:id/activate',
  name: 'ActivateAlarm',
  component: ActivateAlarm
}
```

---

## ✅ Confirmation

### Feature: Click group → Open activation page ✓
**Status**: ✅ **IMPLEMENTED**
- Click search icon on any group in Emergency Groups list
- Opens activation page with group details
- Shows "Go to activate alarm" button

### Feature: Show "Alarm activated" alert ✓
**Status**: ✅ **IMPLEMENTED**
- Click "Go to activate alarm" button
- Confirmation dialog appears
- After confirmation, shows alert: "Alarm activated"
- Navigates to Active Alarms with new alarm

---

## 🚀 Ready for Testing!

All features are implemented and ready to test. Follow the steps above to verify the complete activation flow.
