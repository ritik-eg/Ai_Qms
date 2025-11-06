package com.emergency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ActiveAlarmDTO {
    private Long id;
    private Long emergencyGroupId;
    private String emergencyGroupName;
    private LocalDateTime activatedDate;
    private String activatedBy;
    private List<String> methods;
    private LocalDateTime alarmDeactivated;
    private String deactivatedBy;
    private String status;
    private Integer statisticsAcknowledged;
    private Integer statisticsTotal;
}
