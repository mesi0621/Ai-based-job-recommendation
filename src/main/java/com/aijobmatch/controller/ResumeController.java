package com.aijobmatch.controller;

import com.aijobmatch.dto.ResumeData;
import com.aijobmatch.service.ResumeAnalysisService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/resumes")
public class ResumeController {
    
    private final ResumeAnalysisService resumeAnalysisService;
    
    public ResumeController(ResumeAnalysisService resumeAnalysisService) {
        this.resumeAnalysisService = resumeAnalysisService;
    }
    
    @PostMapping("/upload")
    public ResponseEntity<Map<String, String>> uploadResume(
            @RequestParam("file") MultipartFile file,
            Authentication authentication) {
        
        Long userId = getUserIdFromAuth(authentication);
        String resumeId = resumeAnalysisService.uploadResume(userId, file);
        
        Map<String, String> response = new HashMap<>();
        response.put("resumeId", resumeId);
        response.put("message", "Resume uploaded successfully and queued for analysis");
        
        return ResponseEntity.ok(response);
    }
    
    @GetMapping("/me")
    public ResponseEntity<ResumeData> getMyResume(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        ResumeData resumeData = resumeAnalysisService.getResumeData(userId);
        return ResponseEntity.ok(resumeData);
    }
    
    @DeleteMapping("/me")
    public ResponseEntity<Void> deleteMyResume(Authentication authentication) {
        Long userId = getUserIdFromAuth(authentication);
        resumeAnalysisService.deleteResume(userId);
        return ResponseEntity.ok().build();
    }
    
    private Long getUserIdFromAuth(Authentication authentication) {
        // TODO: Implement proper user ID extraction from authentication
        // For now, return a mock ID
        return 1L;
    }
}
