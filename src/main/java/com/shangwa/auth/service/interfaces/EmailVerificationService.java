package com.shangwa.auth.service.interfaces;

import org.springframework.stereotype.Service;

import com.shangwa.auth.entity.User;

import jakarta.mail.MessagingException;

/** Acts as a helper to the auth service for email verification */

@Service
public interface EmailVerificationService {
    
    /** Creates an email verification token for a user */
    public String createEmailVerificationToken(User user);

    /** sends an email to the user with a verification token */
    public void sendTokenToUserEmail(String token, User user) throws MessagingException;

    /** Takes in an email verification token and verifies and validates it */
    public boolean verifyEmailToken(String token);

}
