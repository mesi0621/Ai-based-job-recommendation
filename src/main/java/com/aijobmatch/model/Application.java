package com.aijobmatch.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(
    name = "applications",
    uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "job_id"})
)
public class Application {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "job_id", nullable = false)
    private Job job;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ApplicationStatus status;
    
    @Column(columnDefinition = "TEXT", nullable = false)
    private String resumeSnapshot;
    
    @Column(nullable = false, updatable = false)
    private LocalDateTime appliedAt;
    
    @Column(nullable = false)
    private LocalDateTime statusUpdatedAt;
    
    public Application() {
    }
    
    public Application(Long id, User user, Job job, ApplicationStatus status, String resumeSnapshot, LocalDateTime appliedAt, LocalDateTime statusUpdatedAt) {
        this.id = id;
        this.user = user;
        this.job = job;
        this.status = status;
        this.resumeSnapshot = resumeSnapshot;
        this.appliedAt = appliedAt;
        this.statusUpdatedAt = statusUpdatedAt;
    }
    
    @PrePersist
    protected void onCreate() {
        appliedAt = LocalDateTime.now();
        statusUpdatedAt = LocalDateTime.now();
        if (status == null) {
            status = ApplicationStatus.RESUME;
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        statusUpdatedAt = LocalDateTime.now();
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
    
    public Job getJob() {
        return job;
    }
    
    public void setJob(Job job) {
        this.job = job;
    }
    
    public ApplicationStatus getStatus() {
        return status;
    }
    
    public void setStatus(ApplicationStatus status) {
        this.status = status;
    }
    
    public String getResumeSnapshot() {
        return resumeSnapshot;
    }
    
    public void setResumeSnapshot(String resumeSnapshot) {
        this.resumeSnapshot = resumeSnapshot;
    }
    
    public LocalDateTime getAppliedAt() {
        return appliedAt;
    }
    
    public void setAppliedAt(LocalDateTime appliedAt) {
        this.appliedAt = appliedAt;
    }
    
    public LocalDateTime getStatusUpdatedAt() {
        return statusUpdatedAt;
    }
    
    public void setStatusUpdatedAt(LocalDateTime statusUpdatedAt) {
        this.statusUpdatedAt = statusUpdatedAt;
    }
}
