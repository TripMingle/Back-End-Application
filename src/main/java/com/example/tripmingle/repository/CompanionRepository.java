package com.example.tripmingle.repository;

import com.example.tripmingle.entity.Companion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CompanionRepository extends JpaRepository<Companion, Long> {
    Optional<Companion> findCompanionByBoardIdAndUserId(Long boardId, Long userId);

    List<Companion> findCompanionsByBoardId(Long boardId);
}
