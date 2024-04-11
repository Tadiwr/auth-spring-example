package com.shangwa.auth.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shangwa.auth.entity.User;
import com.shangwa.auth.lib.AuthPayload;
import com.shangwa.auth.lib.AuthUser;
import com.shangwa.auth.lib.LoginCredidentials;

@Service
public class AuthService {
    
    @Autowired
    private UserService userService;

    public AuthPayload create(User user) {
        User newUser = userService.addUser(user);
        String token = "";

        return new AuthPayload(newUser, token);
    }

    public AuthPayload login(LoginCredidentials creds) {
        AuthPayload res = new AuthPayload(null, null);
        Optional<User> user =userService.getUser(creds.email, creds.password);
        
        if (user.isPresent()) {
            res.user = new AuthUser(user.get());
            res.token = "";
        }

        return res;
    }

}
