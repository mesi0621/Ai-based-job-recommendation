package com.aijobmatch.service;

import com.aijobmatch.dto.AuthenticationResponse;
import com.aijobmatch.model.OAuthProvider;
import com.aijobmatch.model.PasswordResetToken;
import com.aijobmatch.model.User;
import com.aijobmatch.repository.PasswordResetTokenRepository;
import com.aijobmatch.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AuthenticationService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    
    public AuthenticationService(UserRepository userRepository, PasswordEncoder passwordEncoder,
                                JwtService jwtService, PasswordResetTokenRepository passwordResetTokenRepository) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.passwordResetTokenRepository = passwordResetTokenRepository;
    }
    
    @Transactional
    public AuthenticationResponse register(String fullName, String email, String phone, String password) {
        // Check if user already exists
        if (userRepository.existsByEmail(email)) {
            throw new RuntimeException("Email already registered");
        }
        
        // Create new user
        User user = new User();
        user.setFullName(fullName);
        user.setEmail(email);
        user.setPhone(phone);
        user.setPasswordHash(passwordEncoder.encode(password));
        user.setCreatedAt(LocalDateTime.now());
        
        // Save user
        user = userRepository.save(user);
        
        // Generate JWT token
        String token = jwtService.generateToken(email, false);
        
        return new AuthenticationResponse(token, user.getEmail(), user.getFullName(), user.getId());
    }
    
    @Transactional
    public AuthenticationResponse authenticate(String email, String password, boolean rememberMe) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid credentials"));
        
        if (!passwordEncoder.matches(password, user.getPasswordHash())) {
            throw new RuntimeException("Invalid credentials");
        }
        
        // Update last login time
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
        
        String token = jwtService.generateToken(email, rememberMe);
        
        return new AuthenticationResponse(token, user.getEmail(), user.getFullName(), user.getId());
    }
    
    public boolean validateSession(String token) {
        try {
            String email = jwtService.extractEmail(token);
            return userRepository.existsByEmail(email) && !jwtService.isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }
    
    public void logout(String sessionToken) {
        // JWT tokens are stateless, so logout is handled client-side
        // This method can be extended to maintain a blacklist if needed
    }
    
    @Transactional
    public void initiatePasswordReset(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));
        
        // Delete any existing reset tokens for this user
        passwordResetTokenRepository.deleteByUserId(user.getId());
        
        // Generate reset token
        String resetToken = UUID.randomUUID().toString();
        
        // Create and save password reset token
        PasswordResetToken token = new PasswordResetToken();
        token.setToken(resetToken);
        token.setUser(user);
        token.setExpiryDate(LocalDateTime.now().plusHours(24));
        passwordResetTokenRepository.save(token);
        
        // TODO: Send email via EmailService
        System.out.println("Password reset token for " + email + ": " + resetToken);
    }
    
    @Transactional
    public void resetPassword(String resetToken, String newPassword) {
        PasswordResetToken token = passwordResetTokenRepository.findByToken(resetToken)
                .orElseThrow(() -> new RuntimeException("Invalid reset token"));
        
        if (token.isExpired()) {
            throw new RuntimeException("Reset token has expired");
        }
        
        if (token.isUsed()) {
            throw new RuntimeException("Reset token has already been used");
        }
        
        // Update user password
        User user = token.getUser();
        user.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepository.save(user);
        
        // Mark token as used
        token.setUsed(true);
        passwordResetTokenRepository.save(token);
    }
    
    @Transactional
    public AuthenticationResponse authenticateWithOAuth(OAuthProvider provider, String token) {
        // TODO: Validate OAuth token with provider
        // TODO: Extract user information from OAuth provider
        // For now, this is a placeholder implementation
        
        // Mock OAuth user data extraction
        String email = extractEmailFromOAuthToken(provider, token);
        String fullName = extractNameFromOAuthToken(provider, token);
        
        // Find or create user
        User user = userRepository.findByEmail(email)
                .orElseGet(() -> {
                    User newUser = new User();
                    newUser.setEmail(email);
                    newUser.setFullName(fullName);
                    newUser.setPasswordHash(passwordEncoder.encode(UUID.randomUUID().toString()));
                    return userRepository.save(newUser);
                });
        
        // Update last login time
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
        
        String jwtToken = jwtService.generateToken(email, false);
        
        return new AuthenticationResponse(jwtToken, user.getEmail(), user.getFullName(), user.getId());
    }
    
    private String extractEmailFromOAuthToken(OAuthProvider provider, String token) {
        // TODO: Implement actual OAuth token validation and email extraction
        return "oauth.user@example.com";
    }
    
    private String extractNameFromOAuthToken(OAuthProvider provider, String token) {
        // TODO: Implement actual OAuth token validation and name extraction
        return "OAuth User";
    }
}
