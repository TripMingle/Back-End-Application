package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.posting.DeletePostingCommentReqDTO;
import com.example.tripmingle.dto.req.posting.PatchPostingCommentReqDTO;
import com.example.tripmingle.dto.req.posting.PostPostingCommentReqDTO;
import com.example.tripmingle.dto.res.posting.GetOnePostingResDTO;

public interface PostingCommentUseCase {
	GetOnePostingResDTO createPostingComment(PostPostingCommentReqDTO postPostingCommentReqDTO);

	GetOnePostingResDTO updatePostingComment(PatchPostingCommentReqDTO patchPostingCommentReqDTO);

	GetOnePostingResDTO deletePostingComment(DeletePostingCommentReqDTO deletePostingCommentReqDTO);
}
