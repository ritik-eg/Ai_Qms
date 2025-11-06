-- Insert seed data for emergency groups
INSERT INTO emergency_groups (type, name, number, notification_type, responsible, date_revised, notes, email_topic, email_content, sms_text, created_by, member_count, deleted)
VALUES
    ('Fire Emergency', 'Fire Response Team Alpha', 'FIRE-001', 'ALARM', 'John Smith', '2025-01-01', 'Primary fire response unit', 'Fire Emergency Alert', 'Fire emergency detected. Please respond immediately.', 'FIRE ALERT: Respond immediately', 'system', 25, false),
    ('Medical Emergency', 'Medical Response Unit 1', 'MED-001', 'DISPATCH', 'Dr. Sarah Johnson', '2025-01-01', 'Emergency medical response', 'Medical Emergency', 'Medical emergency requiring immediate attention.', 'MEDICAL EMERGENCY: Immediate response needed', 'system', 15, false),
    ('Security Alert', 'Security Team Bravo', 'SEC-001', 'ALARM', 'Mike Davis', '2025-01-01', 'Security breach response', 'Security Alert', 'Security breach detected. Security team please respond.', 'SECURITY ALERT: Breach detected', 'system', 10, false),
    ('Natural Disaster', 'Disaster Response Team', 'DIS-001', 'DISPATCH', 'Lisa Chen', '2025-01-01', 'Natural disaster coordination', 'Natural Disaster Alert', 'Natural disaster warning. Evacuation may be required.', 'DISASTER ALERT: Follow evacuation procedures', 'system', 30, false),
    ('Testing Emergency', 'Test Group 1', 'TEST-001', 'ALARM', 'Test User', '2025-01-01', 'For testing purposes only', 'Test Alert', 'This is a test alert.', 'TEST: This is a test', 'system', 5, false);

-- Note: Active alarms and history will be created through the application
