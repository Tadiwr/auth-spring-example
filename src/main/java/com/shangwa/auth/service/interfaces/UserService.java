package com.shangwa.auth.service.interfaces;

import java.util.Optional;

import com.shangwa.auth.entity.User;

public interface UserService {

    public User addUser(User user);
    public Optional<User> getUser(String email, String password);
    public boolean userExists(String email, String password);
    public void saveUser(User user);
}
