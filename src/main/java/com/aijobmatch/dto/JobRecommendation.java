package com.aijobmatch.dto;

import com.aijobmatch.model.Job;

import java.util.HashMap;
import java.util.Map;

public class JobRecommendation {
    private Job job;
    private double matchScore;
    private double aiConfidenceScore;
    private SkillGapReport skillGapReport;
    private Map<String, Double> matchBenchmarks = new HashMap<>();
    
    public JobRecommendation() {
    }
    
    public JobRecommendation(Job job, double matchScore, double aiConfidenceScore, SkillGapReport skillGapReport, Map<String, Double> matchBenchmarks) {
        this.job = job;
        this.matchScore = matchScore;
        this.aiConfidenceScore = aiConfidenceScore;
        this.skillGapReport = skillGapReport;
        this.matchBenchmarks = matchBenchmarks;
    }
    
    public Job getJob() {
        return job;
    }
    
    public void setJob(Job job) {
        this.job = job;
    }
    
    public double getMatchScore() {
        return matchScore;
    }
    
    public void setMatchScore(double matchScore) {
        this.matchScore = matchScore;
    }
    
    public double getAiConfidenceScore() {
        return aiConfidenceScore;
    }
    
    public void setAiConfidenceScore(double aiConfidenceScore) {
        this.aiConfidenceScore = aiConfidenceScore;
    }
    
    public SkillGapReport getSkillGapReport() {
        return skillGapReport;
    }
    
    public void setSkillGapReport(SkillGapReport skillGapReport) {
        this.skillGapReport = skillGapReport;
    }
    
    public Map<String, Double> getMatchBenchmarks() {
        return matchBenchmarks;
    }
    
    public void setMatchBenchmarks(Map<String, Double> matchBenchmarks) {
        this.matchBenchmarks = matchBenchmarks;
    }
}
