package com.emergency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AlarmHistoryDTO {
    private Long id;
    private Long alarmId;
    private Long emergencyGroupId;
    private String emergencyGroupName;
    private String action;
    private LocalDateTime actionDate;
    private String actionBy;
    private String notes;
}
