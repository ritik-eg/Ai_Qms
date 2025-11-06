-- Create active_alarms table
CREATE TABLE active_alarms (
    id BIGSERIAL PRIMARY KEY,
    emergency_group_id BIGINT NOT NULL,
    activated_date TIMESTAMP NOT NULL,
    activated_by VARCHAR(255) NOT NULL,
    alarm_deactivated TIMESTAMP,
    deactivated_by VARCHAR(255),
    status VARCHAR(50) NOT NULL,
    statistics_acknowledged INTEGER DEFAULT 0,
    statistics_total INTEGER DEFAULT 0,
    CONSTRAINT fk_emergency_group
        FOREIGN KEY (emergency_group_id)
        REFERENCES emergency_groups(id)
        ON DELETE CASCADE
);

-- Create alarm_methods table for storing methods array
CREATE TABLE alarm_methods (
    alarm_id BIGINT NOT NULL,
    method VARCHAR(50) NOT NULL,
    CONSTRAINT fk_alarm
        FOREIGN KEY (alarm_id)
        REFERENCES active_alarms(id)
        ON DELETE CASCADE
);

-- Create indexes for better query performance
CREATE INDEX idx_active_alarms_group_id ON active_alarms(emergency_group_id);
CREATE INDEX idx_active_alarms_status ON active_alarms(status);
CREATE INDEX idx_active_alarms_activated_date ON active_alarms(activated_date);
