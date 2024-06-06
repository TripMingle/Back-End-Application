package com.example.tripmingle.port.out;

public interface PostingPersistPort {
    void getRecentPostings();

    void getAllPostings();

    void createPosting();

    void updatePosting();

    void getPostingById();

    void deletePostingById();
}
