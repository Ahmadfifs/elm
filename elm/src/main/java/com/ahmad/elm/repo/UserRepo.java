package com.ahmad.elm.repo;

import com.ahmad.elm.model.User;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User, Integer> {

    User findByUsername(String username);
}
