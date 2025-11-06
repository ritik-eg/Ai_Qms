package com.emergency.service;

import com.emergency.dto.EmergencyGroupDTO;
import com.emergency.dto.request.CreateEmergencyGroupRequest;
import com.emergency.dto.request.UpdateEmergencyGroupRequest;
import com.emergency.entity.EmergencyGroup;
import com.emergency.exception.DuplicateResourceException;
import com.emergency.exception.ResourceNotFoundException;
import com.emergency.mapper.EmergencyMapper;
import com.emergency.repository.EmergencyGroupRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class EmergencyGroupService {

    private final EmergencyGroupRepository repository;
    private final EmergencyMapper mapper;

    @Transactional(readOnly = true)
    public Page<EmergencyGroupDTO> getAllGroups(Pageable pageable, String type, String search) {
        log.debug("Fetching emergency groups with type: {}, search: {}", type, search);

        Page<EmergencyGroup> groups;

        if (search != null && !search.isBlank()) {
            groups = repository.searchGroups(search, pageable);
        } else if (type != null && !type.isBlank()) {
            groups = repository.findByType(type, pageable);
        } else {
            groups = repository.findByDeletedFalse(pageable);
        }

        return groups.map(mapper::toDTO);
    }

    @Transactional(readOnly = true)
    public EmergencyGroupDTO getGroupById(Long id) {
        log.debug("Fetching emergency group with id: {}", id);
        EmergencyGroup group = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emergency group not found with id: " + id));
        return mapper.toDTO(group);
    }

    @Transactional
    public EmergencyGroupDTO createGroup(CreateEmergencyGroupRequest request) {
        log.info("Creating new emergency group: {}", request.getName());

        // Check for duplicate number
        if (request.getNumber() != null && repository.existsByNumberAndDeletedFalse(request.getNumber())) {
            throw new DuplicateResourceException("Emergency group with number " + request.getNumber() + " already exists");
        }

        EmergencyGroup group = EmergencyGroup.builder()
                .type(request.getType())
                .name(request.getName())
                .number(request.getNumber())
                .notificationType(request.getNotificationType())
                .responsible(request.getResponsible())
                .dateRevised(request.getDateRevised())
                .notes(request.getNotes())
                .emailTopic(request.getEmailTopic())
                .emailContent(request.getEmailContent())
                .smsText(request.getSmsText())
                .memberCount(request.getMemberCount() != null ? request.getMemberCount() : 0)
                .createdBy(request.getCreatedBy())
                .deleted(false)
                .build();

        EmergencyGroup savedGroup = repository.save(group);
        log.info("Emergency group created successfully with id: {}", savedGroup.getId());

        return mapper.toDTO(savedGroup);
    }

    @Transactional
    public EmergencyGroupDTO updateGroup(Long id, UpdateEmergencyGroupRequest request) {
        log.info("Updating emergency group with id: {}", id);

        EmergencyGroup group = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emergency group not found with id: " + id));

        // Check for duplicate number if it's being changed
        if (request.getNumber() != null && !request.getNumber().equals(group.getNumber())) {
            if (repository.existsByNumberAndDeletedFalse(request.getNumber())) {
                throw new DuplicateResourceException("Emergency group with number " + request.getNumber() + " already exists");
            }
        }

        // Update fields
        if (request.getType() != null) group.setType(request.getType());
        if (request.getName() != null) group.setName(request.getName());
        if (request.getNumber() != null) group.setNumber(request.getNumber());
        if (request.getNotificationType() != null) group.setNotificationType(request.getNotificationType());
        if (request.getResponsible() != null) group.setResponsible(request.getResponsible());
        if (request.getDateRevised() != null) group.setDateRevised(request.getDateRevised());
        if (request.getNotes() != null) group.setNotes(request.getNotes());
        if (request.getEmailTopic() != null) group.setEmailTopic(request.getEmailTopic());
        if (request.getEmailContent() != null) group.setEmailContent(request.getEmailContent());
        if (request.getSmsText() != null) group.setSmsText(request.getSmsText());
        if (request.getMemberCount() != null) group.setMemberCount(request.getMemberCount());

        EmergencyGroup updatedGroup = repository.save(group);
        log.info("Emergency group updated successfully with id: {}", updatedGroup.getId());

        return mapper.toDTO(updatedGroup);
    }

    @Transactional
    public void deleteGroup(Long id) {
        log.info("Deleting emergency group with id: {}", id);

        EmergencyGroup group = repository.findByIdAndDeletedFalse(id)
                .orElseThrow(() -> new ResourceNotFoundException("Emergency group not found with id: " + id));

        // Soft delete
        group.setDeleted(true);
        repository.save(group);

        log.info("Emergency group deleted successfully with id: {}", id);
    }
}
