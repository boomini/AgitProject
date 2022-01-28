package com.ssafy.db.entity;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;


@Getter
@Setter
@Entity
@IdClass(UserTeam.class)
public class UserTeam {

    @Id
    @ManyToOne
    @JoinColumn(name = "TEAM_ID")
    private Team team;

    @Id
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

}