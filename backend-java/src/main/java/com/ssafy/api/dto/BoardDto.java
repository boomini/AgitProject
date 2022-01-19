package com.ssafy.api.dto;

import com.ssafy.db.entity.Board;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import java.time.LocalDateTime;


@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel("BoardDto")
public class BoardDto {
    Long id;
    String title;
    String content;
    String writer;
    LocalDateTime createdTime;
    LocalDateTime updatedDate;

    public Board toEntity(){
        Board build = Board.builder()
                .title(title)
                .content(content)
                .writer(writer)
                .build();
        return build;
    }


    @Builder
    public BoardDto(Board board){
        this.id = board.getId();
        this.title = board.getTitle();
        this.content = board.getContent();
        this.writer = board.getWriter();
        this.createdTime = board.getCreatedDate();
        this.updatedDate = board.getUpdatedDate();
    }

}
