package com.example.tripmingle.adapter.out;

import com.example.tripmingle.entity.PostingLikes;
import com.example.tripmingle.port.out.PostingLikesPersistPort;
import com.example.tripmingle.repository.PostingLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PostingLikesPersistAdapter implements PostingLikesPersistPort {

    private final PostingLikesRepository postingLikesRepository;

    @Override
    public boolean existsByPostingIdAndUserId(Long postingId, Long userId) {
        return postingLikesRepository.existsByPostingIdAndUserId(postingId, userId);
    }

    @Override
    public void save(PostingLikes postingLikes) {
        postingLikesRepository.save(postingLikes);
    }

    @Override
    public PostingLikes findByPostingIdAndUserId(Long postingId, Long userId) {
        return postingLikesRepository.findByPostingIdAndUserId(postingId, userId);
    }

    @Override
    public Page<PostingLikes> getAllPostingLikes(Long userId, Pageable pageable) {
        return postingLikesRepository.findAllPostingsLikesByUserId(userId, pageable);
    }
}
