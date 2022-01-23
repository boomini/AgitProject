package com.ssafy.api.service;

import com.ssafy.api.dto.ArticleDto;
import com.ssafy.db.entity.Article;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleRepository articleRepository;

    @Autowired
    ArticleRepositorySupport articleRepositorySupport;


    @Autowired
    UserRepositorySupport userRepositorySupport;





    @Override
    @Transactional
    public List<ArticleDto> getArticleList() {
        List<Article> articleList = articleRepository.findAll();
        List<ArticleDto> articleDtoList = new ArrayList<>();

        for(Article article : articleList){
            ArticleDto articleDto = new ArticleDto(article);
            articleDtoList.add(articleDto);
        }
        return articleDtoList;
    }

    @Override
    public List<ArticleDto> getUsersArticleList(String userId) {
        List<ArticleDto> articleDtoList = new ArrayList<>();
        User user = userRepositorySupport.findUserByUserId(userId).get();
        List<Article> articles = user.getArticles();
        for(Article article : articles){
            ArticleDto articleDto = new ArticleDto(article);
            articleDtoList.add(articleDto);
        }
        return articleDtoList;
    }

    @Override
    public Article addArticle(ArticleDto articleDto) {
        Article article = articleDto.toEntity();
        Optional<User> user = userRepositorySupport.findUserByUserId(article.getWriter());
        article.setUser(user.get());
        return articleRepository.save(article);
    }

    @Override
    public Article getArticleDetailById(Long id) {
        Optional<Article> articleOpt = articleRepositorySupport.findArticleById(id);
        Article article = new Article();
        if(articleOpt.isPresent()){
            article = articleOpt.get();
        }
        return article;
    }

    @Override
    public boolean updateArticleDetailById(Long id, ArticleDto articleDto) {
        Article article = getArticleDetailById(id);
        if(article == null){
            return false;
        }
        article.setTitle(articleDto.getTitle());
        article.setContent(articleDto.getContent());
        articleRepository.save(article);
        return true;
    }

    @Override
    public boolean deleteArticleDetailById(Long id) {
        Article article = getArticleDetailById(id);
        if(article == null){
            return false;
        }
        articleRepository.delete(article);
        return true;
    }
}
