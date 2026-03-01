package com.aijobmatch.model;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "skills")
public class Skill {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(nullable = false, unique = true)
    private String name;
    
    @Column(nullable = false)
    private String category;
    
    @ManyToMany(mappedBy = "requiredSkills")
    private List<Job> jobs = new ArrayList<>();
    
    public Skill() {
    }
    
    public Skill(Long id, String name, String category, List<Job> jobs) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.jobs = jobs;
    }
    
    public Long getId() {
        return id;
    }
    
    public void setId(Long id) {
        this.id = id;
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
    
    public List<Job> getJobs() {
        return jobs;
    }
    
    public void setJobs(List<Job> jobs) {
        this.jobs = jobs;
    }
}
