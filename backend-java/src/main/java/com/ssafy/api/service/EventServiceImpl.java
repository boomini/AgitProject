package com.ssafy.api.service;

import com.ssafy.api.dto.EventDto;
import com.ssafy.db.entity.Event;
import com.ssafy.db.entity.Team;
import com.ssafy.db.repository.EventRepository;
import com.ssafy.db.repository.TeamRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service("EventService")
public class EventServiceImpl implements EventService{

    @Autowired
    TeamRepositorySupport teamRepositorySupport;

    @Autowired
    EventRepository eventRepository;

    @Override
    public boolean addEvent(EventDto eventDto, Long teamId) {
        Event event = eventDto.toEntity();

        Optional<Team> team = teamRepositorySupport.findTeamByTeamId(teamId);
        if (team == null){
            return false;
        }
        event.setTeam(team.get());
        eventRepository.save(event);
        return true;
    }
}
