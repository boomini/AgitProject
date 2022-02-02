package com.ssafy.api.service;

import com.ssafy.api.dto.EventDto;
import com.ssafy.api.dto.EventResDto;

import java.util.List;

public interface EventService {
    boolean addTeamEvent(EventDto eventDto, Long teamId);
    List<EventDto> getUsersEventList(Long userId);
    List<EventResDto> getUserEventListTest(Long userId);

}
