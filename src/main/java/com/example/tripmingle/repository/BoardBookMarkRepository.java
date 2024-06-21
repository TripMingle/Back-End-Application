package com.example.tripmingle.repository;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardBookMark;
import com.example.tripmingle.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BoardBookMarkRepository extends JpaRepository<BoardBookMark, Long> {
    boolean existsBoardBookMarkByUserAndBoard(User user, Board board);

    Optional<BoardBookMark> findBoardBookMarkByUserAndBoard(User user, Board board);

    @Query("select bbm from BoardBookMark bbm where bbm.user=:user and bbm.isActive=true")
    Page<BoardBookMark> findBoardBookMarksByUser(User user, Pageable pageable);

    List<BoardBookMark> findBoardBookMarksByBoardId(Long boardId);
}
