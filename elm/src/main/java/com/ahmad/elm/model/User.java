package com.ahmad.elm.model;


import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true, nullable = false)
    @Size(min = 1, message = "please provide a username")
    private String username;

    @Size(min = 10, max = 10)
    @Pattern(regexp = "(^$|[0-9]{10})")
    @Column(nullable = false, unique = true)
    private String mobileNumber;

    @Size(min = 8)
    private String password;

    @Column(unique = true, nullable = false)
    private String email;

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY ,  orphanRemoval = true)
    private Set<Article> articles = new HashSet<Article>();

    @JsonIgnore
    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Comment> comments = new HashSet<Comment>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Role> roles = new HashSet<Role>();


    public User(String username, String mobileNumber, String password, String email) {
        this.username = username;
        this.mobileNumber = mobileNumber;
        this.password = password;
        this.email = email;
    }


    public User() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public Set<Article> getArticles() {
        return articles;
    }

    public void setArticles(Article article) {
        this.articles.add(article);
        article.setAuthor(this);
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Comment comment) {
        this.comments.add(comment);
        comment.setAuthor(this);
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Role role) {
        this.roles.add(role);
        role.setUser(this);
    }
}
