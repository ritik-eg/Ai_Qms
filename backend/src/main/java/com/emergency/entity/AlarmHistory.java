package com.emergency.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "alarm_history")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "alarm_id")
    private ActiveAlarm alarm;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emergency_group_id")
    private EmergencyGroup emergencyGroup;

    @Column(name = "action")
    private String action;

    @Column(name = "action_date")
    private LocalDateTime actionDate;

    @Column(name = "action_by")
    private String actionBy;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;
}
