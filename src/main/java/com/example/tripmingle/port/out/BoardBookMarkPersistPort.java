package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardBookMark;
import com.example.tripmingle.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface BoardBookMarkPersistPort {

    void saveBoardBookMark(BoardBookMark boardBookMark);

    boolean existsBoardBookMarkByUserAndBoard(User user, Board board);

    BoardBookMark findByUserAndBoard(User user, Board board);

    Page<BoardBookMark> getBoardBookMarksByUser(User currentUser, Pageable pageable);
}
