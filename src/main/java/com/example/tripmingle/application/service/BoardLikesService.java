package com.example.tripmingle.application.service;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardBookMark;
import com.example.tripmingle.entity.BoardLikes;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.BoardLikesPersistPort;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardLikesService {
    private final UserPersistPort userPersistPort;
    private final BoardLikesPersistPort boardLikesPersistPort;
    public List<BoardLikes> getMyLikedBoards(User currentUser) {
        return boardLikesPersistPort.findBoardLikesByUser(currentUser);
    }

    public void toggleBoardLikes(Board board) {
        User currentUser = userPersistPort.findCurrentUserByEmail();
        if(boardLikesPersistPort.existsBoardBookMarkByUserAndBoard(currentUser,board)){
            boardLikesPersistPort.findByUserAndBoard(currentUser,board).toggleBoardLikes();
        }
        else{
            BoardLikes boardLikes = BoardLikes.builder()
                    .user(currentUser)
                    .board(board)
                    .isActive(true)
                    .build();
            boardLikesPersistPort.saveBoardLikes(boardLikes);
        }
    }
}
