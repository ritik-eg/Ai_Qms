package com.emergency.controller;

import com.emergency.dto.AlarmHistoryDTO;
import com.emergency.dto.response.ApiResponse;
import com.emergency.service.AlarmHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Tag(name = "Alarm History", description = "Alarm history APIs")
@CrossOrigin(origins = "*")
public class AlarmHistoryController {

    private final AlarmHistoryService service;

    @GetMapping("/alarm-history")
    @Operation(summary = "Get alarm history", description = "Retrieve alarm history with optional filtering")
    public ResponseEntity<ApiResponse<Page<AlarmHistoryDTO>>> getAlarmHistory(
            @Parameter(description = "Page number (0-indexed)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Filter by emergency group ID") @RequestParam(required = false) Long groupId,
            @Parameter(description = "Filter by start date") @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @Parameter(description = "Filter by end date") @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "actionDate"));
        Page<AlarmHistoryDTO> history = service.getAlarmHistory(groupId, startDate, endDate, pageable);

        return ResponseEntity.ok(ApiResponse.success(history));
    }

    @GetMapping("/emergency-groups/{groupId}/alarm-history")
    @Operation(summary = "Get alarm history for group", description = "Retrieve alarm history for a specific emergency group")
    public ResponseEntity<ApiResponse<List<AlarmHistoryDTO>>> getHistoryByGroupId(
            @Parameter(description = "Emergency group ID") @PathVariable Long groupId) {

        List<AlarmHistoryDTO> history = service.getHistoryByGroupId(groupId);
        return ResponseEntity.ok(ApiResponse.success(history));
    }
}
