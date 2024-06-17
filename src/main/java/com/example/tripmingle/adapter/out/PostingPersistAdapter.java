package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.PostingNotFoundException;
import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.port.out.PostingPersistPort;
import com.example.tripmingle.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PostingPersistAdapter implements PostingPersistPort {

    private final PostingRepository postingRepository;

    @Override
    public Long createPosting(Posting posting) {
        return postingRepository.save(posting).getId();
    }

    @Override
    public Posting getPostingById(Long postingId) {
        return postingRepository.findById(postingId).orElseThrow(() -> new PostingNotFoundException("Posting Not Found.", ErrorCode.POSING_NOT_FOUND));
    }

    @Override
    public List<Posting> findAllPostingForPreview() {
        return postingRepository.findTop10ByOrderByCreatedAtDesc();
    }

    @Override
    public Slice<Posting> getAllPostings(Pageable pageable) {
        return postingRepository.findAll(pageable);
    }
}
