package com.shangwa.auth.lib;

import com.shangwa.auth.entity.User;

public class AuthUser {

    public String email = null;
    public String name = null;
    public Long id = null;

    public AuthUser(User user) {

        
        if (user != null) {
            this.email = user.getEmail();
            this.name = user.getName();
            this.id = user.getId();
        }
    }
}