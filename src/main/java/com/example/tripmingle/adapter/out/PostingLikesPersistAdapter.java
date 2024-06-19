package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.exception.PostingLikesNotFoundException;
import com.example.tripmingle.entity.PostingLikes;
import com.example.tripmingle.port.out.PostingLikesPersistPort;
import com.example.tripmingle.repository.PostingLikesRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.tripmingle.common.error.ErrorCode.POSTING_LIKES_NOT_FOUND;

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
        return postingLikesRepository.findByPostingIdAndUserId(postingId, userId).orElseThrow(
                () -> new PostingLikesNotFoundException("Posting Likes Not Found", POSTING_LIKES_NOT_FOUND));
    }
}
