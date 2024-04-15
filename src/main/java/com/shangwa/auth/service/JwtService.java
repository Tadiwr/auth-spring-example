package com.shangwa.auth.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Optional;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.shangwa.auth.entity.User;
import com.shangwa.auth.lib.exceptions.BadAuthorizationHeader;
import com.shangwa.auth.repository.UsersReposity;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {
    
    // private SecretKey key = Jwts.SIG.HS256.key().build();

    @Autowired
    private UsersReposity userRepo;

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

    public User verifyToken(String token) throws BadAuthorizationHeader {

        try {
            Object payload = Jwts.parser()
            .verifyWith(getSecretKey())
            .build()
            .parse(token).getPayload();

            // Cast the payload to Claims
            Claims claims = (Claims) payload;
            Long subId = Long.parseLong(claims.get("sub", String.class));
            User user = userRepo.findById(subId).get();
            return user;
            
        } catch (Exception e) {
            throw new BadAuthorizationHeader();
        }
        
    }

}
