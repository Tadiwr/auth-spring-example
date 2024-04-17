package com.shangwa.auth.service.implimentations;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shangwa.auth.entity.User;
import com.shangwa.auth.service.interfaces.EmailVerificationService;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


@Service
public class EmailVerificationServiceImpl implements EmailVerificationService {

    // @Value("email.verifcation.key")
    @Value("${email.verifcation.key}")
    private String keyStr;

    @Override
    public String createEmailVerificationToken(User user) {

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

    @Override
    public void sendTokenToUserEmail(String token, User user) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendTokenToUserEmail'");
    }

    @Override
    public boolean verifyEmailToken(String token) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'verifyEmailToken'");
    }

    /** Generates a secret key for usng the Advanced Encryption Standard */
    private SecretKey getKey() {
        byte[] bytes = keyStr.getBytes(StandardCharsets.UTF_8);
        return Keys.hmacShaKeyFor(bytes);
    }

}
