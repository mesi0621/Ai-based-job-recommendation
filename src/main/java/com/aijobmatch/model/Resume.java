package com.aijobmatch.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "resumes")
public class Resume {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(nullable = false)
    private String fileName;
    
    private String fileUrl;
    
    @Column(columnDefinition = "TEXT")
    private String extractedText;
    
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ExtractedSkill> skills = new ArrayList<>();
    
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences = new ArrayList<>();
    
    @OneToMany(mappedBy = "resume", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Education> educations = new ArrayList<>();
    
    @Column(columnDefinition = "TEXT")
    private String summary;
    
    @Column(nullable = false)
    private LocalDateTime uploadedAt;
    
    private LocalDateTime analyzedAt;
    
    public Resume() {
    }
    
    public Resume(Long id, User user, String fileName, String fileUrl, String extractedText, List<ExtractedSkill> skills, List<Experience> experiences, List<Education> educations, String summary, LocalDateTime uploadedAt, LocalDateTime analyzedAt) {
        this.id = id;
        this.user = user;
        this.fileName = fileName;
        this.fileUrl = fileUrl;
        this.extractedText = extractedText;
        this.skills = skills;
        this.experiences = experiences;
        this.educations = educations;
        this.summary = summary;
        this.uploadedAt = uploadedAt;
        this.analyzedAt = analyzedAt;
    }
    
    @PrePersist
    protected void onCreate() {
        if (uploadedAt == null) {
            uploadedAt = LocalDateTime.now();
        }
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public String getFileName() {
        return fileName;
    }
    
    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
    
    public String getFileUrl() {
        return fileUrl;
    }
    
    public void setFileUrl(String fileUrl) {
        this.fileUrl = fileUrl;
    }
    
    public String getExtractedText() {
        return extractedText;
    }
    
    public void setExtractedText(String extractedText) {
        this.extractedText = extractedText;
    }
    
    public List<ExtractedSkill> getSkills() {
        return skills;
    }
    
    public void setSkills(List<ExtractedSkill> skills) {
        this.skills = skills;
    }
    
    public List<Experience> getExperiences() {
        return experiences;
    }
    
    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }
    
    public List<Education> getEducations() {
        return educations;
    }
    
    public void setEducations(List<Education> educations) {
        this.educations = educations;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public LocalDateTime getUploadedAt() {
        return uploadedAt;
    }
    
    public void setUploadedAt(LocalDateTime uploadedAt) {
        this.uploadedAt = uploadedAt;
    }
    
    public LocalDateTime getAnalyzedAt() {
        return analyzedAt;
    }
    
    public void setAnalyzedAt(LocalDateTime analyzedAt) {
        this.analyzedAt = analyzedAt;
    }
}
