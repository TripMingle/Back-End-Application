package com.example.tripmingle.repository;

import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.PostingType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {
    Optional<Posting> findByIdAndIsDeletedFalse(Long postingId);

    List<Posting> findTop10ByOrderByCreatedAtDesc();

    Slice<Posting> findAllByPostingType(PostingType postingType, Pageable pageable);

    @Query("select p from Posting p where lower(p.title) like %:keyword% or lower(p.content) like %:keyword% order by p.createdAt desc")
    Slice<Posting> findAllBySearching(String keyword, Pageable pageable);

}
