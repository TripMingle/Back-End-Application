package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface BoardPersistPort {
    Page<Board> getAllBoards(String country, String gender, String language, Pageable pageable);

    Board getBoardById(Long boardId);

    void deleteBoardById(Long boardId);

    void getAllBoardsByIds();

    List<Board> getRecentBoardsByCountryName(String countryName);

    Board saveBoard(Board board);

    Page<Board> searchBoard(String keyword, Pageable pageable);

    void getBoardsWithinRange();

    Page<Board> getBoardByUser(User user, Pageable pageable);
}
