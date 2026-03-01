package com.aijobmatch.service;

import com.aijobmatch.dto.SkillGapReport;
import com.aijobmatch.model.Job;
import com.aijobmatch.model.Resume;
import com.aijobmatch.model.Skill;
import com.aijobmatch.repository.JobRepository;
import com.aijobmatch.repository.ResumeRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class SkillGapAnalysisService {
    
    private final ResumeRepository resumeRepository;
    private final JobRepository jobRepository;
    
    public SkillGapAnalysisService(ResumeRepository resumeRepository, JobRepository jobRepository) {
        this.resumeRepository = resumeRepository;
        this.jobRepository = jobRepository;
    }
    
    @Transactional(readOnly = true)
    public SkillGapReport analyzeSkillGap(Long userId, Long jobId) {
        Resume resume = resumeRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Resume not found for user"));
        
        Job job = jobRepository.findById(jobId)
                .orElseThrow(() -> new RuntimeException("Job not found"));
        
        return analyzeSkillGap(resume, job);
    }
    
    private SkillGapReport analyzeSkillGap(Resume resume, Job job) {
        SkillGapReport report = new SkillGapReport();
        
        // Get user skills
        Set<String> userSkillNames = resume.getSkills().stream()
                .map(es -> es.getSkill().getName().toLowerCase())
                .collect(Collectors.toSet());
        
        // Get required skills
        List<Skill> requiredSkills = job.getRequiredSkills();
        
        // Identify matched and missing skills
        for (Skill requiredSkill : requiredSkills) {
            if (userSkillNames.contains(requiredSkill.getName().toLowerCase())) {
                report.getMatchedSkills().add(requiredSkill);
            } else {
                report.getMissingSkills().add(requiredSkill);
            }
        }
        
        // Calculate match percentage
        if (!requiredSkills.isEmpty()) {
            double matchPercentage = (report.getMatchedSkills().size() * 100.0) / requiredSkills.size();
            report.setMatchPercentage(Math.round(matchPercentage * 100.0) / 100.0);
        } else {
            report.setMatchPercentage(100.0);
        }
        
        // Categorize skills
        Map<String, Integer> categoryBreakdown = new HashMap<>();
        for (Skill skill : requiredSkills) {
            String category = skill.getCategory();
            categoryBreakdown.put(category, categoryBreakdown.getOrDefault(category, 0) + 1);
        }
        report.setCategoryBreakdown(categoryBreakdown);
        
        return report;
    }
    
    @Transactional(readOnly = true)
    public List<Skill> getMissingSkills(Long userId, Long jobId) {
        SkillGapReport report = analyzeSkillGap(userId, jobId);
        return report.getMissingSkills();
    }
    
    @Transactional(readOnly = true)
    public double calculateSkillMatchPercentage(Long userId, Long jobId) {
        SkillGapReport report = analyzeSkillGap(userId, jobId);
        return report.getMatchPercentage();
    }
}
