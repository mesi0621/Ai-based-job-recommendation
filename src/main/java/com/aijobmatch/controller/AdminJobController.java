package com.aijobmatch.controller;

import com.aijobmatch.model.Job;
import com.aijobmatch.model.JobStatus;
import com.aijobmatch.service.JobManagementService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin/jobs")
public class AdminJobController {
    
    private final JobManagementService jobManagementService;
    
    public AdminJobController(JobManagementService jobManagementService) {
        this.jobManagementService = jobManagementService;
    }
    
    @PostMapping
    public ResponseEntity<Job> createJob(@RequestBody Job job) {
        Job createdJob = jobManagementService.createJob(job);
        return ResponseEntity.ok(createdJob);
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Job> updateJob(
            @PathVariable Long id,
            @RequestBody Job job) {
        
        Job updatedJob = jobManagementService.updateJob(id, job);
        return ResponseEntity.ok(updatedJob);
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteJob(@PathVariable Long id) {
        jobManagementService.deleteJob(id);
        return ResponseEntity.ok().build();
    }
    
    @GetMapping
    public ResponseEntity<List<Job>> getAllJobs(
            @RequestParam(required = false) JobStatus status) {
        
        List<Job> jobs = jobManagementService.getAdminJobList(status);
        return ResponseEntity.ok(jobs);
    }
    
    @PatchMapping("/{id}/status")
    public ResponseEntity<Job> changeJobStatus(
            @PathVariable Long id,
            @RequestParam JobStatus status) {
        
        Job job = jobManagementService.changeJobStatus(id, status);
        return ResponseEntity.ok(job);
    }
}
