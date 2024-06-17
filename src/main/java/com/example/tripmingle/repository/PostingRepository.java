package com.example.tripmingle.repository;

import com.example.tripmingle.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PostingRepository extends JpaRepository<Posting, Long> {
    Optional<Posting> findByIdAndIsDeletedFalse(Long postingId);

    List<Posting> findTop10ByOrderByCreatedAtDesc();
}
