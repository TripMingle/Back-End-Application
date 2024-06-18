package com.example.tripmingle.repository;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardLikes;
import com.example.tripmingle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardLikesRepository extends JpaRepository<BoardLikes, Long> {
    Optional<BoardLikes> findByUserAndBoard(User user, Board board);

    boolean existsBoardBookMarkByUserAndBoard(User currentUser, Board board);

    List<BoardLikes> findBoardLikesByUser(User user);
}
