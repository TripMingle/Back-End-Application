package com.example.tripmingle.application.service;

import com.example.tripmingle.common.utils.UserUtils;
import com.example.tripmingle.dto.req.posting.PatchPostingCommentReqDTO;
import com.example.tripmingle.dto.req.posting.PostPostingCommentReqDTO;
import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.PostingComment;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.PostingCommentPersistPort;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PostingCommentService {

    private final PostingCommentPersistPort postingCommentPersistPort;
    private final UserPersistPort userPersistPort;
    private final UserUtils userUtils;

    public List<PostingComment> getPostingComments(Long postingId) {
        return postingCommentPersistPort.getPostingCommentsByPostingId(postingId);
    }

    public Long createPostingComment(PostPostingCommentReqDTO postPostingCommentReqDTO, Posting posting) {
        User user = userPersistPort.findCurrentUserByEmail();
        PostingComment parentPostingComment = null;
        if (postPostingCommentReqDTO.getParentCommentId() != -1) {
            parentPostingComment = postingCommentPersistPort.getPostingCommentById(postPostingCommentReqDTO.getParentCommentId());
        }
        PostingComment postingComment = PostingComment.builder()
                .user(user)
                .posting(posting)
                .postingComment(parentPostingComment)
                .comment(postPostingCommentReqDTO.getComment())
                .build();
        Long postingCommentId = postingCommentPersistPort.save(postingComment).getId();
        posting.increasePostingCommentCount();
        return postingCommentId;
    }

    public Long updatePostingComment(PatchPostingCommentReqDTO patchPostingCommentReqDTO) {
        PostingComment postingComment = postingCommentPersistPort.getPostingCommentById(patchPostingCommentReqDTO.getPostingCommentId());
        if (userUtils.validateMasterUser(postingComment.getUser().getId())) {
            postingComment.updateComment(patchPostingCommentReqDTO.getComment());
        }
        return postingComment.getId();
    }

    public Long deletePostingComment(Long commentId) {
        PostingComment postingComment = postingCommentPersistPort.getPostingCommentById(commentId);
        if (userUtils.validateMasterUser(postingComment.getUser().getId())) {
            postingComment.deleteComment();
        }
        postingComment.getPosting().decreasePostingCommentCount();
        return postingComment.getId();
    }
}
