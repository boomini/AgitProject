package com.ssafy.db.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Table(name = "team", uniqueConstraints = {@UniqueConstraint(
        name = "TEAM_ID_UNIQUE", columnNames = {"teamId"}
)})
@EntityListeners(AuditingEntityListener.class)
public class Team extends BaseEntity{
    @Column(columnDefinition="varchar(100)")
    int teamId; //팀 아이디
    String teamName; //팀명
    String teamPassword; //인증코드
    @CreatedDate
    LocalDateTime teamCdate; //생성일
    String teamDescription; //한줄소개
    String teamPicture; // 팀 썸네일
    int teamMember; //인원수
    String teamBoss; //팀장 이름

}