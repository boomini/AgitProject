package com.ssafy.api.controller;


import com.ssafy.api.advice.exception.CTokenForbiddenException;
import com.ssafy.api.dto.EventDto;
import com.ssafy.api.dto.EventResDto;
import com.ssafy.api.dto.UserDto;
import com.ssafy.api.service.EventService;
import com.ssafy.common.auth.SsafyUserDetails;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Team;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.TeamRepositorySupport;
import com.ssafy.db.repository.UserRepositorySupport;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Api(value = "이벤트 API", tags = {"Event"})
@RestController
@RequestMapping("/api/v1/event")
public class EventController {

    @Autowired
    EventService eventService;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    TeamRepositorySupport teamRepositorySupport;

    @PostMapping("/{teamId}")
    @ApiOperation(value = "일정 작성", notes = "특정 팀에서 일정등록을 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> addTeamEvent(@RequestParam(value="eventDate")String eventDate, @RequestParam(value="eventTitle")String eventTitle,
                                                               @RequestParam(value="eventContent")String eventContent, @ApiParam(value = "teamId", required = true) @PathVariable("teamId") Long teamId) throws Exception{
        EventDto eventDto = new EventDto();
        Team team = teamRepositorySupport.findTeamByTeamId(teamId).get();

        eventDto.setEventTitle(eventTitle);
        eventDto.setEventContent(eventContent);
        eventDto.setTeamName(team.getTeamName());
        eventDto.setEventDate(LocalDate.parse(eventDate));
        eventService.addTeamEvent(eventDto, teamId);

        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "success"));
    }

    @PostMapping("/user")
    @ApiOperation(value = "User와 연관된 모든 일정 조회", notes = "userId (PK값)를 통해 조회.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<List<EventDto>> getUsersEventList(@ApiIgnore Authentication authentication){
        List<EventDto> eventDtoList;

        try{
            SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
            Long userId = userDetails.getUser().getId();
            eventDtoList = eventService.getUsersEventList(userId);
        }catch(Exception e){
            //잘못된 접근일때
            throw new CTokenForbiddenException();
        }

        return ResponseEntity.status(200).body(eventDtoList);
    }


    @PostMapping("/user/test")
    @ApiOperation(value = "User와 연관된 모든 일정 조회", notes = "userId (PK값)를 통해 조회.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<List<EventResDto>> getUsersEventListTest(@ApiIgnore Authentication authentication){
        List<EventResDto> eventResDtoList;

        try{
            SsafyUserDetails userDetails = (SsafyUserDetails)authentication.getDetails();
            Long userId = userDetails.getUser().getId();
            eventResDtoList = eventService.getUserEventListTest(userId);
        }catch(Exception e){
            //잘못된 접근일때
            throw new CTokenForbiddenException();
        }

        return ResponseEntity.status(200).body(eventResDtoList);
    }

}
