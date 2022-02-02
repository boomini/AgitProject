package com.ssafy.api.controller;


import com.ssafy.api.dto.EventDto;
import com.ssafy.api.service.EventService;
import com.ssafy.common.model.response.BaseResponseBody;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Api(value = "이벤트 API", tags = {"Event"})
@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    @Autowired
    EventService eventService;

    @PostMapping("/{teamId}")
    @ApiOperation(value = "일정 작성", notes = "특정 팀에서 일정등록을 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> addEvent(@RequestParam(value="eventDate")String eventDate, @RequestParam(value="eventTitle")String eventTitle,
                                                               @RequestParam(value="eventContent")String eventContent, @ApiParam(value = "teamId", required = true) @PathVariable("teamId") Long teamId) throws Exception{
        EventDto eventDto = new EventDto();
        eventDto.setEventTitle(eventTitle);
        eventDto.setEventContent(eventContent);
        eventDto.setEventDate(LocalDate.parse(eventDate));
        eventService.addEvent(eventDto, teamId);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
    }
}
