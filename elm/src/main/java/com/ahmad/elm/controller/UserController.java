package com.ahmad.elm.controller;


import com.ahmad.elm.exception.ApiException;
import com.ahmad.elm.model.Article;
import com.ahmad.elm.model.Comment;
import com.ahmad.elm.model.Role;
import com.ahmad.elm.model.User;
import com.ahmad.elm.repo.ArticleRepo;
import com.ahmad.elm.repo.CommentRepo;
import com.ahmad.elm.repo.UserRepo;
import org.hibernate.mapping.Set;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    UserRepo userRepo;
    ArticleRepo articleRepo;
    CommentRepo commentRepo;
    BCryptPasswordEncoder passwordEncoder;

    @Autowired
    public UserController(UserRepo userRepo, ArticleRepo articleRepo, CommentRepo commentRepo, BCryptPasswordEncoder passwordEncoder) {
        this.userRepo = userRepo;
        this.articleRepo = articleRepo;
        this.commentRepo = commentRepo;
        this.passwordEncoder = passwordEncoder;
    }



    @PostMapping("")
    public User register(@Valid @RequestBody User user) {
        try {
            user.setRoles(new Role("USER"));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
            userRepo.save(user);
            return user;
        } catch (Exception e){
            throw new ApiException("enter a valid  input" , HttpStatus.BAD_REQUEST);
        }

    }

    // login and logout in basic HTTP authentication -> https://stackoverflow.com/a/5033081

}
