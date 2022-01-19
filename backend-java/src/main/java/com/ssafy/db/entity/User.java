package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;

/**
 * 유저 모델 정의.
 */
@Entity
@Getter
@Setter
@Table(name = "user", uniqueConstraints = {@UniqueConstraint(
        name = "USER_ID_UNIQUE", columnNames = {"userId"}
)})
@EntityListeners(AuditingEntityListener.class)
@DynamicUpdate
public class User extends BaseEntity{
    @Column(columnDefinition="varchar(100)")
    String userId;
    String name;
    String nickName;
    int year;
    int month;
    int day;
    @CreatedDate
    LocalDateTime cDate;

    @JsonIgnore
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    String password;


    public User(){}

    @Builder
    public User(Long id, String userId, String name, String nickName, int year, int month, int day, String password){
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.nickName = nickName;
        this.year = year;
        this.month = month;
        this.day = day;
        this.password = password;
    }



}