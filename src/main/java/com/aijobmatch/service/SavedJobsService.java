package com.aijobmatch.service;

import com.aijobmatch.model.Job;
import com.aijobmatch.model.User;
import com.aijobmatch.repository.JobRepository;
import com.aijobmatch.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class SavedJobsService {
    
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    
    public SavedJobsService(UserRepository userRepository, JobRepository jobRepository) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
    }
    
    @Transactional
    public void saveJob(Long userId, Long jobId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        
        // Idempotent operation - add only if not already saved
        if (!user.getSavedJobs().contains(job)) {
            user.getSavedJobs().add(job);
            userRepository.save(user);
        }
    }
    
    @Transactional
    public void unsaveJob(Long userId, Long jobId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        
        user.getSavedJobs().remove(job);
        userRepository.save(user);
    }
    
    @Transactional(readOnly = true)
    public List<Job> getSavedJobs(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return new ArrayList<>(user.getSavedJobs());
    }
    
    @Transactional(readOnly = true)
    public boolean isJobSaved(Long userId, Long jobId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        return user.getSavedJobs().stream()
                .anyMatch(job -> job.getId().equals(jobId));
    }
}
