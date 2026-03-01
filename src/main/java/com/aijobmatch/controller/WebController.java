package com.aijobmatch.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {
    
    @GetMapping("/login")
    public String login() {
        return "login";
    }
    
    @GetMapping("/register")
    public String register() {
        return "register";
    }
    
    @GetMapping("/dashboard")
    public String dashboard() {
        return "dashboard";
    }
    
    @GetMapping("/jobs")
    public String jobs() {
        return "jobs";
    }
    
    @GetMapping("/jobs/{id}")
    public String jobDetails() {
        return "job-details";
    }
    
    @GetMapping("/resume")
    public String resume() {
        return "resume";
    }
    
    @GetMapping("/applications")
    public String applications() {
        return "applications";
    }
    
    @GetMapping("/profile")
    public String profile() {
        return "profile";
    }
}
