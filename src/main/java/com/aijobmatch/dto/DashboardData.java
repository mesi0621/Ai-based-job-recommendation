package com.aijobmatch.dto;

import java.util.ArrayList;
import java.util.List;

public class DashboardData {
    private JobStatistics statistics;
    private List<JobRecommendation> topRecommendations = new ArrayList<>();
    
    public DashboardData() {
    }
    
    public DashboardData(JobStatistics statistics, List<JobRecommendation> topRecommendations) {
        this.statistics = statistics;
        this.topRecommendations = topRecommendations;
    }
    
    public JobStatistics getStatistics() {
        return statistics;
    }
    
    public void setStatistics(JobStatistics statistics) {
        this.statistics = statistics;
    }
    
    public List<JobRecommendation> getTopRecommendations() {
        return topRecommendations;
    }
    
    public void setTopRecommendations(List<JobRecommendation> topRecommendations) {
        this.topRecommendations = topRecommendations;
    }
}
