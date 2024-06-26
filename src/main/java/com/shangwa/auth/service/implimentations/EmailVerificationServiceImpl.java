package com.shangwa.auth.service.implimentations;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shangwa.auth.entity.User;
import com.shangwa.auth.repository.UsersReposity;
import com.shangwa.auth.service.EmailService;
import com.shangwa.auth.service.interfaces.EmailVerificationService;
import com.shangwa.auth.service.interfaces.UserService;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import jakarta.mail.MessagingException;


@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    // @Value("email.verifcation.key")
    @Value("${email.verifcation.key}")
    private String keyStr;

    @Autowired
    private EmailService emailService; 

    @Autowired
    private UsersReposity userRepo;

    public String createEmailVerificationToken(User user)  {

        // A second has 1000 milliseconds * 1800 seconds in thirty minutes
        Long expiryDateTime = System.currentTimeMillis() + (1000 * 1800);

        System.out.println("The expiry date: " +  expiryDateTime);

        String token = Jwts.builder()
        .subject(user.getEmail())
        .claim("user_id", user.getId())
        .expiration( new Date(expiryDateTime))
        .issuedAt(new Date())
        .signWith(getKey())
        .header().add("type", "email-verification").and()
        .compact();

        System.out.println(token);
        System.out.println(new Date(expiryDateTime).toString());

        return token;
    }

    public void sendTokenToUserEmail(String token, User user) throws MessagingException {
        emailService.sendVerficationEmail(token, user);
    }

    public Optional<User> verifyEmailToken(String token) {

        try {
            Object payload = Jwts.parser()
            .verifyWith(getKey())
            .build()
            .parse(token).getPayload();
    
            Claims claims = (Claims) payload;
            Date expiry = claims.getExpiration();
    
            // check time
            if (new Date().after(expiry)) {
                return null;
            }

            // get the user
            String email = claims.getSubject();

            return userRepo.findByEmail(email);

        } catch (Exception e) {
            return null;
        }

    }

    /** Generates a secret key for usng the Advanced Encryption Standard */
    private SecretKey getKey() {
        byte[] bytes = keyStr.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(bytes);
    }

}
