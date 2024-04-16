package com.shangwa.auth.service.implimentations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shangwa.auth.entity.User;
import com.shangwa.auth.lib.AuthPayload;
import com.shangwa.auth.lib.LoginCredidentials;
import com.shangwa.auth.lib.exceptions.UnAuthorisedException;
import com.shangwa.auth.service.UserService;
import com.shangwa.auth.service.interfaces.TokenAuthService;
import com.shangwa.auth.service.interfaces.TokenUtilService;

@Service
public class TokenAuthServiceImpl implements TokenAuthService {
    
    @Autowired
    private UserService userService;

    @Autowired
    private TokenUtilService tokenService;

    public AuthPayload createUser(User user) {
        userService.addUser(user);
        String token = tokenService.issueToken(user);

        return new AuthPayload(token, "Account created successfully");
    }

    public AuthPayload login(LoginCredidentials creds) {
        AuthPayload res = new AuthPayload(null, "Invalid credidentials");
        Optional<User> user =userService.getUser(creds.email, creds.password);
        
        if (user.isPresent()) {
            res.token = tokenService.issueToken(user.get());
            res.message = "Logged in successfuly";
        }

        return res;
    }

    public User verifyRequest(String authToken) throws UnAuthorisedException {
        return tokenService.verifyToken(authToken);
    }

}
