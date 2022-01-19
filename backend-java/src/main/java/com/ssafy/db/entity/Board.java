package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

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
public class Board extends BaseEntity {
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

    public Board(){}

    @Builder
    public Board(Long id, String title, String content, String writer){
        this.id = id;
        this.title = title;
        this.content = content;
        // 작성자 id
        this.writer = writer;
    }

    // 연관 관계 메소드
    public void setUser(User user){
        this.user = user;
        user.getBoards().add(this);
    }
}
