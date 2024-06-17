package com.example.tripmingle.application.Service;

import com.example.tripmingle.port.out.PostingCommentPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
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
