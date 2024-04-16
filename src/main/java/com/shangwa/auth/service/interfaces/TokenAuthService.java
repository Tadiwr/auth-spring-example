package com.shangwa.auth.service.interfaces;

import org.springframework.stereotype.Service;

import com.shangwa.auth.entity.User;
import com.shangwa.auth.lib.AuthPayload;
import com.shangwa.auth.lib.LoginCredidentials;
import com.shangwa.auth.lib.exceptions.UnAuthorisedException;
import com.shangwa.auth.lib.exceptions.UserAlreadyExistsException;

/** Defines a interface for a Authentication service using tokens */
@Service
public interface TokenAuthService {

    /** - Takes in `LoginCredidentials` and returns an `AuthPayload` object*/
    public AuthPayload login(LoginCredidentials creds);

    /** Takes in an token and verifies if it is valid and authentic*/
    public User verifyRequest(String authToken) throws UnAuthorisedException;

    /** creates a user and returns an auth payload */
    public AuthPayload createUser(User user) throws UserAlreadyExistsException;

    
}
