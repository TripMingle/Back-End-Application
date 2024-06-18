package com.example.tripmingle.repository;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardBookMark;
import com.example.tripmingle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardBookMarkRepository extends JpaRepository<BoardBookMark, Long> {
    boolean existsBoardBookMarkByUserAndBoard(User user, Board board);

    Optional<BoardBookMark> findBoardBookMarkByUserAndBoard(User user, Board board);

    List<BoardBookMark> findBoardBookMarksByUser(User user);
}
