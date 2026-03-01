package com.aijobmatch.model;

import jakarta.persistence.*;

@Entity
@Table(name = "extracted_skills")
public class ExtractedSkill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;
    
    @ManyToOne
    @JoinColumn(name = "skill_id", nullable = false)
    private Skill skill;
    
    private Integer yearsOfExperience;
    
    private String proficiencyLevel;
    
    public ExtractedSkill() {
    }
    
    public ExtractedSkill(Long id, Resume resume, Skill skill, Integer yearsOfExperience, String proficiencyLevel) {
        this.id = id;
        this.resume = resume;
        this.skill = skill;
        this.yearsOfExperience = yearsOfExperience;
        this.proficiencyLevel = proficiencyLevel;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
    }
    
    public Resume getResume() {
        return resume;
    }
    
    public void setResume(Resume resume) {
        this.resume = resume;
    }
    
    public Skill getSkill() {
        return skill;
    }
    
    public void setSkill(Skill skill) {
        this.skill = skill;
    }
    
    public Integer getYearsOfExperience() {
        return yearsOfExperience;
    }
    
    public void setYearsOfExperience(Integer yearsOfExperience) {
        this.yearsOfExperience = yearsOfExperience;
    }
    
    public String getProficiencyLevel() {
        return proficiencyLevel;
    }
    
    public void setProficiencyLevel(String proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }
}
