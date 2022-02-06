package com.ssafy.api.service;

import com.ssafy.api.dto.TeamDto;
import com.ssafy.api.dto.UserDto;

import java.util.List;

public interface TeamService {
    boolean createTeam(TeamDto teamDto, String userId);
    boolean addMember(Long teamId, String userId);
    List<UserDto> userListInTeam(Long teamId);
    List<UserDto> userListInTeam2(Long teamId);

    TeamDto getTeamById(Long teamId);
}
