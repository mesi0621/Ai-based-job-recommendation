package com.aijobmatch.dto;

import java.util.ArrayList;
import java.util.List;

public class ResumeData {
    private String fullName;
    private String email;
    private String phone;
    private List<SkillData> skills = new ArrayList<>();
    private List<ExperienceData> experiences = new ArrayList<>();
    private List<EducationData> educations = new ArrayList<>();
    private String summary;
    
    public ResumeData() {
    }
    
    public ResumeData(String fullName, String email, String phone, List<SkillData> skills, List<ExperienceData> experiences, List<EducationData> educations, String summary) {
        this.fullName = fullName;
        this.email = email;
        this.phone = phone;
        this.skills = skills;
        this.experiences = experiences;
        this.educations = educations;
        this.summary = summary;
    }
    
    public String getFullName() {
        return fullName;
    }
    
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email;
    }
    
    public String getPhone() {
        return phone;
    }
    
    public void setPhone(String phone) {
        this.phone = phone;
    }
    
    public List<SkillData> getSkills() {
        return skills;
    }
    
    public void setSkills(List<SkillData> skills) {
        this.skills = skills;
    }
    
    public List<ExperienceData> getExperiences() {
        return experiences;
    }
    
    public void setExperiences(List<ExperienceData> experiences) {
        this.experiences = experiences;
    }
    
    public List<EducationData> getEducations() {
        return educations;
    }
    
    public void setEducations(List<EducationData> educations) {
        this.educations = educations;
    }
    
    public String getSummary() {
        return summary;
    }
    
    public void setSummary(String summary) {
        this.summary = summary;
    }
    
    public static class SkillData {
        private String name;
        private String category;
        private Integer yearsOfExperience;
        private String proficiencyLevel;
        
        public SkillData() {
        }
        
        public SkillData(String name, String category, Integer yearsOfExperience, String proficiencyLevel) {
            this.name = name;
            this.category = category;
            this.yearsOfExperience = yearsOfExperience;
            this.proficiencyLevel = proficiencyLevel;
        }
        
        public String getName() {
            return name;
        }
        
        public void setName(String name) {
            this.name = name;
        }
        
        public String getCategory() {
            return category;
        }
        
        public void setCategory(String category) {
            this.category = category;
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
    
    public static class ExperienceData {
        private String company;
        private String title;
        private String location;
        private String description;
        private String startDate;
        private String endDate;
        private boolean current;
        
        public ExperienceData() {
        }
        
        public ExperienceData(String company, String title, String location, String description, String startDate, String endDate, boolean current) {
            this.company = company;
            this.title = title;
            this.location = location;
            this.description = description;
            this.startDate = startDate;
            this.endDate = endDate;
            this.current = current;
        }
        
        public String getCompany() {
            return company;
        }
        
        public void setCompany(String company) {
            this.company = company;
        }
        
        public String getTitle() {
            return title;
        }
        
        public void setTitle(String title) {
            this.title = title;
        }
        
        public String getLocation() {
            return location;
        }
        
        public void setLocation(String location) {
            this.location = location;
        }
        
        public String getDescription() {
            return description;
        }
        
        public void setDescription(String description) {
            this.description = description;
        }
        
        public String getStartDate() {
            return startDate;
        }
        
        public void setStartDate(String startDate) {
            this.startDate = startDate;
        }
        
        public String getEndDate() {
            return endDate;
        }
        
        public void setEndDate(String endDate) {
            this.endDate = endDate;
        }
        
        public boolean isCurrent() {
            return current;
        }
        
        public void setCurrent(boolean current) {
            this.current = current;
        }
    }
    
    public static class EducationData {
        private String institution;
        private String degree;
        private String fieldOfStudy;
        private String graduationDate;
        private Double gpa;
        
        public EducationData() {
        }
        
        public EducationData(String institution, String degree, String fieldOfStudy, String graduationDate, Double gpa) {
            this.institution = institution;
            this.degree = degree;
            this.fieldOfStudy = fieldOfStudy;
            this.graduationDate = graduationDate;
            this.gpa = gpa;
        }
        
        public String getInstitution() {
            return institution;
        }
        
        public void setInstitution(String institution) {
            this.institution = institution;
        }
        
        public String getDegree() {
            return degree;
        }
        
        public void setDegree(String degree) {
            this.degree = degree;
        }
        
        public String getFieldOfStudy() {
            return fieldOfStudy;
        }
        
        public void setFieldOfStudy(String fieldOfStudy) {
            this.fieldOfStudy = fieldOfStudy;
        }
        
        public String getGraduationDate() {
            return graduationDate;
        }
        
        public void setGraduationDate(String graduationDate) {
            this.graduationDate = graduationDate;
        }
        
        public Double getGpa() {
            return gpa;
        }
        
        public void setGpa(Double gpa) {
            this.gpa = gpa;
        }
    }
}
