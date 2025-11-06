-- Create emergency_groups table
CREATE TABLE emergency_groups (
    id BIGSERIAL PRIMARY KEY,
    type VARCHAR(255),
    name VARCHAR(255) NOT NULL,
    number VARCHAR(100) UNIQUE,
    notification_type VARCHAR(50),
    responsible VARCHAR(255),
    date_revised DATE,
    notes TEXT,
    email_topic VARCHAR(255),
    email_content TEXT,
    sms_text TEXT,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_by VARCHAR(255),
    member_count INTEGER DEFAULT 0,
    last_notification_date TIMESTAMP,
    deleted BOOLEAN DEFAULT FALSE
);

-- Create index for better query performance
CREATE INDEX idx_emergency_groups_deleted ON emergency_groups(deleted);
CREATE INDEX idx_emergency_groups_type ON emergency_groups(type);
CREATE INDEX idx_emergency_groups_number ON emergency_groups(number);
