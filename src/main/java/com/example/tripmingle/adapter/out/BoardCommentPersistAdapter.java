package com.example.tripmingle.adapter.out;

import com.example.tripmingle.entity.BoardComment;
import com.example.tripmingle.port.out.BoardCommentPersistPort;
import com.example.tripmingle.repository.BoardCommentRepository;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class BoardCommentPersistAdapter implements BoardCommentPersistPort {
    private final BoardCommentRepository boardCommentRepository;
    @Override
    public List<BoardComment> getBoardCommentsByBoardId(Long boardId) {
        return boardCommentRepository.findBoardCommentsByBoardIdOOrderByCreatedAtAsc(boardId);
    }

    @Override
    public void saveBoardComment() {

    }


    @Override
    public void deleteBoardCommentById() {

    }

    @Override
    public void getBoardCommentsById() {

    }


}
