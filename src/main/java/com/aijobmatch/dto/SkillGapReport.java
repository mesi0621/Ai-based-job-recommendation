package com.aijobmatch.dto;

import com.aijobmatch.model.Skill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SkillGapReport {
    private List<Skill> matchedSkills = new ArrayList<>();
    private List<Skill> missingSkills = new ArrayList<>();
    private double matchPercentage;
    private Map<String, Integer> categoryBreakdown = new HashMap<>();
    
    public SkillGapReport() {
    }
    
    public SkillGapReport(List<Skill> matchedSkills, List<Skill> missingSkills, double matchPercentage, Map<String, Integer> categoryBreakdown) {
        this.matchedSkills = matchedSkills;
        this.missingSkills = missingSkills;
        this.matchPercentage = matchPercentage;
        this.categoryBreakdown = categoryBreakdown;
    }
    
    public List<Skill> getMatchedSkills() {
        return matchedSkills;
    }
    
    public void setMatchedSkills(List<Skill> matchedSkills) {
        this.matchedSkills = matchedSkills;
    }
    
    public List<Skill> getMissingSkills() {
        return missingSkills;
    }
    
    public void setMissingSkills(List<Skill> missingSkills) {
        this.missingSkills = missingSkills;
    }
    
    public double getMatchPercentage() {
        return matchPercentage;
    }
    
    public void setMatchPercentage(double matchPercentage) {
        this.matchPercentage = matchPercentage;
    }
    
    public Map<String, Integer> getCategoryBreakdown() {
        return categoryBreakdown;
    }
    
    public void setCategoryBreakdown(Map<String, Integer> categoryBreakdown) {
        this.categoryBreakdown = categoryBreakdown;
    }
}
