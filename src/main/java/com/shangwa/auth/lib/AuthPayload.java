package com.shangwa.auth.lib;

import com.shangwa.auth.entity.User;

public class AuthPayload {
    public User user;
    public String token;

    public AuthPayload(User user, String token) {
        this.user = user;
        this.token = token;
    }
}