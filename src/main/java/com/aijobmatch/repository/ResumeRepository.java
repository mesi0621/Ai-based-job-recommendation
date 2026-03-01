package com.aijobmatch.repository;

import com.aijobmatch.model.Resume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    
    Optional<Resume> findByUserId(Long userId);
    
    void deleteByUserId(Long userId);
}
