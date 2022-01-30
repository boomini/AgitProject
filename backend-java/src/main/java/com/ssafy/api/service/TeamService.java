package com.ssafy.api.service;

import com.ssafy.api.dto.TeamDto;
import com.ssafy.db.entity.Team;

public interface TeamService {
    boolean createTeam(TeamDto teamDto, String userId);
}
