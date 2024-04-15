package com.shangwa.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shangwa.auth.entity.User;
import com.shangwa.auth.lib.AuthPayload;
import com.shangwa.auth.lib.LoginCredidentials;
import com.shangwa.auth.lib.exceptions.BadAuthorizationHeader;

@Service
public class AuthService {
    
    @Autowired
    private UserService userService;

    @Autowired
    private JwtService jwtService;

    public AuthPayload create(User user) {
        userService.addUser(user);
        String token = jwtService.issueToken(user);

        return new AuthPayload(token, "Account created successfully");
    }

    public AuthPayload login(LoginCredidentials creds) {
        AuthPayload res = new AuthPayload(null, "Invalid credidentials");
        Optional<User> user =userService.getUser(creds.email, creds.password);
        
        if (user.isPresent()) {
            res.token = jwtService.issueToken(user.get());
            res.message = "Logged in successfuly";
        }

        return res;
    }

    public Object verifyRequest(String authToken) throws BadAuthorizationHeader {
        return jwtService.verifyToken(authToken);
    }

}
