package com.ssafy.api.service;

import com.ssafy.api.dto.TeamDto;

import com.ssafy.api.dto.UserDto;
import com.ssafy.db.entity.User;
import com.ssafy.db.entity.UserTeam;
import com.ssafy.db.repository.*;
import org.checkerframework.checker.nullness.Opt;
import org.checkerframework.checker.units.qual.A;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Team;

import javax.swing.text.html.Option;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("teamService")
public class TeamServiceImpl implements TeamService{
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamRepositorySupport teamRepositorySupport;

    @Autowired
    UserRepository userRepository;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    UserTeamRepository userTeamRepository;

    @Autowired
    UserTeamRepositroySupport userTeamRepositroySupport;

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
        userTeamRepository.save(userTeam);
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

    @Override
    public List<UserDto> userListInTeam(Long teamId) {
        List<Long> userFKs = userTeamRepositroySupport.findUserFKListByTeamId(teamId).get();
        List<UserDto> userDtoList = new ArrayList<>();

        for(Long userFk : userFKs){
            User user = userRepository.findById(userFk).get();
            UserDto userDto = new UserDto(user);
            userDtoList.add(userDto);
        }

        return userDtoList;
    }

    @Override
    public List<UserDto> userListInTeam2(Long teamId) {
        List<User> userList = userTeamRepositroySupport.findUserListByTeamId(teamId).get();
        List<UserDto> userDtoList = new ArrayList<>();

        for(User user : userList){
            UserDto userDto = new UserDto(user);
            userDtoList.add(userDto);
        }

        return userDtoList;
    }

}
