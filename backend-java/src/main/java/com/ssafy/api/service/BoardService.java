package com.ssafy.api.service;

import com.ssafy.api.dto.BoardDto;
import com.ssafy.db.entity.Board;

import java.util.List;

public interface BoardService {
    List<BoardDto> getBoardList();
    Board addBoard(BoardDto boardDto);
//    Board getBoardDetailById(Long id);
//    Board updateBoardDetailById(Long id);
//    Board deleteBoardDetailById(Long id);
}
