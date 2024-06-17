package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.CreateBoardCommentReqDTO;
import com.example.tripmingle.dto.req.UpdateBoardCommentReqDTO;
import com.example.tripmingle.dto.res.CreateBoardCommentResDTO;
import com.example.tripmingle.dto.res.UpdateBoardCommentResDTO;

public interface BoardCommentUseCase {
    CreateBoardCommentResDTO createBoardComment(CreateBoardCommentReqDTO createBoardCommentReqDTO);

    void deleteBoardComment(Long commentId);

    UpdateBoardCommentResDTO updateBoardComment(UpdateBoardCommentReqDTO updateBoardCommentReqDTO, Long commentId);
}
