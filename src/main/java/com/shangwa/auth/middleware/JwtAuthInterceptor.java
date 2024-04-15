package com.shangwa.auth.middleware;

import org.springframework.lang.NonNull;
import org.springframework.web.servlet.HandlerInterceptor;

import com.shangwa.auth.entity.User;
import com.shangwa.auth.lib.exceptions.BadAuthorizationHeader;
import com.shangwa.auth.service.JwtService;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class JwtAuthInterceptor implements HandlerInterceptor {

    private JwtService jwtService;

    public JwtAuthInterceptor(JwtService jwtService2) {
        this.jwtService = jwtService2;
    }

    @Override
    public boolean preHandle( @NonNull HttpServletRequest request , @NonNull HttpServletResponse response, @NonNull Object handler) {

        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer X ")) {
            response.setStatus(401); // Return Unauthorized http code
            return false;
        }

        String token = authorization.substring(9);
        
        try {
            User loggedInUser = jwtService.verifyToken(token);
            request.setAttribute("user", loggedInUser);
            return true;
        } catch(BadAuthorizationHeader e) {
            response.setStatus(401); 
            return false;
        }

    }

}
