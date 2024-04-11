package com.shangwa.auth.lib;

import com.shangwa.auth.entity.User;

public class AuthPayload {
    public AuthUser user;
    public String token;

    public AuthPayload(User user, String token) {
        this.user = new AuthUser(user);
        this.token = token;
    }

    public boolean isNull() {
        return user == null || token == null;
    }


}

