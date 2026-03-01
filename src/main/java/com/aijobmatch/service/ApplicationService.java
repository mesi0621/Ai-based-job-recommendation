package com.aijobmatch.service;

import com.aijobmatch.model.Application;
import com.aijobmatch.model.ApplicationStatus;
import com.aijobmatch.model.Job;
import com.aijobmatch.model.User;
import com.aijobmatch.repository.ApplicationRepository;
import com.aijobmatch.repository.JobRepository;
import com.aijobmatch.repository.UserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class ApplicationService {
    
    private final ApplicationRepository applicationRepository;
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ResumeAnalysisService resumeAnalysisService;
    private final ObjectMapper objectMapper = new ObjectMapper();
    
    public ApplicationService(ApplicationRepository applicationRepository, UserRepository userRepository,
                            JobRepository jobRepository, ResumeAnalysisService resumeAnalysisService) {
        this.applicationRepository = applicationRepository;
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.resumeAnalysisService = resumeAnalysisService;
    }
    
    @Transactional
    public Application submitApplication(Long userId, Long jobId) {
        // Check for duplicate application
        if (applicationRepository.existsByUserIdAndJobId(userId, jobId)) {
            throw new RuntimeException("Application already exists for this job");
        }
        
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        
        // Get resume snapshot
        String resumeSnapshot;
        try {
            resumeSnapshot = objectMapper.writeValueAsString(
                resumeAnalysisService.getResumeData(userId)
            );
        } catch (Exception e) {
            throw new RuntimeException("Failed to create resume snapshot", e);
        }
        
        // Create application
        Application application = new Application();
        application.setUser(user);
        application.setJob(job);
        application.setStatus(ApplicationStatus.RESUME);
        application.setResumeSnapshot(resumeSnapshot);
        application.setAppliedAt(LocalDateTime.now());
        application.setStatusUpdatedAt(LocalDateTime.now());
        
        return applicationRepository.save(application);
    }
    
    @Transactional
    public Application updateApplicationStatus(Long applicationId, ApplicationStatus newStatus) {
        Application application = applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
        
        // Validate status transition
        if (!isValidStatusTransition(application.getStatus(), newStatus)) {
            throw new RuntimeException(
                String.format("Invalid status transition from %s to %s", 
                    application.getStatus(), newStatus)
            );
        }
        
        application.setStatus(newStatus);
        application.setStatusUpdatedAt(LocalDateTime.now());
        
        return applicationRepository.save(application);
    }
    
    private boolean isValidStatusTransition(ApplicationStatus current, ApplicationStatus next) {
        switch (current) {
            case RESUME:
                return next == ApplicationStatus.INTERVIEW_SCHEDULED ||
                       next == ApplicationStatus.SHORTLISTED ||
                       next == ApplicationStatus.REJECTED;
            case INTERVIEW_SCHEDULED:
            case SHORTLISTED:
                return next == ApplicationStatus.REJECTED;
            case REJECTED:
                return false; // No transitions from REJECTED
            default:
                return false;
        }
    }
    
    @Transactional(readOnly = true)
    public List<Application> getUserApplications(Long userId) {
        return applicationRepository.findByUserId(userId);
    }
    
    @Transactional(readOnly = true)
    public Application getApplicationDetails(Long applicationId) {
        return applicationRepository.findById(applicationId)
                .orElseThrow(() -> new RuntimeException("Application not found"));
    }
    
    @Transactional(readOnly = true)
    public boolean hasApplied(Long userId, Long jobId) {
        return applicationRepository.existsByUserIdAndJobId(userId, jobId);
    }
}
