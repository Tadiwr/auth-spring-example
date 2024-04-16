package com.shangwa.auth.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.shangwa.auth.entity.User;
import com.shangwa.auth.lib.AuthPayload;
import com.shangwa.auth.lib.LoginCredidentials;
import com.shangwa.auth.lib.Validator;
import com.shangwa.auth.lib.exceptions.UnAuthorisedException;
import com.shangwa.auth.lib.exceptions.UserAlreadyExistsException;
import com.shangwa.auth.service.implimentations.TokenAuthServiceImpl;
import com.shangwa.auth.service.interfaces.TokenAuthService;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.ResponseEntity.HeadersBuilder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    @Autowired
    private TokenAuthService auth;

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

        AuthPayload payload = new AuthPayload(null, null);

        ValidationResult validationRes = validationHelper(user);

        if (!validationRes.passed) {
            payload.message = validationRes.message;
            return ResponseEntity.internalServerError().body(payload);
        }

        try {
            payload = auth.createUser(user);
        } catch (Exception e) {

            if (e instanceof UserAlreadyExistsException) {
                payload.message = e.getMessage();
                return ResponseEntity.badRequest().body(payload);
            }

            payload.message = "An Error occured";
            return ResponseEntity.internalServerError().body(payload);

        }
        

        return ResponseEntity.ok(payload);
    }

    @GetMapping("/getUserInfo")
    public Object getStuff(HttpServletRequest request) throws UnAuthorisedException {
        String authorization = request.getHeader("Authorization");

        if (authorization == null || !authorization.startsWith("Bearer ")) {
            return new ResponseEntity<>("UnAuthorised Request", HttpStatus.UNAUTHORIZED);
        }

        String token = authorization.substring(7); // Removes the 'Bearer' string

        return auth.verifyRequest(token);
    }

    private ValidationResult validationHelper(User user) {
        ValidationResult res = new ValidationResult(true, "");

        if (!user.precenceCheck()) {
            res.message = "One of the fields has an null value or empty string";
            res.passed = false;
            return res;
        }

        if (!Validator.validateEmail(user.getEmail())) {
            res.message = "Invalid Email";
            res.passed = false;
            return res;
        }

        return res;
    }
    

}

class AuthReqHeaders {
    public String token;

    AuthReqHeaders(String token) {
        this.token = token;
    }
}

class ValidationResult {
    boolean passed;
    String message;

    ValidationResult(boolean passed, String message) {
        this.passed = passed;
        this.message = message;
    }

}



