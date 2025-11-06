-- Create alarm_history table
CREATE TABLE alarm_history (
    id BIGSERIAL PRIMARY KEY,
    alarm_id BIGINT,
    emergency_group_id BIGINT,
    action VARCHAR(50),
    action_date TIMESTAMP,
    action_by VARCHAR(255),
    notes TEXT,
    CONSTRAINT fk_alarm_history_alarm
        FOREIGN KEY (alarm_id)
        REFERENCES active_alarms(id)
        ON DELETE SET NULL,
    CONSTRAINT fk_alarm_history_group
        FOREIGN KEY (emergency_group_id)
        REFERENCES emergency_groups(id)
        ON DELETE CASCADE
);

-- Create indexes for better query performance
CREATE INDEX idx_alarm_history_alarm_id ON alarm_history(alarm_id);
CREATE INDEX idx_alarm_history_group_id ON alarm_history(emergency_group_id);
CREATE INDEX idx_alarm_history_action_date ON alarm_history(action_date);
