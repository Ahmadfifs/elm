package com.ahmad.elm.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import org.apache.catalina.LifecycleState;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.sql.Blob;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "articles")
public class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Size(max = 100)
    @Column(nullable = false)
    @NotNull(message = "the title should not be null")
    private String title;

    @Size(max = 500)
    @Column(nullable = false)
    @NotNull(message = "the body should not be null")
    private String body;

    @Column(name = "image", length = 500)
    private byte[] image;

    @Column(name = "createdAt", updatable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdAt = new Date();



    private int likes = 0;
    private int dislikes = 0;
    private boolean disabled = false;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REFRESH, CascadeType.DETACH })
    @JoinColumn(name = "user_id")
    @JsonIgnore
    private User author;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Comment> comments = new HashSet<Comment>();


    public Article(String title, String body) {
        this.title = title;
        this.body = body;
    }

    public Article() {
    }

    public Integer getId() {
        return id;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public Date getCreatedAt() {
        return createdAt;
    }


    public int getLikes() {
        return likes;
    }

    public void setLikes() {
        this.likes = this.likes + 1;
    }

    public int getDislikes() {
        return dislikes;
    }

    public void setDislikes() {
        this.dislikes = this.dislikes + 1;
    }

    public boolean isDisabled() {
        return disabled;
    }

    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }


    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public Set<Comment> getComments() {
        return comments;
    }

    public void setComments(Comment comment) {
        this.comments.add(comment);
        comment.setArticle(this);
    }
}
