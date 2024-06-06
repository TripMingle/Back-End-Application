package com.example.tripmingle.application.Service;

import com.example.tripmingle.port.out.BoardCommentPersistPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class BoardCommentService {
    private final BoardCommentPersistPort boardCommentPersistPort;

    public void getBoardCommentsByBoardId() {
        boardCommentPersistPort.getBoardCommentsByBoardId();
    }

    public void createBoardComment() {
        boardCommentPersistPort.saveBoardComment();
    }

    public void deleteBoardComment() {
        boardCommentPersistPort.deleteBoardCommentById();
    }

    public void getBoardCommentById() {
        boardCommentPersistPort.getBoardCommentsById();
    }

    public void updateBoardComment() {
    }
}
