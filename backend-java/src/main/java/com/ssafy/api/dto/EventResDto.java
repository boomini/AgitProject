package com.ssafy.api.dto;

import com.querydsl.core.annotations.QueryProjection;
import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel("EventResDto")
public class EventResDto {

    Long dDay;
    String eventTitle;
    String eventContent;
    String teamName;
    LocalDate eventDate;

    @QueryProjection
    public EventResDto(String eventTitle, String eventContent, String teamName, LocalDate eventDate, Long dDay){
        this.eventTitle = eventTitle;
        this.eventContent = eventContent;
        this.teamName = teamName;
        this.eventDate = eventDate;
        this.dDay = dDay;
    }
}
