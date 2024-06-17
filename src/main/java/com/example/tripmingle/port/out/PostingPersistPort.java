package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.Posting;

import java.util.List;

public interface PostingPersistPort {

    Long createPosting(Posting posting);

    Posting getPostingById(Long postingId);

    List<Posting> findAllPostingForPreview();
}
