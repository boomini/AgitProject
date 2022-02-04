package com.ssafy.api.service;

import com.ssafy.api.dto.EventDto;
import com.ssafy.api.dto.EventResDto;
import com.ssafy.db.entity.Event;
import com.ssafy.db.entity.Team;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.*;
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

    @Autowired
    EventRepositorySupport eventRepositorySupport;

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

    @Override
    public List<EventDto> getUsersEventList(Long userId) {

        List<Event> eventList = eventRepositorySupport.findEventsAllByUserID(userId).get();
        List<EventDto> eventDtoList = new ArrayList<>();
        for(Event event : eventList){
            EventDto eventDto = new EventDto(event);
            eventDtoList.add(eventDto);
        }

        return eventDtoList;
    }

    @Override
    public List<EventResDto> getUserEventListTest(Long userId) {
        List<EventResDto> eventResDtoList = eventRepositorySupport.findEventResAllByUserID(userId).get();

        return eventResDtoList;
    }


}
