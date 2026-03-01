package com.aijobmatch.repository;

import com.aijobmatch.model.Application;
import com.aijobmatch.model.ApplicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {
    
    List<Application> findByUserId(Long userId);
    
    List<Application> findByUserIdAndStatus(Long userId, ApplicationStatus status);
    
    Optional<Application> findByUserIdAndJobId(Long userId, Long jobId);
    
    boolean existsByUserIdAndJobId(Long userId, Long jobId);
    
    long countByUserIdAndStatus(Long userId, ApplicationStatus status);
    
    long countByUserId(Long userId);
}
