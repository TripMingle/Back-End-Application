package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.PostingType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface PostingPersistPort {

    Long createPosting(Posting posting);

    Posting getPostingById(Long postingId);

    List<Posting> findAllPostingForPreview();

    Slice<Posting> getAllPostings(PostingType postingType, Pageable pageable);

    Slice<Posting> getSearchPostings(String keyword, Pageable pageable);
}
