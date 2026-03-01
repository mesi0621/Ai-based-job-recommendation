package com.aijobmatch.service;

import com.aijobmatch.dto.JobRecommendation;
import com.aijobmatch.dto.SkillGapReport;
import com.aijobmatch.model.*;
import com.aijobmatch.repository.JobRepository;
import com.aijobmatch.repository.ResumeRepository;
import com.aijobmatch.repository.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class JobMatchingService {
    
    private final UserRepository userRepository;
    private final JobRepository jobRepository;
    private final ResumeRepository resumeRepository;
    private final SkillGapAnalysisService skillGapAnalysisService;
    private final AIServiceClient aiServiceClient;
    
    public JobMatchingService(UserRepository userRepository, JobRepository jobRepository, ResumeRepository resumeRepository, SkillGapAnalysisService skillGapAnalysisService, AIServiceClient aiServiceClient) {
        this.userRepository = userRepository;
        this.jobRepository = jobRepository;
        this.resumeRepository = resumeRepository;
        this.skillGapAnalysisService = skillGapAnalysisService;
        this.aiServiceClient = aiServiceClient;
    }
    
    private static final double SKILLS_WEIGHT = 0.5;
    private static final double EXPERIENCE_WEIGHT = 0.3;
    private static final double LOCATION_WEIGHT = 0.2;
    private static final double MATCH_THRESHOLD = 30.0;
    
    @Transactional(readOnly = true)
    @Cacheable(value = "jobRecommendations", key = "#userId + '_' + #limit")
    public List<JobRecommendation> getRecommendations(Long userId, int limit) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Resume resume = resumeRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Resume not found for user"));
        
        // Get all active jobs
        List<Job> activeJobs = jobRepository.findByStatus(JobStatus.ACTIVE);
        
        // Calculate match scores for all jobs
        List<JobRecommendation> recommendations = new ArrayList<>();
        for (Job job : activeJobs) {
            double matchScore = calculateMatchScore(user, resume, job);
            
            // Filter jobs below threshold
            if (matchScore >= MATCH_THRESHOLD) {
                JobRecommendation recommendation = new JobRecommendation();
                recommendation.setJob(job);
                recommendation.setMatchScore(matchScore);
                recommendation.setAiConfidenceScore(aiServiceClient.getAIConfidenceScore(userId, job.getId()));
                recommendation.setSkillGapReport(skillGapAnalysisService.analyzeSkillGap(userId, job.getId()));
                
                // Calculate match benchmarks
                Map<String, Double> benchmarks = new HashMap<>();
                benchmarks.put("skills", calculateSkillsMatch(resume, job));
                benchmarks.put("experience", calculateExperienceMatch(resume, job));
                benchmarks.put("location", calculateLocationMatch(user, job));
                recommendation.setMatchBenchmarks(benchmarks);
                
                recommendations.add(recommendation);
            }
        }
        
        // Sort by match score descending
        recommendations.sort((a, b) -> Double.compare(b.getMatchScore(), a.getMatchScore()));
        
        // Return top N recommendations
        return recommendations.stream()
                .limit(limit)
                .collect(Collectors.toList());
    }
    
    @Transactional(readOnly = true)
    public double calculateMatchScore(Long userId, Long jobId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        Resume resume = resumeRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Resume not found for user"));
        
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        
        return calculateMatchScore(user, resume, job);
    }
    
    private double calculateMatchScore(User user, Resume resume, Job job) {
        double skillsMatch = calculateSkillsMatch(resume, job);
        double experienceMatch = calculateExperienceMatch(resume, job);
        double locationMatch = calculateLocationMatch(user, job);
        
        return (skillsMatch * SKILLS_WEIGHT) + 
               (experienceMatch * EXPERIENCE_WEIGHT) + 
               (locationMatch * LOCATION_WEIGHT);
    }
    
    private double calculateSkillsMatch(Resume resume, Job job) {
        if (job.getRequiredSkills().isEmpty()) {
            return 100.0;
        }
        
        Set<String> userSkills = resume.getSkills().stream()
                .map(es -> es.getSkill().getName().toLowerCase())
                .collect(Collectors.toSet());
        
        Set<String> requiredSkills = job.getRequiredSkills().stream()
                .map(s -> s.getName().toLowerCase())
                .collect(Collectors.toSet());
        
        long matchedCount = requiredSkills.stream()
                .filter(userSkills::contains)
                .count();
        
        return (matchedCount * 100.0) / requiredSkills.size();
    }
    
    private double calculateExperienceMatch(Resume resume, Job job) {
        if (job.getExperienceYears() == null || job.getExperienceYears() == 0) {
            return 100.0;
        }
        
        // Calculate total years of experience from resume
        int totalYears = resume.getExperiences().stream()
                .mapToInt(exp -> {
                    if (exp.getStartDate() == null) return 0;
                    if (exp.isCurrent() || exp.getEndDate() == null) {
                        return java.time.Period.between(exp.getStartDate(), java.time.LocalDate.now()).getYears();
                    }
                    return java.time.Period.between(exp.getStartDate(), exp.getEndDate()).getYears();
                })
                .sum();
        
        double ratio = (double) totalYears / job.getExperienceYears();
        return Math.min(ratio, 1.0) * 100.0;
    }
    
    private double calculateLocationMatch(User user, Job job) {
        // TODO: Implement actual location matching logic
        // For now, return 100 if locations match, 0 otherwise
        // This would need user location preference data
        return 50.0; // Default neutral score
    }
    
    public void recalculateRecommendations(Long userId) {
        // Cache eviction would happen here
        // For now, this is a placeholder
    }
}
