package com.example.tripmingle.port.out;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.PostingType;

public interface PostingPersistPort {

	Long createPosting(Posting posting);

	Posting getPostingById(Long postingId);

	Posting getPostingByIdWithPessimisticLock(Long postingId);

	List<Posting> findAllPostingForPreview(String country, PostingType postingType);

	Page<Posting> getAllPostings(String country, PostingType postingType, Pageable pageable);

	Page<Posting> getSearchPostings(String keyword, String postingType, Pageable pageable);

	Page<Posting> getAllPopularityPostings(String country, PostingType postingType, Pageable pageable);
}
