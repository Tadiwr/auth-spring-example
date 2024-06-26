package com.shangwa.auth.service.implimentations;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shangwa.auth.entity.User;
import com.shangwa.auth.repository.UsersReposity;
import com.shangwa.auth.service.interfaces.UserService;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UsersReposity userRepo;

    public User addUser(User user) {
        return userRepo.save(user);
    }

    public Optional<User> getUser(String email, String password) {
        return userRepo.findDistinctByEmailAndPassword(email, password);
    }

    public boolean userExists(String email, String password) {
        return getUser(email, password).isPresent();
    }

    public void saveUser(User user) {
        userRepo.save(user);
    }
}
