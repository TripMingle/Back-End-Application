package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.board.CreateBoardCommentReqDTO;
import com.example.tripmingle.dto.req.board.UpdateBoardCommentReqDTO;
import com.example.tripmingle.dto.res.board.CreateBoardCommentResDTO;
import com.example.tripmingle.dto.res.board.UpdateBoardCommentResDTO;

public interface BoardCommentUseCase {
    CreateBoardCommentResDTO createBoardComment(CreateBoardCommentReqDTO createBoardCommentReqDTO);

    void deleteBoardComment(Long commentId);

    UpdateBoardCommentResDTO updateBoardComment(UpdateBoardCommentReqDTO updateBoardCommentReqDTO, Long commentId);
}
