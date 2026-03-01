package com.aijobmatch.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "educations")
public class Education {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "resume_id", nullable = false)
    private Resume resume;
    
    @Column(nullable = false)
    private String institution;
    
    @Column(nullable = false)
    private String degree;
    
    private String fieldOfStudy;
    
    private LocalDate graduationDate;
    
    private Double gpa;
    
    public Education() {
    }
    
    public Education(Long id, Resume resume, String institution, String degree, String fieldOfStudy, LocalDate graduationDate, Double gpa) {
        this.id = id;
        this.resume = resume;
        this.institution = institution;
        this.degree = degree;
        this.fieldOfStudy = fieldOfStudy;
        this.graduationDate = graduationDate;
        this.gpa = gpa;
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
    
    public LocalDate getGraduationDate() {
        return graduationDate;
    }
    
    public void setGraduationDate(LocalDate graduationDate) {
        this.graduationDate = graduationDate;
    }
    
    public Double getGpa() {
        return gpa;
    }
    
    public void setGpa(Double gpa) {
        this.gpa = gpa;
    }
}
