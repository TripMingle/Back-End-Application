package com.example.tripmingle.application.service;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardBookMark;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.BoardBookMarkPersistPort;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardBookMarkService {
    private final UserPersistPort userPersistPort;
    private final BoardBookMarkPersistPort boardBookMarkPersistPort;
    public boolean toggleBoardBookMark(Board board) {
        User currentUser = userPersistPort.findCurrentUserByEmail();
        BoardBookMark boardBookMark;
        if(boardBookMarkPersistPort.existsBoardBookMarkByUserAndBoard(currentUser,board)){
            boardBookMark = boardBookMarkPersistPort.findByUserAndBoard(currentUser,board);
            boardBookMark.toggleBoardBookMark();
        }
        else{
            boardBookMark = BoardBookMark.builder()
                    .user(currentUser)
                    .board(board)
                    .isActive(true)
                    .build();
            boardBookMarkPersistPort.saveBoardBookMark(boardBookMark);
        }
        return boardBookMark.isActive();
    }


    public List<BoardBookMark> getMyBookMarkedBoards(User user) {
        return boardBookMarkPersistPort.getBoardBookMarksByUser(user);
    }

    public boolean isBookMarkedBoard(User currentUser, Board board) {
        if(boardBookMarkPersistPort.existsBoardBookMarkByUserAndBoard(currentUser,board)){
            return boardBookMarkPersistPort.findByUserAndBoard(currentUser,board).isActive();
        }
        else return false;
    }
}