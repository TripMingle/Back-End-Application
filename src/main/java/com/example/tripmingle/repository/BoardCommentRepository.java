package com.example.tripmingle.repository;

import com.example.tripmingle.entity.BoardComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BoardCommentRepository extends JpaRepository<BoardComment,Long> {
    @Query("SELECT bc FROM BoardComment bc WHERE bc.board.id = :boardId ORDER BY bc.createdAt ASC")
    List<BoardComment> findBoardCommentsByBoardIdOOrderByCreatedAtAsc(Long boardId);
}
