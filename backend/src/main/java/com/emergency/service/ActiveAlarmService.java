package com.emergency.service;

import com.emergency.dto.ActiveAlarmDTO;
import com.emergency.dto.request.ActivateAlarmRequest;
import com.emergency.dto.request.DeactivateAlarmRequest;
import com.emergency.entity.ActiveAlarm;
import com.emergency.entity.AlarmHistory;
import com.emergency.entity.EmergencyGroup;
import com.emergency.exception.AlreadyActiveException;
import com.emergency.exception.ResourceNotFoundException;
import com.emergency.mapper.EmergencyMapper;
import com.emergency.repository.ActiveAlarmRepository;
import com.emergency.repository.AlarmHistoryRepository;
import com.emergency.repository.EmergencyGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ActiveAlarmService {

    private final ActiveAlarmRepository alarmRepository;
    private final EmergencyGroupRepository groupRepository;
    private final AlarmHistoryRepository historyRepository;
    private final EmergencyMapper mapper;

    @Transactional(readOnly = true)
    public Page<ActiveAlarmDTO> getAllActiveAlarms(Pageable pageable, Long groupId, String status) {
        log.debug("Fetching active alarms with groupId: {}, status: {}", groupId, status);

        Page<ActiveAlarm> alarms;

        if (groupId != null) {
            alarms = alarmRepository.findByEmergencyGroupId(groupId, pageable);
        } else if ("ACTIVE".equalsIgnoreCase(status)) {
            alarms = alarmRepository.findAllActiveAlarms(pageable);
        } else {
            alarms = alarmRepository.findAll(pageable);
        }

        return alarms.map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public ActiveAlarmDTO getAlarmById(Long id) {
        log.debug("Fetching active alarm with id: {}", id);
        ActiveAlarm alarm = alarmRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Active alarm not found with id: " + id));
        return mapper.toDTO(alarm);
    }

    @Transactional
    public ActiveAlarmDTO activateAlarm(Long groupId, ActivateAlarmRequest request) {
        log.info("Activating alarm for emergency group id: {}", groupId);

        // Check if group exists
        EmergencyGroup group = groupRepository.findByIdAndDeletedFalse(groupId)
                .orElseThrow(() -> new ResourceNotFoundException("Emergency group not found with id: " + groupId));

        // Check if there's already an active alarm for this group
        Optional<ActiveAlarm> existingAlarm = alarmRepository.findActiveAlarmByGroupId(groupId);
        if (existingAlarm.isPresent()) {
            throw new AlreadyActiveException("An active alarm already exists for this emergency group");
        }

        // Create active alarm
        LocalDateTime now = LocalDateTime.now();
        ActiveAlarm alarm = ActiveAlarm.builder()
                .emergencyGroup(group)
                .activatedDate(now)
                .activatedBy(request.getActivatedBy())
                .methods(request.getMethods())
                .status("ACTIVE")
                .statisticsAcknowledged(0)
                .statisticsTotal(group.getMemberCount() != null ? group.getMemberCount() : 0)
                .build();

        ActiveAlarm savedAlarm = alarmRepository.save(alarm);

        // Update group's last notification date
        group.setLastNotificationDate(now);
        groupRepository.save(group);

        // Create history entry
        AlarmHistory history = AlarmHistory.builder()
                .alarm(savedAlarm)
                .emergencyGroup(group)
                .action("ACTIVATED")
                .actionDate(now)
                .actionBy(request.getActivatedBy())
                .notes("Alarm activated with methods: " + String.join(", ", request.getMethods()))
                .build();
        historyRepository.save(history);

        log.info("Alarm activated successfully with id: {}", savedAlarm.getId());

        return mapper.toDTO(savedAlarm);
    }

    @Transactional
    public ActiveAlarmDTO deactivateAlarm(Long alarmId, DeactivateAlarmRequest request) {
        log.info("Deactivating alarm with id: {}", alarmId);

        ActiveAlarm alarm = alarmRepository.findById(alarmId)
                .orElseThrow(() -> new ResourceNotFoundException("Active alarm not found with id: " + alarmId));

        if (!"ACTIVE".equals(alarm.getStatus())) {
            throw new IllegalStateException("Alarm is already deactivated");
        }

        // Deactivate alarm
        LocalDateTime now = LocalDateTime.now();
        alarm.setAlarmDeactivated(now);
        alarm.setDeactivatedBy(request.getDeactivatedBy());
        alarm.setStatus("DEACTIVATED");

        ActiveAlarm updatedAlarm = alarmRepository.save(alarm);

        // Create history entry
        AlarmHistory history = AlarmHistory.builder()
                .alarm(alarm)
                .emergencyGroup(alarm.getEmergencyGroup())
                .action("DEACTIVATED")
                .actionDate(now)
                .actionBy(request.getDeactivatedBy())
                .notes(request.getNotes())
                .build();
        historyRepository.save(history);

        log.info("Alarm deactivated successfully with id: {}", alarmId);

        return mapper.toDTO(updatedAlarm);
    }

    @Transactional(readOnly = true)
    public Long getActiveAlarmCount() {
        return alarmRepository.countActiveAlarms();
    }
}
