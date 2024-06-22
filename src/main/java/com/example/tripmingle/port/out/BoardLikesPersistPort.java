package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardLikes;
import com.example.tripmingle.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardLikesPersistPort {
    boolean existsBoardBookMarkByUserAndBoard(User currentUser, Board board);

    BoardLikes findByUserAndBoard(User currentUser, Board board);

    void saveBoardLikes(BoardLikes boardLikes);

    Page<BoardLikes> findBoardLikesByUser(User user, Pageable pageable);

    List<BoardLikes> findBoardLikesByBoardId(Long boardId);
}
