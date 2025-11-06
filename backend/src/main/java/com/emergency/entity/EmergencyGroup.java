package com.emergency.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Table(name = "emergency_groups")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class EmergencyGroup {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "type")
    private String type;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "number", unique = true)
    private String number;

    @Column(name = "notification_type")
    private String notificationType;

    @Column(name = "responsible")
    private String responsible;

    @Column(name = "date_revised")
    private LocalDate dateRevised;

    @Column(name = "notes", columnDefinition = "TEXT")
    private String notes;

    @Column(name = "email_topic")
    private String emailTopic;

    @Column(name = "email_content", columnDefinition = "TEXT")
    private String emailContent;

    @Column(name = "sms_text", columnDefinition = "TEXT")
    private String smsText;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @LastModifiedDate
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "created_by")
    private String createdBy;

    @Column(name = "member_count")
    private Integer memberCount = 0;

    @Column(name = "last_notification_date")
    private LocalDateTime lastNotificationDate;

    @Column(name = "deleted")
    private Boolean deleted = false;
}
