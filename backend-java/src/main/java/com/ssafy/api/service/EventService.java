package com.ssafy.api.service;

import com.ssafy.api.dto.EventDto;

public interface EventService {
    boolean addEvent(EventDto eventDto, Long teamId);
}
