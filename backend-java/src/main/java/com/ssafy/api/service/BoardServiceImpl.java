package com.ssafy.api.service;

import com.ssafy.api.dto.BoardDto;
import com.ssafy.db.entity.Board;
import com.ssafy.db.repository.BoardRepository;
import com.ssafy.db.repository.BoardRepositorySupport;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service("boardService")
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardRepositorySupport boardRepositorySupport;


    @Override
    @Transactional
    public List<BoardDto> getBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for(Board board : boardList){
            BoardDto boardDto = new BoardDto();
            boardDto.setTitle(board.getTitle());
            boardDto.setContent(board.getContent());
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }

    @Override
    public Board addBoard(BoardDto boardDto) {
        Board board = new Board();

        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());

        return boardRepository.save(board);
    }
}
