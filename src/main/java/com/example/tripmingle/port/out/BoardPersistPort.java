package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import java.util.List;

public interface BoardPersistPort {
    Page<Board> getAllBoards(String country, String gender, String language, Pageable pageable);

    Board getBoardById(Long boardId);

    void deleteBoardById(Long boardId);

    void getAllBoardsByIds();

    List<Board> getRecentBoardsByCountryName(String countryName);

    Long saveBoard(Board board);

    List<Board> searchBoard(String keyword);

    void getBoardsWithinRange();

    List<Board> getBoardByUser(User user);
}
