package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.Posting;

public interface PostingPersistPort {

    Long createPosting(Posting posting);

    Posting getPostingById(Long postingId);

}
