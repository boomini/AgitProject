package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


@Repository
public class UserTeamRepositroySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QUserTeam qUserTeam = QUserTeam.userTeam;
    QUser qUser = QUser.user;
    QTeam qTeam = QTeam.team;

    // 특정 유저가 가입된 팀 리스트
    public Optional<List<Long>> findTeamFKListByUserId(Long id){
        List<Long> teamFKList = jpaQueryFactory.select(qUserTeam.team.id)
                .from(qUserTeam)
                .where(qUserTeam.user.id.eq(id))
                .fetch();

        return Optional.ofNullable(teamFKList);
    }

    // 특정 팀에 가입된 유저 리스트
    public Optional<List<Long>> findUserFKListByTeamId(Long id){
        List<Long> userFKList = jpaQueryFactory.select(qUserTeam.user.id)
                .from(qUserTeam)
                .where(qUserTeam.team.id.eq(id))
                .fetch();

        return Optional.ofNullable(userFKList);
    }

    public Optional<List<User>> findUserListByTeamId(Long id){
        List<User> userList = jpaQueryFactory.select(qUser)
                .from(qUserTeam)
                .join(qUser)
                .on(qUserTeam.user.id.eq(qUser.id))
                .where(qUserTeam.team.id.eq(id))
                .fetch();

        return Optional.ofNullable(userList);
    }


    public Optional<List<Team>> findTeamListByUserId(Long id){
        List<Team> teamList = jpaQueryFactory.select(qTeam)
                .from(qUserTeam)
                .join(qTeam)
                .on(qUserTeam.team.id.eq(qTeam.id))
                .where(qUserTeam.user.id.eq(id))
                .fetch();

        return Optional.ofNullable(teamList);
    }
}
