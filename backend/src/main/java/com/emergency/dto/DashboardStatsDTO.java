package com.emergency.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    private long totalGroups;
    private long activeAlarms;
    private long totalActivations;
    private long totalAcknowledgements;
}
