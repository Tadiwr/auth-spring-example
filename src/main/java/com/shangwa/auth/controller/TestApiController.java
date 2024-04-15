package com.shangwa.auth.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shangwa.auth.entity.User;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.GetMapping;

@RestController
public class TestApiController {
    
    @GetMapping("/api/getUserInfo")
    public User getMethodName(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");

        return user;
    }
    

}
