package com.aijobmatch.service;

import com.aijobmatch.dto.ResumeData;
import com.aijobmatch.model.*;
import com.aijobmatch.repository.*;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class ResumeAnalysisService {
    
    private final FileValidationService fileValidationService;
    private final TextExtractionService textExtractionService;
    private final AIServiceClient aiServiceClient;
    private final ResumeRepository resumeRepository;
    private final UserRepository userRepository;
    private final SkillRepository skillRepository;
    
    public ResumeAnalysisService(FileValidationService fileValidationService, TextExtractionService textExtractionService,
                                AIServiceClient aiServiceClient, ResumeRepository resumeRepository,
                                UserRepository userRepository, SkillRepository skillRepository) {
        this.fileValidationService = fileValidationService;
        this.textExtractionService = textExtractionService;
        this.aiServiceClient = aiServiceClient;
        this.resumeRepository = resumeRepository;
        this.userRepository = userRepository;
        this.skillRepository = skillRepository;
    }
    
    @Transactional
    public String uploadResume(Long userId, MultipartFile file) {
        // Validate file
        fileValidationService.validateFile(file);
        
        // Get user
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Delete existing resume if any
        resumeRepository.findByUserId(userId).ifPresent(resumeRepository::delete);
        
        try {
            // Extract text
            String extractedText = textExtractionService.extractText(file);
            
            // Create resume entity
            Resume resume = new Resume();
            resume.setUser(user);
            resume.setFileName(file.getOriginalFilename());
            resume.setFileUrl("/uploads/" + UUID.randomUUID() + "_" + file.getOriginalFilename());
            resume.setExtractedText(extractedText);
            resume.setUploadedAt(LocalDateTime.now());
            
            Resume savedResume = resumeRepository.save(resume);
            
            // Trigger async analysis
            analyzeResumeAsync(savedResume.getId());
            
            return savedResume.getId().toString();
            
        } catch (IOException e) {
            throw new RuntimeException("Failed to process resume file: " + e.getMessage(), e);
        }
    }
    
    @Async
    @Transactional
    public void analyzeResumeAsync(Long resumeId) {
        Resume resume = resumeRepository.findById(resumeId)
                .orElseThrow(() -> new RuntimeException("Resume not found"));
        
        try {
            // Call AI service to parse resume
            ResumeData resumeData = aiServiceClient.parseResume(resume.getExtractedText());
            
            // Store extracted data
            storeResumeData(resume, resumeData);
            
            // Update analyzed timestamp
            resume.setAnalyzedAt(LocalDateTime.now());
            resumeRepository.save(resume);
            
        } catch (Exception e) {
            // Log error and notify user
            System.err.println("Resume analysis failed for resume " + resumeId + ": " + e.getMessage());
            // TODO: Implement user notification
        }
    }
    
    private void storeResumeData(Resume resume, ResumeData data) {
        // Store summary
        resume.setSummary(data.getSummary());
        
        // Store skills
        for (ResumeData.SkillData skillData : data.getSkills()) {
            Skill skill = skillRepository.findByName(skillData.getName())
                    .orElseGet(() -> {
                        Skill newSkill = new Skill();
                        newSkill.setName(skillData.getName());
                        newSkill.setCategory(skillData.getCategory());
                        return skillRepository.save(newSkill);
                    });
            
            ExtractedSkill extractedSkill = new ExtractedSkill();
            extractedSkill.setResume(resume);
            extractedSkill.setSkill(skill);
            extractedSkill.setYearsOfExperience(skillData.getYearsOfExperience());
            extractedSkill.setProficiencyLevel(skillData.getProficiencyLevel());
            resume.getSkills().add(extractedSkill);
        }
        
        // Store experiences
        for (ResumeData.ExperienceData expData : data.getExperiences()) {
            Experience experience = new Experience();
            experience.setResume(resume);
            experience.setCompany(expData.getCompany());
            experience.setTitle(expData.getTitle());
            experience.setLocation(expData.getLocation());
            experience.setDescription(expData.getDescription());
            experience.setStartDate(LocalDate.parse(expData.getStartDate()));
            if (expData.getEndDate() != null && !expData.getEndDate().isEmpty()) {
                experience.setEndDate(LocalDate.parse(expData.getEndDate()));
            }
            experience.setCurrent(expData.isCurrent());
            resume.getExperiences().add(experience);
        }
        
        // Store educations
        for (ResumeData.EducationData eduData : data.getEducations()) {
            Education education = new Education();
            education.setResume(resume);
            education.setInstitution(eduData.getInstitution());
            education.setDegree(eduData.getDegree());
            education.setFieldOfStudy(eduData.getFieldOfStudy());
            if (eduData.getGraduationDate() != null && !eduData.getGraduationDate().isEmpty()) {
                education.setGraduationDate(LocalDate.parse(eduData.getGraduationDate()));
            }
            education.setGpa(eduData.getGpa());
            resume.getEducations().add(education);
        }
    }
    
    @Transactional(readOnly = true)
    public ResumeData getResumeData(Long userId) {
        Resume resume = resumeRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Resume not found for user"));
        
        return convertToResumeData(resume);
    }
    
    @Transactional
    public void deleteResume(Long userId) {
        resumeRepository.deleteByUserId(userId);
    }
    
    private ResumeData convertToResumeData(Resume resume) {
        ResumeData data = new ResumeData();
        data.setSummary(resume.getSummary());
        
        // Convert skills
        for (ExtractedSkill extractedSkill : resume.getSkills()) {
            ResumeData.SkillData skillData = new ResumeData.SkillData();
            skillData.setName(extractedSkill.getSkill().getName());
            skillData.setCategory(extractedSkill.getSkill().getCategory());
            skillData.setYearsOfExperience(extractedSkill.getYearsOfExperience());
            skillData.setProficiencyLevel(extractedSkill.getProficiencyLevel());
            data.getSkills().add(skillData);
        }
        
        // Convert experiences
        for (Experience experience : resume.getExperiences()) {
            ResumeData.ExperienceData expData = new ResumeData.ExperienceData();
            expData.setCompany(experience.getCompany());
            expData.setTitle(experience.getTitle());
            expData.setLocation(experience.getLocation());
            expData.setDescription(experience.getDescription());
            expData.setStartDate(experience.getStartDate().toString());
            if (experience.getEndDate() != null) {
                expData.setEndDate(experience.getEndDate().toString());
            }
            expData.setCurrent(experience.isCurrent());
            data.getExperiences().add(expData);
        }
        
        // Convert educations
        for (Education education : resume.getEducations()) {
            ResumeData.EducationData eduData = new ResumeData.EducationData();
            eduData.setInstitution(education.getInstitution());
            eduData.setDegree(education.getDegree());
            eduData.setFieldOfStudy(education.getFieldOfStudy());
            if (education.getGraduationDate() != null) {
                eduData.setGraduationDate(education.getGraduationDate().toString());
            }
            eduData.setGpa(education.getGpa());
            data.getEducations().add(eduData);
        }
        
        return data;
    }
}
