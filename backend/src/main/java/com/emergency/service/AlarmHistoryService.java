package com.emergency.service;

import com.emergency.dto.AlarmHistoryDTO;
import com.emergency.entity.AlarmHistory;
import com.emergency.mapper.EmergencyMapper;
import com.emergency.repository.AlarmHistoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class AlarmHistoryService {

    private final AlarmHistoryRepository repository;
    private final EmergencyMapper mapper;

    @Transactional(readOnly = true)
    public Page<AlarmHistoryDTO> getAlarmHistory(Long groupId, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        log.debug("Fetching alarm history with filters - groupId: {}, startDate: {}, endDate: {}", groupId, startDate, endDate);

        Page<AlarmHistory> history = repository.findWithFilters(groupId, startDate, endDate, pageable);
        return history.map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public List<AlarmHistoryDTO> getHistoryByGroupId(Long groupId) {
        log.debug("Fetching alarm history for emergency group id: {}", groupId);

        List<AlarmHistory> history = repository.findByEmergencyGroupId(groupId);
        return history.stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }
}
