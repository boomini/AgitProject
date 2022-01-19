package com.ssafy.api.service;

import com.ssafy.api.dto.BoardDto;
import com.ssafy.db.entity.Board;
import com.ssafy.db.entity.User;
import com.ssafy.db.repository.BoardRepository;
import com.ssafy.db.repository.BoardRepositorySupport;
import com.ssafy.db.repository.UserRepositorySupport;
import org.checkerframework.checker.nullness.Opt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service("boardService")
public class BoardServiceImpl implements BoardService {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    UserRepositorySupport userRepositorySupport;

    @Autowired
    BoardRepositorySupport boardRepositorySupport;


    @Override
    @Transactional
    public List<BoardDto> getBoardList() {
        List<Board> boardList = boardRepository.findAll();
        List<BoardDto> boardDtoList = new ArrayList<>();

        for(Board board : boardList){
            BoardDto boardDto = new BoardDto(board);
            boardDtoList.add(boardDto);
        }
        return boardDtoList;
    }

    @Override
    public Board addBoard(BoardDto boardDto) {
        Board board = boardDto.toEntity();
        Optional<User> user = userRepositorySupport.findUserByUserId(board.getWriter());
        board.setUser(user.get());
        return boardRepository.save(board);
    }

    @Override
    public Board getBoardDetailById(Long id) {
        Optional<Board> boardOpt = boardRepositorySupport.findBoardById(id);
        Board board = new Board();
        if(boardOpt.isPresent()){
            board = boardOpt.get();
        }
        return board;
    }

    @Override
    public boolean updateBoardDetailById(Long id, BoardDto boardDto) {
        Board board = getBoardDetailById(id);
        if(board == null){
            return false;
        }
        board.setTitle(boardDto.getTitle());
        board.setContent(boardDto.getContent());
        boardRepository.save(board);
        return true;
    }

    @Override
    public boolean deleteBoardDetailById(Long id) {
        Board board = getBoardDetailById(id);
        if(board == null){
            return false;
        }
        boardRepository.delete(board);
        return true;
    }
}
