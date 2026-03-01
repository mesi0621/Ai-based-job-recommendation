package com.aijobmatch.service;

import com.aijobmatch.dto.ResumeData;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

@Service
public class AIServiceClient {
    
    private final RestTemplate restTemplate = new RestTemplate();
    
    @Value("${ai.service.url}")
    private String aiServiceUrl;
    
    @Value("${ai.service.timeout.parsing}")
    private long parsingTimeout;
    
    @Value("${ai.service.timeout.matching}")
    private long matchingTimeout;
    
    public ResumeData parseResume(String resumeText) {
        try {
            String url = aiServiceUrl + "/api/parse-resume";
            
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            
            Map<String, String> request = new HashMap<>();
            request.put("text", resumeText);
            
            HttpEntity<Map<String, String>> entity = new HttpEntity<>(request, headers);
            
            // TODO: Implement actual REST call with timeout
            // ResponseEntity<ResumeData> response = restTemplate.postForEntity(url, entity, ResumeData.class);
            // return response.getBody();
            
            // Mock implementation for now
            return createMockResumeData(resumeText);
            
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse resume with AI service: " + e.getMessage(), e);
        }
    }
    
    public double calculateJobMatchScore(Long userId, Long jobId) {
        try {
            // TODO: Implement actual AI service call
            // Mock implementation returns a random score
            return 75.0 + (Math.random() * 20);
        } catch (Exception e) {
            throw new RuntimeException("Failed to calculate job match score: " + e.getMessage(), e);
        }
    }
    
    public double getAIConfidenceScore(Long userId, Long jobId) {
        try {
            // TODO: Implement actual AI service call
            // Mock implementation returns a random confidence score
            return 80.0 + (Math.random() * 15);
        } catch (Exception e) {
            throw new RuntimeException("Failed to get AI confidence score: " + e.getMessage(), e);
        }
    }
    
    private ResumeData createMockResumeData(String resumeText) {
        ResumeData data = new ResumeData();
        data.setFullName("John Doe");
        data.setEmail("john.doe@example.com");
        data.setPhone("+1234567890");
        data.setSummary("Experienced software engineer with expertise in Java and Spring Boot");
        
        // Add mock skill
        ResumeData.SkillData skill = new ResumeData.SkillData();
        skill.setName("Java");
        skill.setCategory("Technical");
        skill.setYearsOfExperience(5);
        skill.setProficiencyLevel("Advanced");
        data.getSkills().add(skill);
        
        // Add mock experience
        ResumeData.ExperienceData experience = new ResumeData.ExperienceData();
        experience.setCompany("Tech Corp");
        experience.setTitle("Software Engineer");
        experience.setLocation("San Francisco, CA");
        experience.setDescription("Developed enterprise applications");
        experience.setStartDate("2020-01-01");
        experience.setEndDate("2024-01-01");
        experience.setCurrent(false);
        data.getExperiences().add(experience);
        
        // Add mock education
        ResumeData.EducationData education = new ResumeData.EducationData();
        education.setInstitution("University of Technology");
        education.setDegree("Bachelor of Science");
        education.setFieldOfStudy("Computer Science");
        education.setGraduationDate("2019-05-01");
        education.setGpa(3.8);
        data.getEducations().add(education);
        
        return data;
    }
}
