package com.ssafy.api.request;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Date;

/**
 * 유저 회원가입 API ([POST] /api/v1/users) 요청에 필요한 리퀘스트 바디 정의.
 */
@Getter
@Setter
@ApiModel("UserRegisterPostRequest")
public class UserRegisterPostReq {
	@ApiModelProperty(name="유저 ID", example="ssafy_web")
	String userId;
	@ApiModelProperty(name="유저 Password", example="your_password")
	String password;
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
