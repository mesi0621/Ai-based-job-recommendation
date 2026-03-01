package com.aijobmatch.controller;

import com.aijobmatch.dto.AuthenticationResponse;
import com.aijobmatch.dto.LoginRequest;
import com.aijobmatch.dto.PasswordResetRequest;
import com.aijobmatch.dto.RegisterRequest;
import com.aijobmatch.model.OAuthProvider;
import com.aijobmatch.service.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthenticationController {
    
    private final AuthenticationService authenticationService;
    
    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    
    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(@RequestBody RegisterRequest request) {
        AuthenticationResponse response = authenticationService.register(
                request.getFullName(),
                request.getEmail(),
                request.getPhone(),
                request.getPassword()
        );
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(@RequestBody LoginRequest request) {
        AuthenticationResponse response = authenticationService.authenticate(
                request.getEmail(),
                request.getPassword(),
                request.isRememberMe()
        );
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/oauth/{provider}")
    public ResponseEntity<AuthenticationResponse> oauthLogin(
            @PathVariable String provider,
            @RequestBody String token) {
        OAuthProvider oauthProvider = OAuthProvider.valueOf(provider.toUpperCase());
        AuthenticationResponse response = authenticationService.authenticateWithOAuth(oauthProvider, token);
        return ResponseEntity.ok(response);
    }
    
    @PostMapping("/logout")
    public ResponseEntity<Void> logout(@RequestHeader("Authorization") String token) {
        String jwt = token.substring(7); // Remove "Bearer " prefix
        authenticationService.logout(jwt);
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/password-reset")
    public ResponseEntity<Void> initiatePasswordReset(@RequestBody PasswordResetRequest request) {
        authenticationService.initiatePasswordReset(request.getEmail());
        return ResponseEntity.ok().build();
    }
    
    @PostMapping("/password-reset/confirm")
    public ResponseEntity<Void> confirmPasswordReset(
            @RequestParam String token,
            @RequestBody PasswordResetRequest request) {
        authenticationService.resetPassword(token, request.getNewPassword());
        return ResponseEntity.ok().build();
    }
}
