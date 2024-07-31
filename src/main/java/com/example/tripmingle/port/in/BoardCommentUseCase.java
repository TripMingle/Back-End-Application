package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.board.CreateBoardCommentReqDTO;
import com.example.tripmingle.dto.req.board.UpdateBoardCommentReqDTO;
import com.example.tripmingle.dto.res.board.CreateBoardCommentResDTO;
import com.example.tripmingle.dto.res.board.UpdateBoardCommentResDTO;

public interface BoardCommentUseCase {
    void createBoardComment(CreateBoardCommentReqDTO createBoardCommentReqDTO);

    void deleteBoardComment(Long commentId);

    void updateBoardComment(UpdateBoardCommentReqDTO updateBoardCommentReqDTO, Long commentId);
}
