package com.example.tripmingle.repository;

import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.PostingType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {
    Optional<Posting> findByIdAndIsDeletedFalse(Long postingId);

    Page<Posting> findAllByCountryAndPostingType(String country, PostingType postingType, Pageable pageable);

    @Query("select p from Posting p where lower(p.title) like %:keyword% or lower(p.content) like %:keyword% order by p.createdAt desc")
    Page<Posting> findAllBySearching(String keyword, Pageable pageable);

    List<Posting> findTop10ByCountryAndPostingTypeOrderByCreatedAtDesc(String country, PostingType postingType);
}
