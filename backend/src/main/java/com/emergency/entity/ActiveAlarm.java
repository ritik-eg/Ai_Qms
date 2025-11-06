package com.emergency.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "active_alarms")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActiveAlarm {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emergency_group_id", nullable = false)
    private EmergencyGroup emergencyGroup;

    @Column(name = "activated_date", nullable = false)
    private LocalDateTime activatedDate;

    @Column(name = "activated_by", nullable = false)
    private String activatedBy;

    @ElementCollection
    @CollectionTable(name = "alarm_methods", joinColumns = @JoinColumn(name = "alarm_id"))
    @Column(name = "method")
    private List<String> methods;

    @Column(name = "alarm_deactivated")
    private LocalDateTime alarmDeactivated;

    @Column(name = "deactivated_by")
    private String deactivatedBy;

    @Column(name = "status")
    private String status;

    @Column(name = "statistics_acknowledged")
    private Integer statisticsAcknowledged = 0;

    @Column(name = "statistics_total")
    private Integer statisticsTotal = 0;
}
