package com.example.tripmingle.port.out;

public interface BoardCommentPersistPort {
    void getBoardCommentsByBoardId();

    void saveBoardComment();

    void deleteBoardCommentById();

    void getBoardCommentsById();
}
