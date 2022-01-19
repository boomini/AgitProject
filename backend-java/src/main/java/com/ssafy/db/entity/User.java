package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

//    @OneToMany(mappedBy = "user")
//    private List<Board> boards = new ArrayList<>();


}