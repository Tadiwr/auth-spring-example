package com.shangwa.auth.repository;


import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.shangwa.auth.entity.User;

@Repository
public interface UsersReposity extends CrudRepository<User, Long> {
    
}
