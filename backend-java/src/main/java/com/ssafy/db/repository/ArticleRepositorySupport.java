package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.QArticle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ArticleRepositorySupport {

    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QArticle qArticle = QArticle.article;

    public Optional<Article> findArticleById(Long id){
        Article article = jpaQueryFactory.select(qArticle).from(qArticle)
                .where(qArticle.id.eq(id)).fetchOne();
        if(article == null) return Optional.empty();
        return Optional.ofNullable(article);
    }

}
