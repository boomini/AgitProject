package com.ssafy.db.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@DynamicUpdate
@EntityListeners(AuditingEntityListener.class)
public class Article extends BaseEntity{
    @Column(columnDefinition="varchar(100)")
    String title;
    String content;
    String writer;

    @CreatedDate
    LocalDateTime createdDate;
    @LastModifiedDate
    LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    public Article(){}

    @Builder
    public Article(Long id, String title, String content, String writer){
        this.id = id;
        this.title = title;
        this.content = content;
        // 작성자 id
        this.writer = writer;
    }
    // 연관 관계 메소드
    public void setUser(User user){
        this.user = user;
        user.getArticles().add(this);
    }

}
