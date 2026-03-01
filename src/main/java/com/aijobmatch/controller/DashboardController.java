package com.aijobmatch.controller;

import com.aijobmatch.dto.DashboardData;
import com.aijobmatch.service.DashboardService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
public class DashboardController {
    
    private final DashboardService dashboardService;
    
    public DashboardController(DashboardService dashboardService) {
        this.dashboardService = dashboardService;
    }
    
    @GetMapping
    public ResponseEntity<DashboardData> getDashboard(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        DashboardData data = dashboardService.getDashboardData(userId);
        return ResponseEntity.ok(data);
    }
    
    private Long getUserIdFromAuth(Authentication authentication) {
        return 1L; // Mock implementation
    }
}
