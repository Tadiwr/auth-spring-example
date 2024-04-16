package com.shangwa.auth.service.interfaces;

import java.util.Optional;

import com.shangwa.auth.entity.User;
import com.shangwa.auth.lib.exceptions.UserAlreadyExistsException;

public interface UserService {

    public User addUser(User user) throws UserAlreadyExistsException;
    public Optional<User> getUser(String email, String password);
    public boolean userExists(String email);
    
}
