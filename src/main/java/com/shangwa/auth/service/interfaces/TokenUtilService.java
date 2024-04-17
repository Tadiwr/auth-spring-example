package com.shangwa.auth.service.interfaces;

import org.springframework.stereotype.Service;

import com.shangwa.auth.entity.User;
import com.shangwa.auth.lib.exceptions.UnAuthorisedException;

import io.jsonwebtoken.Claims;

/** Defines an interface for the functions of A Token Service including
 *- issueing a token or generating one
 *- verifying a token
 *- getting the bearer of a token
 */

 @Service
public interface TokenUtilService {
    /** Generates a JWT token for a user */
    public String issueToken(User user);

    /** Gets the User who is the bearer of the token based on the claims in the JWT payload
     * 
     * @returns User
     */
    public User getTokenBearer(Claims claims);

    /** Attempts to verify a token and return the `User` bearing the token
     * @throws UnAuthorisedException if the token is missing or is invalid
     */
    public User verifyToken(String token) throws UnAuthorisedException;
}
