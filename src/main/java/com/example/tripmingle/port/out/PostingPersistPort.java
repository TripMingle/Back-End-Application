package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.Posting;

public interface PostingPersistPort {
    void getRecentPostings();

    void getAllPostings();

    Long createPosting(Posting posting);

    void updatePosting();

    Posting getPostingById(Long postingId);

    void deletePostingById();
}
