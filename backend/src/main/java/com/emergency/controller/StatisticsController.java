package com.emergency.controller;

import com.emergency.dto.DashboardStatsDTO;
import com.emergency.dto.response.ApiResponse;
import com.emergency.service.StatisticsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
@Tag(name = "Statistics", description = "Statistics and dashboard APIs")
@CrossOrigin(origins = "*")
public class StatisticsController {

    private final StatisticsService service;

    @GetMapping("/dashboard")
    @Operation(summary = "Get dashboard statistics", description = "Retrieve overall dashboard statistics")
    public ResponseEntity<ApiResponse<DashboardStatsDTO>> getDashboardStats() {
        DashboardStatsDTO stats = service.getDashboardStats();
        return ResponseEntity.ok(ApiResponse.success(stats));
    }
}
