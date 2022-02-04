package com.ssafy.api.dto;

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

}
