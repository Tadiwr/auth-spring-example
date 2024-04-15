package com.shangwa.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.shangwa.auth.entity.User;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@SpringBootApplication
public class AuthApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthApplication.class, args);
	}

	@GetMapping("/api/getUserInfo")
    public User getStuff(HttpServletRequest request) {
		User user = (User) request.getAttribute("user");

		return user;
    }


}
