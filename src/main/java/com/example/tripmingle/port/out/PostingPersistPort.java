package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.PostingType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostingPersistPort {

    Long createPosting(Posting posting);

    Posting getPostingById(Long postingId);

    List<Posting> findAllPostingForPreview();

    Page<Posting> getAllPostings(PostingType postingType, Pageable pageable);

    Page<Posting> getSearchPostings(String keyword, Pageable pageable);
}
