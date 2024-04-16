package com.shangwa.auth.controller;

import org.springframework.web.bind.annotation.RestController;

import com.shangwa.auth.entity.User;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class TestApiController {
    
    @GetMapping("/api/getUserInfo")
    public User getMethodName(HttpServletRequest request) {
        User user = (User) request.getAttribute("user");

        // User user = new User();
        // user.setName("Tanatswa Shangwa");
        // user.setEmail("tana@gmail.com");
        // user.setId(Long.parseLong("56"));
        return user;
    }
    

}
