package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.BoardComment;

import java.util.List;

public interface BoardCommentPersistPort {
    List<BoardComment> getBoardCommentsByBoardId(Long boardId);

    BoardComment saveBoardComment(BoardComment boardComment);

    BoardComment getBoardCommentById(Long boardId);

    List<BoardComment> getBoardCommentByParentBoardId(Long parentBoardId);

    void deleteBoardComment(BoardComment comment);
}
