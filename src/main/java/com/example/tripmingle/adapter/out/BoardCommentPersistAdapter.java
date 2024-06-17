package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.BoardCommentNotFoundException;
import com.example.tripmingle.entity.BoardComment;
import com.example.tripmingle.port.out.BoardCommentPersistPort;
import com.example.tripmingle.repository.BoardCommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.util.List;

@Component
@RequiredArgsConstructor
public class BoardCommentPersistAdapter implements BoardCommentPersistPort {
    private final BoardCommentRepository boardCommentRepository;

    @Override
    public List<BoardComment> getBoardCommentsByBoardId(Long boardId) {
        return boardCommentRepository.findBoardCommentsByBoardIdOOrderByCreatedAtAsc(boardId);
    }

    @Override
    public BoardComment saveBoardComment(BoardComment boardComment) {
        return boardCommentRepository.save(boardComment);
    }

    @Override
    public BoardComment getBoardCommentById(Long boardId) {
        return boardCommentRepository.findById(boardId)
                .orElseThrow(()-> new BoardCommentNotFoundException("board comment not found", ErrorCode.BOARD_COMMENT_NOT_FOUND));
    }

    @Override
    public List<BoardComment> getBoardCommentByParentBoardId(Long parentBoardId) {
        return boardCommentRepository.findBoardCommentsByParentBoardCommentId(parentBoardId);
    }

    @Override
    public void deleteBoardComment(BoardComment comment) {
        comment.delete();
    }


}
