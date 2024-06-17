package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.Posting;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PostingPersistPort {

    Long createPosting(Posting posting);

    Posting getPostingById(Long postingId);

    List<Posting> findAllPostingForPreview();

    Slice<Posting> getAllPostings(Pageable pageable);
}
