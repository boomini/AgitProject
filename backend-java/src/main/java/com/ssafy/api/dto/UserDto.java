package com.ssafy.api.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.ssafy.db.entity.User;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.Column;
import java.time.LocalDate;
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

    LocalDate birthDay;
    LocalDateTime cDate;
    String password;

    public User toEntity(){
        User build = User.builder()
                .userId(userId)
                .name(name)
                .nickName(nickName)
                .birthDay(birthDay)
                .password(password)
                .build();
        return build;
    }

    @Builder
    public UserDto(Long id, String userId, String name, String nickName, LocalDate birthDay , LocalDateTime cDate, String password){
        this.id = id;
        this.userId = userId;
        this.name = name;
        this.nickName = nickName;
        this.birthDay = birthDay;
        this.cDate = cDate;
        this.password = password;
    }

    @Builder
    public UserDto(User user){
        this.id = user.getId();
        this.userId = user.getUserId();
        this.name = user.getName();
        this.nickName = user.getNickName();
        this.birthDay = user.getBirthDay();
        this.cDate = user.getCDate();
        this.password = user.getPassword();
    }
}
