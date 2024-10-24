package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.posting.PostPostingCommentReqDTO;
import com.example.tripmingle.dto.res.posting.GetOnePostingResDTO;

public interface PostingLockUseCase {

	GetOnePostingResDTO createPostingComment(PostPostingCommentReqDTO postPostingCommentReqDTO);

}
