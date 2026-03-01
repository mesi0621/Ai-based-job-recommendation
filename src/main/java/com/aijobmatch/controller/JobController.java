package com.aijobmatch.controller;

import com.aijobmatch.dto.SkillGapReport;
import com.aijobmatch.model.Job;
import com.aijobmatch.model.JobStatus;
import com.aijobmatch.repository.JobRepository;
import com.aijobmatch.service.JobMatchingService;
import com.aijobmatch.service.SkillGapAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/jobs")
public class JobController {
    
    private final JobRepository jobRepository;
    private final JobMatchingService jobMatchingService;
    private final SkillGapAnalysisService skillGapAnalysisService;
    
    public JobController(JobRepository jobRepository, JobMatchingService jobMatchingService, 
                        SkillGapAnalysisService skillGapAnalysisService) {
        this.jobRepository = jobRepository;
        this.jobMatchingService = jobMatchingService;
        this.skillGapAnalysisService = skillGapAnalysisService;
    }
    
    @GetMapping
    public ResponseEntity<List<Job>> searchJobs(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String location) {
        
        if (query != null && !query.isEmpty()) {
            return ResponseEntity.ok(jobRepository.searchByQuery(query, JobStatus.ACTIVE));
        } else if (location != null && !location.isEmpty()) {
            return ResponseEntity.ok(jobRepository.findByStatusAndLocation(JobStatus.ACTIVE, location));
        } else {
            return ResponseEntity.ok(jobRepository.findByStatus(JobStatus.ACTIVE));
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Job> getJobDetails(@PathVariable Long id) {
        Job job = jobRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        return ResponseEntity.ok(job);
    }
    
    @GetMapping("/{id}/skill-gap")
    public ResponseEntity<SkillGapReport> getSkillGapAnalysis(
            @PathVariable Long id,
            Authentication authentication) {
        
        Long userId = getUserIdFromAuth(authentication);
        SkillGapReport report = skillGapAnalysisService.analyzeSkillGap(userId, id);
        return ResponseEntity.ok(report);
    }
    
    private Long getUserIdFromAuth(Authentication authentication) {
        return 1L; // Mock implementation
    }
}
