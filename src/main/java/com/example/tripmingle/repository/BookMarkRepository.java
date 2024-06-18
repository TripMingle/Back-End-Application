package com.example.tripmingle.repository;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BookMark;
import com.example.tripmingle.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long> {
    boolean existsBookMarkByUserAndBoard(User user, Board board);

    Optional<BookMark> findBookMarkByUserAndBoard(User user, Board board);

    List<BookMark> findBookMarksByUser(User user);
}
