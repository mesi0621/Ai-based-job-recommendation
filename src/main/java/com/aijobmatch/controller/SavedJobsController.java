package com.aijobmatch.controller;

import com.aijobmatch.model.Job;
import com.aijobmatch.service.SavedJobsService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/saved-jobs")
public class SavedJobsController {
    
    private final SavedJobsService savedJobsService;
    
    public SavedJobsController(SavedJobsService savedJobsService) {
        this.savedJobsService = savedJobsService;
    }
    
    @PostMapping("/{jobId}")
    public ResponseEntity<Void> saveJob(
            @PathVariable Long jobId,
            Authentication authentication) {
        
        Long userId = getUserIdFromAuth(authentication);
        savedJobsService.saveJob(userId, jobId);
        return ResponseEntity.ok().build();
    }
    
    @DeleteMapping("/{jobId}")
    public ResponseEntity<Void> unsaveJob(
            @PathVariable Long jobId,
            Authentication authentication) {
        
        Long userId = getUserIdFromAuth(authentication);
        savedJobsService.unsaveJob(userId, jobId);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping
    public ResponseEntity<List<Job>> getSavedJobs(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        List<Job> savedJobs = savedJobsService.getSavedJobs(userId);
        return ResponseEntity.ok(savedJobs);
    }
    
    private Long getUserIdFromAuth(Authentication authentication) {
        return 1L; // Mock implementation
    }
}
