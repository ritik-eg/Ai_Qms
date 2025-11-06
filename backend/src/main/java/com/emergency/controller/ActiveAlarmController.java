package com.emergency.controller;

import com.emergency.dto.ActiveAlarmDTO;
import com.emergency.dto.request.ActivateAlarmRequest;
import com.emergency.dto.request.DeactivateAlarmRequest;
import com.emergency.dto.response.ApiResponse;
import com.emergency.service.ActiveAlarmService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Active Alarms", description = "Active alarm management APIs")
@CrossOrigin(origins = "*")
public class ActiveAlarmController {

    private final ActiveAlarmService service;

    @GetMapping("/active-alarms")
    @Operation(summary = "Get all active alarms", description = "Retrieve all active alarms with pagination and optional filtering")
    public ResponseEntity<ApiResponse<Page<ActiveAlarmDTO>>> getAllActiveAlarms(
            @Parameter(description = "Page number (0-indexed)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Filter by emergency group ID") @RequestParam(required = false) Long groupId,
            @Parameter(description = "Filter by status (ACTIVE/DEACTIVATED)") @RequestParam(required = false) String status) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "activatedDate"));
        Page<ActiveAlarmDTO> alarms = service.getAllActiveAlarms(pageable, groupId, status);

        return ResponseEntity.ok(ApiResponse.success(alarms));
    }

    @GetMapping("/active-alarms/{id}")
    @Operation(summary = "Get active alarm by ID", description = "Retrieve a specific active alarm by its ID")
    public ResponseEntity<ApiResponse<ActiveAlarmDTO>> getAlarmById(
            @Parameter(description = "Active alarm ID") @PathVariable Long id) {

        ActiveAlarmDTO alarm = service.getAlarmById(id);
        return ResponseEntity.ok(ApiResponse.success(alarm));
    }

    @PostMapping("/emergency-groups/{groupId}/activate-alarm")
    @Operation(summary = "Activate alarm", description = "Activate an alarm for a specific emergency group")
    public ResponseEntity<ApiResponse<ActiveAlarmDTO>> activateAlarm(
            @Parameter(description = "Emergency group ID") @PathVariable Long groupId,
            @Valid @RequestBody ActivateAlarmRequest request) {

        ActiveAlarmDTO activatedAlarm = service.activateAlarm(groupId, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Alarm activated successfully", activatedAlarm));
    }

    @PostMapping("/active-alarms/{id}/deactivate")
    @Operation(summary = "Deactivate alarm", description = "Deactivate an active alarm")
    public ResponseEntity<ApiResponse<ActiveAlarmDTO>> deactivateAlarm(
            @Parameter(description = "Active alarm ID") @PathVariable Long id,
            @Valid @RequestBody DeactivateAlarmRequest request) {

        ActiveAlarmDTO deactivatedAlarm = service.deactivateAlarm(id, request);
        return ResponseEntity.ok(ApiResponse.success("Alarm deactivated successfully", deactivatedAlarm));
    }

    @GetMapping("/active-alarms/count")
    @Operation(summary = "Get active alarm count", description = "Get the count of currently active alarms")
    public ResponseEntity<ApiResponse<Map<String, Long>>> getActiveAlarmCount() {
        Long count = service.getActiveAlarmCount();
        return ResponseEntity.ok(ApiResponse.success(Map.of("count", count)));
    }
}
