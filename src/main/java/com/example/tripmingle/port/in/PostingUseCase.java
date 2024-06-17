package com.example.tripmingle.port.in;

public interface PostingUseCase {

    void getRecentPostings();
    void getAllPostings();

    void createPosting();

    void updatePosting();

    void deletePosting();

    void getPostingInfo();


}
