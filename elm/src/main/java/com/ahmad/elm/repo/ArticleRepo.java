package com.ahmad.elm.repo;

import com.ahmad.elm.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepo extends JpaRepository<Article, Integer> {

}
