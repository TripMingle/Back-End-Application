package com.example.tripmingle.repository;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardLikes;
import com.example.tripmingle.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardLikesRepository extends JpaRepository<BoardLikes, Long> {
    Optional<BoardLikes> findByUserAndBoard(User user, Board board);

    boolean existsBoardBookMarkByUserAndBoard(User currentUser, Board board);

    @Query("SELECT bl FROM BoardLikes bl where bl.user = :user AND bl.isActive=true")
    Page<BoardLikes> findBoardLikesByUser(User user, Pageable pageable);
}
