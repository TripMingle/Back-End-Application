package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.posting.DeletePostingCommentReqDTO;
import com.example.tripmingle.dto.req.posting.PatchPostingCommentReqDTO;
import com.example.tripmingle.dto.req.posting.PostPostingCommentReqDTO;
import com.example.tripmingle.dto.res.posting.DeletePostingCommentResDTO;
import com.example.tripmingle.dto.res.posting.PatchPostingCommentResDTO;
import com.example.tripmingle.dto.res.posting.PostPostingCommentResDTO;

public interface PostingCommentUseCase {
	PostPostingCommentResDTO createPostingComment(PostPostingCommentReqDTO postPostingCommentReqDTO);

	PatchPostingCommentResDTO updatePostingComment(PatchPostingCommentReqDTO patchPostingCommentReqDTO);

	DeletePostingCommentResDTO deletePostingComment(DeletePostingCommentReqDTO deletePostingCommentReqDTO);
}
