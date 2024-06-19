package com.example.tripmingle.repository;

import com.example.tripmingle.entity.PostingLikes;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PostingLikesRepository extends JpaRepository<PostingLikes, Long> {
    boolean existsByPostingIdAndUserId(Long postingId, Long userId);

    Optional<PostingLikes> findByPostingIdAndUserId(Long postingId, Long userId);
}
