package com.ssafy.api.service;


import com.ssafy.api.dto.ArticleDto;
import com.ssafy.db.entity.Article;

import java.util.List;

public interface ArticleService {

    List<ArticleDto> getArticleList();
    Article addArticle(ArticleDto articleDto);
    Article getArticleDetailById(Long id);
    boolean updateArticleDetailById(Long id, ArticleDto articleDto);
    boolean deleteArticleDetailById(Long id);
}
