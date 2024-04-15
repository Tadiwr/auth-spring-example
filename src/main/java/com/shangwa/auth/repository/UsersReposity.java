package com.shangwa.auth.repository;


import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shangwa.auth.entity.User;

@Repository
public interface UsersReposity extends CrudRepository<User, Long> {
    Optional<User> findDistinctByEmailAndPassword(String email, String password);
    List<User> findByEmail(String email);
}
