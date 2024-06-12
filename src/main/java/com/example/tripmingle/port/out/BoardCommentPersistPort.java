package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.BoardComment;

import java.util.List;

public interface BoardCommentPersistPort {
    List<BoardComment> getBoardCommentsByBoardId(Long boardId);

    void saveBoardComment();

    void deleteBoardCommentById();

    void getBoardCommentsById();
}
