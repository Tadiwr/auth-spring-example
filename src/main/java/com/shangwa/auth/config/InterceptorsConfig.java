package com.shangwa.auth.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.NonNull;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.shangwa.auth.middleware.JwtAuthInterceptor;
import com.shangwa.auth.service.implimentations.JwtTokenServiceImpl;
@Configuration
public class InterceptorsConfig implements WebMvcConfigurer {

    @Autowired
    private JwtTokenServiceImpl jwtService;

    @Override
    public void addInterceptors(@NonNull InterceptorRegistry registry) {
        registry.addInterceptor(new JwtAuthInterceptor(jwtService)).addPathPatterns("/api/**");
    }
}
