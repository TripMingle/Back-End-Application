package com.example.tripmingle.application.Service;

import com.example.tripmingle.port.out.PostingCommentPersistPort;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class PostingCommentService {
    private final PostingCommentPersistPort postingCommentPersistPort;
    public void createPostingComment() {
        postingCommentPersistPort.createPostingComment();
    }

    public void updatePostingComment() {
        postingCommentPersistPort.updatePostingComment();
    }

    public void deletePostingComment() {
        postingCommentPersistPort.deletePostingCommentById();
    }

    public void getPostingCommentsByPostingId() {
        postingCommentPersistPort.getPostingCommentsByPostingId();
    }
}
