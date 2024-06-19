package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.PostingLikes;

public interface PostingLikesPersistPort {
    boolean existsByPostingIdAndUserId(Long postingId, Long id);

    void save(PostingLikes postingLikes);

    PostingLikes findByPostingIdAndUserId(Long id, Long id1);
}
