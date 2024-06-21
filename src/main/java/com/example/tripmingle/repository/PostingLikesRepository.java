package com.example.tripmingle.repository;

import com.example.tripmingle.entity.PostingLikes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostingLikesRepository extends JpaRepository<PostingLikes, Long> {
    boolean existsByPostingIdAndUserId(Long postingId, Long userId);

    Optional<PostingLikes> findByPostingIdAndUserId(Long postingId, Long userId);

    @Query("select pl from PostingLikes pl where pl.user.id = :userId and pl.toggleState = true")
    Page<PostingLikes> findAllPostingsLikesByUserId(Long userId, Pageable pageable);

    int countByPostingIdAndToggleStateTrue(Long postingId);

    List<PostingLikes> findAllByPostingId(Long postingId);
}
