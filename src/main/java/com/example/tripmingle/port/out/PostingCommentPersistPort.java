package com.example.tripmingle.port.out;

public interface PostingCommentPersistPort {
    void createPostingComment();

    void updatePostingComment();

    void deletePostingCommentById();

    void getPostingCommentsByPostingId();
}
