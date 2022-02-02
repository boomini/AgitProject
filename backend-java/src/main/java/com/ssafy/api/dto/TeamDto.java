package com.ssafy.api.dto;

import com.ssafy.db.entity.Team;
import lombok.*;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
public class TeamDto {
    Long id;
    String teamName; //팀명
    String teamPassword; //인증코드
    LocalDateTime teamCdate; //생성일
    String teamDescription; //한줄소개
    String teamPicture; //팀썸네일
    int teamMember; // 인원수
    String teamBoss; //팀장 이름

    public Team toEntity(){ //직접 프론트 단에서 적히는 정보를 toentity를 이용해서 DB에 넣어준다.
        Team build = Team.builder()
                .teamName(teamName)
                .teamPassword(teamPassword)
                .teamDescription(teamDescription)
                .teamPicture(teamPicture)
                .teamMember(teamMember)
                .teamBoss(teamBoss)
                .build();
        return build;
    }

    @Builder
    public TeamDto(Team team){
        this.id = team.getId();
        this.teamName = team.getTeamName();
        this.teamPassword = team.getTeamPassword();
        this.teamCdate = team.getTeamCdate();
        this.teamDescription = team.getTeamDescription();
        this.teamPicture = team.getTeamPicture();
        this.teamMember = team.getTeamMember();
        this.teamBoss = team.getTeamBoss();
    }

}
