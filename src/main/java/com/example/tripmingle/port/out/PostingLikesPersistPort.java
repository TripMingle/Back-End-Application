package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.PostingLikes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostingLikesPersistPort {
    boolean existsByPostingIdAndUserId(Long postingId, Long id);

    void save(PostingLikes postingLikes);

    PostingLikes findByPostingIdAndUserId(Long postingId, Long userId);

    Page<PostingLikes> getAllPostingLikes(Long id, Pageable pageable);
}
