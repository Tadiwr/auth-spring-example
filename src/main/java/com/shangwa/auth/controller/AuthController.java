package com.shangwa.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shangwa.auth.entity.User;
import com.shangwa.auth.lib.AuthPayload;
import com.shangwa.auth.lib.LoginCredidentials;
import com.shangwa.auth.lib.exceptions.BadAuthorizationHeader;
import com.shangwa.auth.service.AuthService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private AuthService auth;

    @PostMapping("/login")
    public ResponseEntity<AuthPayload> login(@RequestBody LoginCredidentials creds) {
        AuthPayload res =  auth.login(creds);

        if (res.token == null) {
            return ResponseEntity.notFound().build();
        } 

        return ResponseEntity.ok(res);
    }

    @PostMapping("/create/account")
    public ResponseEntity<AuthPayload> createAccount(@RequestBody User user) {
        return ResponseEntity.ok(auth.create(user));
    }

    @GetMapping("/getUserInfo")
    public Object getStuff(HttpServletRequest request) throws BadAuthorizationHeader {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer X ")) {
            return new ResponseEntity<>("UnAuthorised Request", HttpStatus.UNAUTHORIZED);
        }

        String token = authorization.substring(9); // Removes the 'Bearer X' string

        return auth.verifyRequest(token);
    }
    

}

class AuthReqHeaders {
    public String token;

    AuthReqHeaders(String token) {
        this.token = token;
    }
}



