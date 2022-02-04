package com.ssafy.api.service;

import com.ssafy.api.dto.EventDto;
import com.ssafy.api.dto.EventResDto;

import java.util.List;

public interface EventService {
    boolean addTeamEvent(EventDto eventDto, Long teamId);
    List<EventResDto> getUserEventList(Long userId);
    List<EventResDto> getUserEventListInMonth(Long userId, int month);

}
