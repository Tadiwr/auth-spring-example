package com.shangwa.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shangwa.auth.entity.User;
import com.shangwa.auth.lib.AuthPayload;
import com.shangwa.auth.lib.LoginCredidentials;
import com.shangwa.auth.service.AuthService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@PreAuthorize("permitAll()")
public class AuthController {
    

    @Autowired
    private AuthService auth;

    @PostMapping("/login")
    public ResponseEntity<AuthPayload> login(@RequestBody LoginCredidentials creds) {
        return ResponseEntity.ok(auth.login(creds));
    }

    @PostMapping("/create/account")
    public ResponseEntity<AuthPayload> createAccount(@RequestBody User user) {
        return ResponseEntity.ok(auth.create(user));
    }
    

}



