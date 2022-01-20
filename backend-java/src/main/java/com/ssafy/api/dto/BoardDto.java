package com.ssafy.api.dto;

import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.User;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@ApiModel("BoardDto")
public class BoardDto {
    @ApiModelProperty(name="글제목", example="title")
    String title;
    @ApiModelProperty(name="글내용", example="content")
    String content;


}
