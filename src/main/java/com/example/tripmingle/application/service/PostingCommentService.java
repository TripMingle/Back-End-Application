package com.example.tripmingle.application.service;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.error.ErrorResponse;
import com.example.tripmingle.dto.req.PatchPostingCommentReqDTO;
import com.example.tripmingle.dto.req.PostPostingCommentReqDTO;
import com.example.tripmingle.dto.res.GetOnePostingCoCommentResDTO;
import com.example.tripmingle.dto.res.GetOnePostingCommentsResDTO;
import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.PostingComment;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.PostingCommentPersistPort;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostingCommentService {

    private final PostingCommentPersistPort postingCommentPersistPort;
    private final UserPersistPort userPersistPort;

    public List<GetOnePostingCommentsResDTO> getPostingComments(Long postingId) {
        List<PostingComment> postingComments = postingCommentPersistPort.getPostingCommentsByPostingId(postingId);
        return postingComments.stream().filter(filter -> filter.getPostingComment() == null)
                .map(comments -> GetOnePostingCommentsResDTO.builder()
                        .commentId(comments.getId())
                        .userNickName(comments.getUser().getNickName())
                        .comment(comments.getComment())
                        .postingCoComment(postingComments.stream()
                                .filter(filter -> filter.getPostingComment() != null && filter.getPostingComment().getId().equals(comments.getId()))
                                .map(cocomments -> GetOnePostingCoCommentResDTO.builder()
                                        .coCommentId(cocomments.getId())
                                        .parentCommentId(comments.getId())
                                        .userNickName(cocomments.getUser().getNickName())
                                        .coComment(cocomments.getComment())
                                        .build()).collect(Collectors.toList()))
                        .build()).toList();
    }

    public Long createPostingComment(PostPostingCommentReqDTO postPostingCommentReqDTO, Posting posting) {
        User user = userPersistPort.findCurrentUserByEmail();
        PostingComment parentPostingComment = null;
        if (postPostingCommentReqDTO.getParentCommentId() != null) {
            parentPostingComment = postingCommentPersistPort.getPostingCommentById(postPostingCommentReqDTO.getParentCommentId());
        }
        PostingComment postingComment = PostingComment.builder()
                .user(user)
                .posting(posting)
                .postingComment(parentPostingComment)
                .comment(postPostingCommentReqDTO.getComment())
                .build();
        return postingCommentPersistPort.save(postingComment).getId();
    }

    public PostingComment updatePostingComment(PatchPostingCommentReqDTO patchPostingCommentReqDTO) {
        User user = userPersistPort.findCurrentUserByEmail();
        PostingComment postingComment = postingCommentPersistPort.getPostingCommentById(patchPostingCommentReqDTO.getPostingCommentId());
        if (validateCommentMasterUser(postingComment.getUser().getId(), user.getId())) {
            postingComment.updateComment(patchPostingCommentReqDTO.getComment());
        } else {
            throw new ErrorResponse(ErrorCode.POSTING_COMMENT_INVALID_USER);
        }
        return postingComment;
    }

    private boolean validateCommentMasterUser(Long commentUserId, Long userId) {
        return userId.equals(commentUserId);
    }
}
