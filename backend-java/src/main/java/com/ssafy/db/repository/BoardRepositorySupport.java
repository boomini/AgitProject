package com.ssafy.db.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import com.ssafy.db.entity.QBoard;
import com.ssafy.db.entity.Board;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class BoardRepositorySupport {
    @Autowired
    private JPAQueryFactory jpaQueryFactory;
    QBoard qBoard = QBoard.board;

    public Optional<Board> findBoardById(Long id){
        Board board = jpaQueryFactory.select(qBoard).from(qBoard)
                .where(qBoard.id.eq(id)).fetchOne();
        if(board == null) return Optional.empty();
        return Optional.ofNullable(board);
    }
}
