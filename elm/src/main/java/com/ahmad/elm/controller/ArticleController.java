package com.ahmad.elm.controller;


import com.ahmad.elm.exception.ApiException;
import com.ahmad.elm.model.Article;
import com.ahmad.elm.model.Comment;
import com.ahmad.elm.model.User;
import com.ahmad.elm.repo.ArticleRepo;
import com.ahmad.elm.repo.CommentRepo;
import com.ahmad.elm.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.security.Principal;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("article")
public class ArticleController {


    private ArticleRepo articleRepo;
    private UserRepo userRepo;
    private CommentRepo commentRepo;



    @Autowired
    public ArticleController(UserRepo userRepo, ArticleRepo articleRepo, CommentRepo commentRepo) {
        this.userRepo = userRepo;
        this.articleRepo = articleRepo;
        this.commentRepo = commentRepo;
    }

    @GetMapping("")
    // TODO: Return the article list with pagination
    public List<Article> getArticles() {
        return articleRepo.findAll().stream().filter(article -> !article.isDisabled()).collect(Collectors.toList());
    }

    @GetMapping("{id}")
    public Article getArticle(@PathVariable int id) {
        if (articleRepo.findById(id).isPresent()) {
            Article article = articleRepo.findById(id).get();
            return article;
        } else {
            throw new ApiException("the id is not correct", HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping("")
    public Article addArticle(@Valid @RequestBody Article article , Principal principal) {
        // get the user context and add it to the article
        User user = userRepo.findByUsername(principal.getName());
        user.setArticles(article);
        userRepo.save(user);
        return article;
    }


    @DeleteMapping("{id}")
    public String deleteArticle(@PathVariable int id , Principal principal) {

        User user = userRepo.findByUsername(principal.getName());

        if(user.getArticles().contains(articleRepo.findById(id).orElseThrow(
                () -> new ApiException("the article not found" , HttpStatus.BAD_REQUEST)))){
        }else {
            throw new ApiException("you dont have the permission to delete this article" , HttpStatus.UNAUTHORIZED);
        }

        // the deletion is not working
        // TODO: figure out what is the problem
        articleRepo.deleteById(id);
        return "delete article " + id;
    }


    // article comment

    @PostMapping("{id}/comment")
    public Comment addComment(@PathVariable int id , @RequestBody Comment comment , Principal principal) {
        // Check if article exist or not
        if (articleRepo.findById(id).isPresent()) {
            User user = userRepo.findByUsername(principal.getName());
            Article article = articleRepo.findById(id).get();
            article.setComments(comment);
            user.setComments(comment);
            // one to many
            userRepo.save(user);
            return comment;
        } else {
            throw new ApiException("the id is not correct", HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping("{id}/comment")
    public Set<Comment> getComment(@PathVariable int id) {

        if (articleRepo.findById(id).isPresent()) {
            Article article = articleRepo.findById(id).get();
            return article.getComments();
        } else {
            throw new ApiException("the id is not correct", HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("{id}/like")
    public Article addLike(@PathVariable int id) {
        if (articleRepo.findById(id).isPresent()) {
            Article article = articleRepo.findById(id).get();
            article.setLikes();
            articleRepo.save(article);
            return article;
        } else {
            throw new ApiException("the id is not correct", HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("{id}/dislike")
    public Article addDislike(@PathVariable int id) {
        if (articleRepo.findById(id).isPresent()) {
            Article article = articleRepo.findById(id).get();
            article.setDislikes();
            articleRepo.save(article);
            return article;
        } else {
            throw new ApiException("the id is not correct", HttpStatus.BAD_REQUEST);
        }
    }


    @PutMapping("{id}/disable")
    public Article Disable(@PathVariable int id) {
        if (articleRepo.findById(id).isPresent()) {
            Article article = articleRepo.findById(id).get();
            article.setDisabled(true);
            articleRepo.save(article);
            return article;
        } else {
            throw new ApiException("the id is not correct", HttpStatus.BAD_REQUEST);
        }
    }



    @PutMapping("{id}/enable")
    public Article enable(@PathVariable int id) {
        if (articleRepo.findById(id).isPresent()) {
            Article article = articleRepo.findById(id).get();
            article.setDisabled(false);
            articleRepo.save(article);
            return article;
        } else {
            throw new ApiException("the id is not correct", HttpStatus.BAD_REQUEST);
        }
    }

}
