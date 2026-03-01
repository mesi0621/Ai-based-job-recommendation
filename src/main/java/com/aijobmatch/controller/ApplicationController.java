package com.aijobmatch.controller;

import com.aijobmatch.model.Application;
import com.aijobmatch.model.ApplicationStatus;
import com.aijobmatch.service.ApplicationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/applications")
public class ApplicationController {
    
    private final ApplicationService applicationService;
    
    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }
    
    @PostMapping
    public ResponseEntity<Application> submitApplication(
            @RequestParam Long jobId,
            Authentication authentication) {
        
        Long userId = getUserIdFromAuth(authentication);
        Application application = applicationService.submitApplication(userId, jobId);
        return ResponseEntity.ok(application);
    }
    
    @GetMapping
    public ResponseEntity<List<Application>> getMyApplications(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        List<Application> applications = applicationService.getUserApplications(userId);
        return ResponseEntity.ok(applications);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Application> getApplicationDetails(@PathVariable Long id) {
        Application application = applicationService.getApplicationDetails(id);
        return ResponseEntity.ok(application);
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<Application> updateApplicationStatus(
            @PathVariable Long id,
            @RequestParam ApplicationStatus status) {
        
        Application application = applicationService.updateApplicationStatus(id, status);
        return ResponseEntity.ok(application);
    }
    
    private Long getUserIdFromAuth(Authentication authentication) {
        return 1L; // Mock implementation
    }
}
