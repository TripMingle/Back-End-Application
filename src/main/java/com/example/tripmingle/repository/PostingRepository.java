package com.example.tripmingle.repository;

import com.example.tripmingle.entity.Posting;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostingRepository extends JpaRepository<Posting, Long> {
    Optional<Posting> findByIdAndIsDeletedFalse(Long postingId);
}
