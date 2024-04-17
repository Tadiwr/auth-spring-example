package com.shangwa.auth.lib;

/** Defines the data sent back to the user when they successfully login */
public class AuthPayload {
    public String token;
    public String message = "";

    public AuthPayload(String token, String message) {
        this.token = token;
    }

}

