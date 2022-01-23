package com.ssafy.api.dto;


import com.ssafy.db.entity.Article;
import io.swagger.annotations.ApiModel;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel("ArticleDto")
public class ArticleDto {
    Long id;
    String title;
    String content;
    String writer;
    LocalDateTime createdTime;
    LocalDateTime updatedDate;

    public Article toEntity(){
        Article build = Article.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
        return build;
    }

    @Builder
    public ArticleDto(Article article){
        this.id = article.getId();
        this.title = article.getTitle();
        this.content = article.getContent();
        this.writer = article.getWriter();
        this.createdTime = article.getCreatedDate();
        this.updatedDate = article.getUpdatedDate();
    }
}
