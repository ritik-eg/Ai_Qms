package com.emergency.service;

import com.emergency.dto.DashboardStatsDTO;
import com.emergency.repository.ActiveAlarmRepository;
import com.emergency.repository.EmergencyGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class StatisticsService {

    private final EmergencyGroupRepository groupRepository;
    private final ActiveAlarmRepository alarmRepository;

    @Transactional(readOnly = true)
    public DashboardStatsDTO getDashboardStats() {
        log.debug("Fetching dashboard statistics");

        long totalGroups = groupRepository.countActiveGroups();
        long activeAlarms = alarmRepository.countActiveAlarms();
        long totalActivations = alarmRepository.countTotalActivations();

        return DashboardStatsDTO.builder()
                .totalGroups(totalGroups)
                .activeAlarms(activeAlarms)
                .totalActivations(totalActivations)
                .totalAcknowledgements(0L) // Can be calculated based on business logic
                .build();
    }
}
