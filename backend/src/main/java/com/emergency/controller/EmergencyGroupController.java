package com.emergency.controller;

import com.emergency.dto.EmergencyGroupDTO;
import com.emergency.dto.request.CreateEmergencyGroupRequest;
import com.emergency.dto.request.UpdateEmergencyGroupRequest;
import com.emergency.dto.response.ApiResponse;
import com.emergency.service.EmergencyGroupService;
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

@RestController
@RequestMapping("/api/v1/emergency-groups")
@RequiredArgsConstructor
@Tag(name = "Emergency Groups", description = "Emergency group management APIs")
@CrossOrigin(origins = "*")
public class EmergencyGroupController {

    private final EmergencyGroupService service;

    @GetMapping
    @Operation(summary = "Get all emergency groups", description = "Retrieve all emergency groups with pagination and optional filtering")
    public ResponseEntity<ApiResponse<Page<EmergencyGroupDTO>>> getAllGroups(
            @Parameter(description = "Page number (0-indexed)") @RequestParam(defaultValue = "0") int page,
            @Parameter(description = "Page size") @RequestParam(defaultValue = "10") int size,
            @Parameter(description = "Filter by type") @RequestParam(required = false) String type,
            @Parameter(description = "Search by name, number, or type") @RequestParam(required = false) String search) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<EmergencyGroupDTO> groups = service.getAllGroups(pageable, type, search);

        return ResponseEntity.ok(ApiResponse.success(groups));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get emergency group by ID", description = "Retrieve a specific emergency group by its ID")
    public ResponseEntity<ApiResponse<EmergencyGroupDTO>> getGroupById(
            @Parameter(description = "Emergency group ID") @PathVariable Long id) {

        EmergencyGroupDTO group = service.getGroupById(id);
        return ResponseEntity.ok(ApiResponse.success(group));
    }

    @PostMapping
    @Operation(summary = "Create emergency group", description = "Create a new emergency group")
    public ResponseEntity<ApiResponse<EmergencyGroupDTO>> createGroup(
            @Valid @RequestBody CreateEmergencyGroupRequest request) {

        EmergencyGroupDTO createdGroup = service.createGroup(request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Emergency group created successfully", createdGroup));
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update emergency group", description = "Update an existing emergency group")
    public ResponseEntity<ApiResponse<EmergencyGroupDTO>> updateGroup(
            @Parameter(description = "Emergency group ID") @PathVariable Long id,
            @Valid @RequestBody UpdateEmergencyGroupRequest request) {

        EmergencyGroupDTO updatedGroup = service.updateGroup(id, request);
        return ResponseEntity.ok(ApiResponse.success("Emergency group updated successfully", updatedGroup));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Delete emergency group", description = "Soft delete an emergency group")
    public ResponseEntity<ApiResponse<Void>> deleteGroup(
            @Parameter(description = "Emergency group ID") @PathVariable Long id) {

        service.deleteGroup(id);
        return ResponseEntity.noContent().build();
    }
}
