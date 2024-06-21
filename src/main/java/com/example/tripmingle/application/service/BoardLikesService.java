package com.example.tripmingle.application.service;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardLikes;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.BoardLikesPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class BoardLikesService {
    private final BoardLikesPersistPort boardLikesPersistPort;
    public Page<BoardLikes> getMyLikedBoards(User currentUser, Pageable pageable) {
        return boardLikesPersistPort.findBoardLikesByUser(currentUser, pageable);
    }

    public boolean toggleBoardLikes(Board board, User currentUser) {
        BoardLikes boardLikes;
        if(boardLikesPersistPort.existsBoardBookMarkByUserAndBoard(currentUser,board)){
            boardLikes = boardLikesPersistPort.findByUserAndBoard(currentUser,board);
            boardLikes.toggleBoardLikes();
        }
        else{
            boardLikes = BoardLikes.builder()
                    .user(currentUser)
                    .board(board)
                    .isActive(true)
                    .build();
            boardLikesPersistPort.saveBoardLikes(boardLikes);
        }
        return boardLikes.isActive();
    }


    public boolean isLikedBoard(User currentUser, Board board) {
        if(boardLikesPersistPort.existsBoardBookMarkByUserAndBoard(currentUser,board)){
            return boardLikesPersistPort.findByUserAndBoard(currentUser,board).isActive();
        }
        else return false;
    }

    public void deleteBoardLikesByBoardId(Long boardId) {
        boardLikesPersistPort.findBoardLikesByBoardId(boardId).stream()
                .forEach(boardLikes -> boardLikes.delete());
    }
}
