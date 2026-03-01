package com.aijobmatch;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class AIJobMatchApplication {
    public static void main(String[] args) {
        SpringApplication.run(AIJobMatchApplication.class, args);
    }
}
