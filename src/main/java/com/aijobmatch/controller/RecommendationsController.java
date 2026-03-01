package com.aijobmatch.controller;

import com.aijobmatch.dto.JobRecommendation;
import com.aijobmatch.service.JobMatchingService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/recommendations")
public class RecommendationsController {
    
    private final JobMatchingService jobMatchingService;
    
    public RecommendationsController(JobMatchingService jobMatchingService) {
        this.jobMatchingService = jobMatchingService;
    }
    
    @GetMapping
    public ResponseEntity<List<JobRecommendation>> getRecommendations(
            @RequestParam(defaultValue = "10") int limit,
            Authentication authentication) {
        
        Long userId = getUserIdFromAuth(authentication);
        List<JobRecommendation> recommendations = jobMatchingService.getRecommendations(userId, limit);
        return ResponseEntity.ok(recommendations);
    }
    
    private Long getUserIdFromAuth(Authentication authentication) {
        return 1L; // Mock implementation
    }
}
