package com.emergency.mapper;

import com.emergency.dto.ActiveAlarmDTO;
import com.emergency.dto.AlarmHistoryDTO;
import com.emergency.dto.EmergencyGroupDTO;
import com.emergency.entity.ActiveAlarm;
import com.emergency.entity.AlarmHistory;
import com.emergency.entity.EmergencyGroup;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EmergencyMapper {

    EmergencyGroupDTO toDTO(EmergencyGroup entity);

    EmergencyGroup toEntity(EmergencyGroupDTO dto);

    void updateEntityFromDTO(EmergencyGroupDTO dto, @MappingTarget EmergencyGroup entity);

    @Mapping(source = "emergencyGroup.id", target = "emergencyGroupId")
    @Mapping(source = "emergencyGroup.name", target = "emergencyGroupName")
    ActiveAlarmDTO toDTO(ActiveAlarm entity);

    @Mapping(source = "alarm.id", target = "alarmId")
    @Mapping(source = "emergencyGroup.id", target = "emergencyGroupId")
    @Mapping(source = "emergencyGroup.name", target = "emergencyGroupName")
    AlarmHistoryDTO toDTO(AlarmHistory entity);
}
