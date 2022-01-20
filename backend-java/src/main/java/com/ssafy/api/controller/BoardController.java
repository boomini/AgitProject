package com.ssafy.api.controller;

import com.ssafy.api.advice.exception.CUserDuplicateException;
import com.ssafy.api.dto.BoardDto;
import com.ssafy.api.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Board;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;

import java.util.List;


@Api(value = "게시판 API", tags = {"Board"})
@RestController
@RequestMapping("/api/v1/board")
public class BoardController {

    @Autowired
    BoardService boardService;

    @PostMapping()
    @ApiOperation(value = "게시글 작성", notes = "<strong>글 제목과 글 내용</strong>를 통해 회원가입 한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "작성 오류"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<? extends BaseResponseBody> addBoard (
            @RequestBody @ApiParam(value="글 작성 정보", required = true) BoardDto boardDto) throws Exception {

        //임의로 리턴된 User 인스턴스. 현재 코드는 회원 가입 성공 여부만 판단하기 때문에 굳이 Insert 된 유저 정보를 응답하지 않음

        Board board = boardService.addBoard(boardDto);
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "Success"));
    }

    @GetMapping()
    @ApiOperation(value = "전체 게시글 조회", notes = "")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
            @ApiResponse(code = 401, message = "작성 오류"),
            @ApiResponse(code = 500, message = "서버 오류")
    })
    public ResponseEntity<List<BoardDto>> getList(){
        List<BoardDto> boardDtoList = boardService.getBoardList();

        return ResponseEntity.status(200).body(boardDtoList);
    }


}
