package com.ssafy.api.service;

import com.ssafy.api.dto.TeamDto;

import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserTeam;
import com.ssafy.db.repository.UserRepositorySupport;
import com.ssafy.db.repository.UserTeamRepository;
import org.checkerframework.checker.nullness.Opt;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Team;
import com.ssafy.db.repository.TeamRepository;
import com.ssafy.db.repository.TeamRepositorySupport;

import javax.swing.text.html.Option;
import java.util.Optional;

@Service("teamService")
public class TeamServiceImpl implements TeamService{
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamRepositorySupport teamRepositorySupport;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    UserTeamRepository userTeamRepository;

    @Override
    public boolean createTeam(TeamDto teamDto, String userId) {
        Optional<User> user = userRepositorySupport.findUserByUserId(userId);
        if (user == null){
            return false;
        }
        Team team = teamDto.toEntity();
        System.out.println(team);
        UserTeam userTeam = new UserTeam();
        userTeam.setUser(user.get());
        userTeam.setTeam(team);
        userTeamRepository.save(userTeam);  // 너 누가 마술 부리래
        return true;
    }

    @Override
    public boolean addMember(Long teamId, String userId) {
        Optional<User> user = userRepositorySupport.findUserByUserId(userId);
        Optional<Team> team = teamRepositorySupport.findTeamByTeamId(teamId);
        if(user == null || team == null){
            return false;
        }
        UserTeam userTeam = new UserTeam();
        userTeam.setUser(user.get());
        userTeam.setTeam(team.get());

        userTeamRepository.save(userTeam);
        return true;
    }
}
