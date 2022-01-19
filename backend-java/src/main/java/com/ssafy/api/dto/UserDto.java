package com.ssafy.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.api.response.UserRes;
import com.ssafy.db.entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Getter
@Setter
@ToString
@NoArgsConstructor
public class UserDto {
    Long id;
    String userId;
    String name;
    String nickName;
    int year;
    int month;
    int day;
    LocalDateTime cDate;
    String password;

    public User toEntity(){
        User build = User.builder()
                .userId(userId)
                .name(name)
                .nickName(nickName)
                .year(year)
                .month(month)
                .day(day)
                .password(password)
                .build();
        return build;
    }

    @Builder
    public UserDto(Long id, String userId, String name, String nickName, int year, int month, int day, LocalDateTime cDate, String password){
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.nickName = nickName;
        this.year = year;
        this.month = month;
        this.day = day;
        this.cDate = cDate;
        this.password = password;
    }

    @Builder
    public UserDto(User user){
        this.id = user.getId();
        this.userId = user.getUserId();
        this.name = user.getName();
        this.nickName = user.getNickName();
        this.year = user.getYear();
        this.month = user.getMonth();
        this.day = user.getDay();
        this.cDate = user.getCDate();
        this.password = user.getPassword();
    }
}
