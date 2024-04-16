package com.shangwa.auth.middleware;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import com.shangwa.auth.entity.User;
import com.shangwa.auth.lib.exceptions.UnAuthorisedException;
import com.shangwa.auth.service.implimentations.JwtTokenServiceImpl;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthInterceptor implements HandlerInterceptor {

    // FIX: Find a way to autowire this instead of passing it as a constructor
    private JwtTokenServiceImpl jwtService;

    public JwtAuthInterceptor(JwtTokenServiceImpl jwtService2) {
        this.jwtService = jwtService2;
    }

    @Override
    public boolean preHandle( @NonNull HttpServletRequest request , @NonNull HttpServletResponse response, @NonNull Object handler) {

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            response.setStatus(401); // Return Unauthorized http code
            return false;
        }

        String token = authorization.substring(7);
        System.out.println(token);
        
        try {

            User loggedInUser = jwtService.verifyToken(token);
            System.out.println(loggedInUser.getName());
            request.setAttribute("user", loggedInUser);

        } catch(UnAuthorisedException e) {
            
            response.setStatus(401); 
            return false;
        }

        return true;

    }

}
