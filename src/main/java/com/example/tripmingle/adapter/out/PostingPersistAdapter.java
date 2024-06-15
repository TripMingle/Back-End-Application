package com.example.tripmingle.adapter.out;

import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.port.out.PostingPersistPort;
import com.example.tripmingle.repository.PostingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class PostingPersistAdapter implements PostingPersistPort {

    private final PostingRepository postingRepository;

    @Override
    public void getRecentPostings() {

    }

    @Override
    public void getAllPostings() {

    }

    @Override
    public Long createPosting(Posting posting) {
        return postingRepository.save(posting).getId();
    }

    @Override
    public void updatePosting() {

    }

    @Override
    public void getPostingById() {

    }

    @Override
    public void deletePostingById() {

    }
}
