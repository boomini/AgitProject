package com.ssafy.api.service;

import com.ssafy.api.dto.TeamDto;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.ssafy.db.entity.Team;
import com.ssafy.db.repository.TeamRepository;
import com.ssafy.db.repository.TeamRepositorySupport;

@Service("teamService")
public class TeamServiceImpl implements TeamService{
    @Autowired
    TeamRepository teamRepository;

    @Autowired
    TeamRepositorySupport teamRepositorySupport;

    @Override
    public boolean createTeam(TeamDto teamDto) {
        teamRepository.save(teamDto.toEntity()).getId();
        return true;
    }
}
