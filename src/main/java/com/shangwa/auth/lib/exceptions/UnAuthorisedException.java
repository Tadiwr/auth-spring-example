package com.shangwa.auth.lib.exceptions;

public class UnAuthorisedException extends Exception {
    
    @Override
    public String getMessage() {
        return "A Bad Authorization header was passed or no authorization header was provided";
    }
}
