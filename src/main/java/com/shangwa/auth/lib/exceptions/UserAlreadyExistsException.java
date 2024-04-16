package com.shangwa.auth.lib.exceptions;

public class UserAlreadyExistsException extends Exception {
    
    @Override
    public String getMessage() {
        return "A user with the same email already exists";
    }

}
