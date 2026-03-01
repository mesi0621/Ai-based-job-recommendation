package com.aijobmatch.repository;

import com.aijobmatch.model.ExtractedSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExtractedSkillRepository extends JpaRepository<ExtractedSkill, Long> {
    
    List<ExtractedSkill> findByResumeId(Long resumeId);
}
