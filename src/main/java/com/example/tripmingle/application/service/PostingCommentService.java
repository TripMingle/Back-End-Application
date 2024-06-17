package com.example.tripmingle.application.service;

import com.example.tripmingle.dto.res.GetOnePostingCoCommentResDTO;
import com.example.tripmingle.dto.res.GetOnePostingCommentsResDTO;
import com.example.tripmingle.entity.PostingComment;
import com.example.tripmingle.port.out.PostingCommentPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostingCommentService {

    private final PostingCommentPersistPort postingCommentPersistPort;

    public List<GetOnePostingCommentsResDTO> getPostingComments(Long postingId) {
        List<PostingComment> postingComments = postingCommentPersistPort.getPostingCommentsByPostingId(postingId);
        return postingComments.stream().filter(filter -> filter.getPostingComment() == null)
                .map(comments -> GetOnePostingCommentsResDTO.builder()
                        .commentId(comments.getId())
                        .userNickName(comments.getUser().getNickName())
                        .comment(comments.getContent())
                        .postingCoComment(postingComments.stream()
                                .filter(filter -> filter.getPostingComment() != null && filter.getPostingComment().getId().equals(comments.getId()))
                                .map(cocomments -> GetOnePostingCoCommentResDTO.builder()
                                        .coCommentId(cocomments.getId())
                                        .parentCommentId(comments.getId())
                                        .userNickName(cocomments.getUser().getNickName())
                                        .coComment(cocomments.getContent())
                                        .build()).collect(Collectors.toList()))
                        .build()).toList();
    }
}
