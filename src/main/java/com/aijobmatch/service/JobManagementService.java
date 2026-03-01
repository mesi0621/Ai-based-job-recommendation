package com.aijobmatch.service;

import com.aijobmatch.model.Job;
import com.aijobmatch.model.JobStatus;
import com.aijobmatch.repository.JobRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class JobManagementService {
    
    private final JobRepository jobRepository;
    
    public JobManagementService(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }
    
    @Transactional
    public Job createJob(Job job) {
        job.setStatus(JobStatus.ACTIVE);
        job.setCreatedAt(LocalDateTime.now());
        job.setUpdatedAt(LocalDateTime.now());
        return jobRepository.save(job);
    }
    
    @Transactional
    public Job updateJob(Long jobId, Job updatedJob) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        
        job.setTitle(updatedJob.getTitle());
        job.setCompany(updatedJob.getCompany());
        job.setLocation(updatedJob.getLocation());
        job.setDescription(updatedJob.getDescription());
        job.setRequiredSkills(updatedJob.getRequiredSkills());
        job.setExperienceYears(updatedJob.getExperienceYears());
        job.setSalaryRange(updatedJob.getSalaryRange());
        job.setUpdatedAt(LocalDateTime.now());
        
        return jobRepository.save(job);
    }
    
    @Transactional
    public void deleteJob(Long jobId) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        
        job.setStatus(JobStatus.DELETED);
        job.setUpdatedAt(LocalDateTime.now());
        jobRepository.save(job);
    }
    
    @Transactional(readOnly = true)
    public Job getJobDetails(Long jobId) {
        return jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
    }
    
    @Transactional(readOnly = true)
    public List<Job> getAdminJobList(JobStatus status) {
        if (status != null) {
            return jobRepository.findByStatus(status);
        }
        return jobRepository.findAll();
    }
    
    @Transactional
    public Job changeJobStatus(Long jobId, JobStatus newStatus) {
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        
        job.setStatus(newStatus);
        job.setUpdatedAt(LocalDateTime.now());
        
        return jobRepository.save(job);
    }
}
