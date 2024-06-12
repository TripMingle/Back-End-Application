package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.Board;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface BoardPersistPort {
    Page<Board> getAllBoards(String country, String gender, String language, Pageable pageable);

    Optional<Board> getBoardById(Long boardId);

    void deleteBoardById();

    void getAllBoardsByIds();

    List<Board> getRecentBoards(String countryName);

    Long saveBoard(Board board);

    void searchBoard();

    void getBoardsWithinRange();

}
