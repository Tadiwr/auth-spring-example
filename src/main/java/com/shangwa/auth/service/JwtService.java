package com.shangwa.auth.service;

import java.nio.charset.StandardCharsets;
import java.util.Date;

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
    

    /** Generates a JWT token for a user */
    public String issueToken(User user) {
    
        String token = Jwts.builder()
        .subject(user.getId().toString())
        .claim("name", user.getName())
        .issuedAt(new Date())
        .signWith(getSecretKey())
        .compact();

        return token;
    }

    /** Gets the User who is the bearer of the token based on the claims in the JWT payload
     * 
     * @returns User
     */
    private User getTokenBearer(Claims claims) {
        Long subId = Long.parseLong(claims.get("sub", String.class));
        User user = userRepo.findById(subId).get();
        return user;
    }

    /** Attempts to verify a token and return the `User` bearing the token
     * @throws BadAuthorizationHeader if the token is missing or is invalid
     */
    public User verifyToken(String token) throws BadAuthorizationHeader {

        try {
            Object payload = Jwts.parser()
            .verifyWith(getSecretKey())
            .build()
            .parse(token).getPayload();

            Claims claims = (Claims) payload;
            return getTokenBearer(claims);
            
        } catch (Exception e) {
            throw new BadAuthorizationHeader();
        }
        
    }

}
