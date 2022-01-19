package com.ssafy.api.controller;


import com.ssafy.api.advice.exception.CBoardNotFoundException;
import com.ssafy.api.dto.BoardDto;
import com.ssafy.api.service.BoardService;
import com.ssafy.common.model.response.BaseResponseBody;
import com.ssafy.db.entity.Board;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;




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
    })
    public ResponseEntity<List<BoardDto>> getList(){
        List<BoardDto> boardDtoList = boardService.getBoardList();

        return ResponseEntity.status(200).body(boardDtoList);
    }

    @GetMapping("/{id}")
    @ApiOperation(value = "특정 게시글 조회", notes = "게시글 id 이용하여 조회")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<BoardDto> getBoardDetail(@ApiParam(value = "id", required = true) @PathVariable("id") Long id){
        Board board = boardService.getBoardDetailById(id);
        // 수정하려는 글이 없으면 예외 처리
        if(board.getId() == null){
            throw new CBoardNotFoundException();
        }
        BoardDto boardDto = new BoardDto(board);
        return ResponseEntity.status(200).body(boardDto);
    }

    @PutMapping("/{id}")
    @ApiOperation(value = "게시글 수정", notes = "게시글 id 이용하여 조회 후 수정")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends  BaseResponseBody> updateBoardDetail(@ApiParam(value = "id", required = true) @PathVariable("id") Long id, BoardDto boardDto){
        if(!boardService.updateBoardDetailById(id, boardDto)){
            // 수정하려는 글이 없으면 예외처리
            throw new CBoardNotFoundException();
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "수정 성공"));
    }

    @DeleteMapping("/{id}")
    @ApiOperation(value = "게시글 삭제", notes = "게시글 id 이용하여 삭제")
    @ApiResponses({
            @ApiResponse(code = 200, message = "성공"),
    })
    public ResponseEntity<? extends BaseResponseBody> deleteBoardDetail(@ApiParam(value = "id", required = true) @PathVariable("id") Long id){
        if(!boardService.deleteBoardDetailById(id)){
            throw new CBoardNotFoundException();
        }
        return ResponseEntity.status(200).body(BaseResponseBody.of(200, "삭제 성공"));
    }
}
