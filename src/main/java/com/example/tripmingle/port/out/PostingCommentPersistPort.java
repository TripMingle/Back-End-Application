package com.example.tripmingle.port.out;

import org.springframework.stereotype.Component;

@Component
public interface PostingCommentPersistPort {
    void createPostingComment();

    void updatePostingComment();

    void deletePostingCommentById();

    void getPostingCommentsByPostingId();
}
