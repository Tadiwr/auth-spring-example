package com.shangwa.auth.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shangwa.auth.entity.User;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    // private SecretKey key = Jwts.SIG.HS256.key().build();

    @Value("${jwt.key}")
    private String jwtKey;

    private SecretKey getSecretKey() {
        byte[] keyStr = jwtKey.getBytes(StandardCharsets.UTF_8);
        SecretKey key = Keys.hmacShaKeyFor(keyStr);
        return key;
    }
    

    public String issueToken(User user) {
    
        String token = Jwts.builder()
        .subject(user.getId().toString())
        .claim("name", user.getName())
        .issuedAt(new Date())
        .signWith(getSecretKey())
        .compact();

        return token;
    }

}
