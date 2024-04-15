package com.shangwa.auth.lib;

public class AuthPayload {
    public String token;
    public String message = "";

    public AuthPayload(String token, String message) {
        this.token = token;
    }

}

