package com.ssafy.api.service;

import com.ssafy.api.dto.EventDto;
import com.ssafy.db.entity.Event;
import com.ssafy.db.entity.Team;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.EventRepository;
import com.ssafy.db.repository.TeamRepositorySupport;
import com.ssafy.db.repository.UserRepositorySupport;
import com.ssafy.db.repository.UserTeamRepositroySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("EventService")
public class EventServiceImpl implements EventService{

    @Autowired
    TeamRepositorySupport teamRepositorySupport;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    UserTeamRepositroySupport userTeamRepositroySupport;

    @Autowired
    EventRepository eventRepository;

    @Override
    public boolean addTeamEvent(EventDto eventDto, Long teamId) {
        Event event = eventDto.toEntity();

        Optional<Team> team = teamRepositorySupport.findTeamByTeamId(teamId);
        if (team == null){
            return false;
        }
        event.setTeam(team.get());
        eventRepository.save(event);
        return true;
    }

    // 이중 for문 이부분은 추후 query문으로 수정 해야할 수도
    @Override
    public List<EventDto> getUsersEventList(Long userId) {

        List<Team> teamList = userTeamRepositroySupport.findTeamListByUserId(userId).get();
        List<EventDto> eventDtoList = new ArrayList<>();
        for(Team team : teamList){
            List<Event> eventList = team.getEvents();
            for(Event event : eventList){
                EventDto eventDto = new EventDto(event);
                eventDtoList.add(eventDto);
            }
        }
        return eventDtoList;
    }
}
