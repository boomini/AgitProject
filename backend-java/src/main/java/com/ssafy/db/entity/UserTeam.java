package com.ssafy.db.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Getter
@Setter
@Table(name = "UserTeam")
public class UserTeam extends BaseEntity{

    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

}
