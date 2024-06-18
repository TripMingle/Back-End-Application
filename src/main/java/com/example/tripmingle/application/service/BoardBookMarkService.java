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
    public void toggleBoardBookMark(Board board) {
        User currentUser = userPersistPort.findCurrentUserByEmail();
        if(boardBookMarkPersistPort.existsBoardBookMarkByUserAndBoard(currentUser,board)){
            boardBookMarkPersistPort.findByUserAndBoard(currentUser,board).toggleBoardBookMark();
        }
        else{
            BoardBookMark boardBookMark = BoardBookMark.builder()
                    .user(currentUser)
                    .board(board)
                    .isActive(true)
                    .build();
            boardBookMarkPersistPort.saveBoardBookMark(boardBookMark);
        }
    }


    public List<BoardBookMark> getMyBookMarkedBoards(User user) {
        return boardBookMarkPersistPort.getBoardBookMarksByUser(user);
    }
}
