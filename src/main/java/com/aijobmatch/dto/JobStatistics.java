package com.aijobmatch.dto;

public class JobStatistics {
    private int recommendedJobsCount;
    private int applicationsCount;
    private int interviewsCount;
    private double successRate;
    
    public JobStatistics() {
    }
    
    public JobStatistics(int recommendedJobsCount, int applicationsCount, int interviewsCount, double successRate) {
        this.recommendedJobsCount = recommendedJobsCount;
        this.applicationsCount = applicationsCount;
        this.interviewsCount = interviewsCount;
        this.successRate = successRate;
    }
    
    public int getRecommendedJobsCount() {
        return recommendedJobsCount;
    }
    
    public void setRecommendedJobsCount(int recommendedJobsCount) {
        this.recommendedJobsCount = recommendedJobsCount;
    }
    
    public int getApplicationsCount() {
        return applicationsCount;
    }
    
    public void setApplicationsCount(int applicationsCount) {
        this.applicationsCount = applicationsCount;
    }
    
    public int getInterviewsCount() {
        return interviewsCount;
    }
    
    public void setInterviewsCount(int interviewsCount) {
        this.interviewsCount = interviewsCount;
    }
    
    public double getSuccessRate() {
        return successRate;
    }
    
    public void setSuccessRate(double successRate) {
        this.successRate = successRate;
    }
}
