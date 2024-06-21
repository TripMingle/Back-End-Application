package com.example.tripmingle.application.service;

import com.example.tripmingle.dto.etc.ChildBoardCommentDTO;
import com.example.tripmingle.dto.req.board.CreateBoardCommentReqDTO;
import com.example.tripmingle.dto.req.board.UpdateBoardCommentReqDTO;
import com.example.tripmingle.dto.res.board.ParentBoardCommentResDTO;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardComment;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.BoardCommentPersistPort;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class BoardCommentService {
    private final BoardCommentPersistPort boardCommentPersistPort;

    public List<BoardComment> getBoardCommentsByBoardId(Long boardId) {
        return boardCommentPersistPort.getBoardCommentsByBoardId(boardId);
    }


    public BoardComment createBoardComment(CreateBoardCommentReqDTO createBoardCommentReqDTO, Board board, User currentUser) {
        BoardComment parentBoardComment;
        if (isParent(createBoardCommentReqDTO.getParentBoardCommentId())) {
            parentBoardComment = null;
        } else {
            parentBoardComment = boardCommentPersistPort
                    .getBoardCommentById(createBoardCommentReqDTO.getParentBoardCommentId());
        }

        board.updateCommentCount(1);

        return boardCommentPersistPort.saveBoardComment(BoardComment.builder()
                .parentBoardComment(parentBoardComment)
                .user(currentUser)
                .board(board)
                .content(createBoardCommentReqDTO.getContent())
                .build());
    }

    private boolean isParent(Long id) {
        if (id == -1) return true;
        else return false;
    }

    public int deleteBoardComment(Long commentId) {
        int commentCount = 0;
        BoardComment boardComment = boardCommentPersistPort.getBoardCommentById(commentId);
        commentCount++;
        if (boardComment.isParentBoardCommentNull()) {
            List<BoardComment> childBoardComments = boardCommentPersistPort.getBoardCommentByParentBoardId(boardComment.getId());
            commentCount += childBoardComments.size();

            childBoardComments.stream()
                    .forEach(childComment -> boardCommentPersistPort.deleteBoardComment(childComment));

        }
        boardCommentPersistPort.deleteBoardComment(boardComment);

        return commentCount;
    }

    public BoardComment updateBoardComment(UpdateBoardCommentReqDTO updateBoardCommentReqDTO, Long commentId) {
        BoardComment boardComment = boardCommentPersistPort.getBoardCommentById(commentId);
        boardComment.update(updateBoardCommentReqDTO);

        return boardCommentPersistPort.saveBoardComment(boardComment);
    }


    public void deleteBoardCommentByBoardId(Long boardId) {
        List<BoardComment> boardComments = boardCommentPersistPort.getBoardCommentsByBoardId(boardId);
        boardComments.stream()
                .forEach(comment -> boardCommentPersistPort.deleteBoardComment(comment));
    }

    public Board getBoardByCommentId(Long commentId) {
        BoardComment boardComment = boardCommentPersistPort.getBoardCommentById(commentId);
        return boardComment.getBoard();
    }
}
