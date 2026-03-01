package com.aijobmatch.service;

import com.aijobmatch.dto.DashboardData;
import com.aijobmatch.dto.JobRecommendation;
import com.aijobmatch.dto.JobStatistics;
import com.aijobmatch.model.ApplicationStatus;
import com.aijobmatch.repository.ApplicationRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DashboardService {
    
    private final ApplicationRepository applicationRepository;
    private final JobMatchingService jobMatchingService;
    
    public DashboardService(ApplicationRepository applicationRepository, JobMatchingService jobMatchingService) {
        this.applicationRepository = applicationRepository;
        this.jobMatchingService = jobMatchingService;
    }
    
    @Transactional(readOnly = true)
    public DashboardData getDashboardData(Long userId) {
        DashboardData data = new DashboardData();
        data.setStatistics(getJobStatistics(userId));
        data.setTopRecommendations(getTopRecommendations(userId, 5));
        return data;
    }
    
    @Transactional(readOnly = true)
    public JobStatistics getJobStatistics(Long userId) {
        JobStatistics stats = new JobStatistics();
        
        // Get recommendations count
        List<JobRecommendation> recommendations = jobMatchingService.getRecommendations(userId, Integer.MAX_VALUE);
        stats.setRecommendedJobsCount(recommendations.size());
        
        // Get applications count
        long totalApplications = applicationRepository.countByUserId(userId);
        stats.setApplicationsCount((int) totalApplications);
        
        // Get interviews count
        long interviewsCount = applicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.INTERVIEW_SCHEDULED);
        stats.setInterviewsCount((int) interviewsCount);
        
        // Calculate success rate
        if (totalApplications > 0) {
            // Assuming "Shortlisted" is a success indicator
            long shortlistedCount = applicationRepository.countByUserIdAndStatus(userId, ApplicationStatus.SHORTLISTED);
            double successRate = (shortlistedCount * 100.0) / totalApplications;
            stats.setSuccessRate(Math.round(successRate * 100.0) / 100.0);
        } else {
            stats.setSuccessRate(0.0);
        }
        
        return stats;
    }
    
    @Transactional(readOnly = true)
    public List<JobRecommendation> getTopRecommendations(Long userId, int limit) {
        return jobMatchingService.getRecommendations(userId, limit);
    }
}
