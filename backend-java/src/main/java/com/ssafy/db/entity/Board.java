package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@EntityListeners(AuditingEntityListener.class)
public class Board extends BaseEntity {
    @Column(columnDefinition="varchar(100)")
    String title;
    String content;
    @CreatedDate
    LocalDateTime createdDate;
    @LastModifiedDate
    LocalDateTime updatedDate;

    @ManyToOne
    @JoinColumn(name = "USER_ID")

    public void assignToUser(User user){
        user.boards.add(this);
        this.user = user;
    }


}
