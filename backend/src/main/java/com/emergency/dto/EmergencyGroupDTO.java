package com.emergency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class EmergencyGroupDTO {
    private Long id;
    private String type;
    private String name;
    private String number;
    private String notificationType;
    private String responsible;
    private LocalDate dateRevised;
    private String notes;
    private String emailTopic;
    private String emailContent;
    private String smsText;
    private Integer memberCount;
    private LocalDateTime lastNotificationDate;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
}
