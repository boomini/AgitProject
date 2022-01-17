package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel("UserPatchPostReq")
public class UserPatchPostReq {
    @ApiModelProperty(name="유저 이름", example="your_password")
    String name;
    @ApiModelProperty(name="유저 닉네임", example="your_password")
    String nickName;
    @ApiModelProperty(name="유저 탄신연도", example="your_year")
    int year;
    @ApiModelProperty(name="유저 탄신달", example="your_month")
    int month;
    @ApiModelProperty(name="유저 탄신일", example="your_day")
    int day;
}
