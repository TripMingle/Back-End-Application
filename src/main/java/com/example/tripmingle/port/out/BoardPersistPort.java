package com.example.tripmingle.port.out;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.User;

public interface BoardPersistPort {
	Page<Board> getAllBoards(String country, String gender, String language, Pageable pageable);

	Board getBoardById(Long boardId);

	void deleteBoardById(Long boardId);

	List<Board> getAllBoardsByIds(List<Long> boardIds);

	List<Board> getRecentBoardsByCountryName(String countryName);

	Board saveBoard(Board board);

	Page<Board> searchBoard(String countryName, String keyword, Pageable pageable);

	void getBoardsWithinRange();

	Page<Board> getBoardByUser(User user, Pageable pageable);

    Page<Board> getAllBoardsByIdsAndPage(List<Long> boardIds, Pageable pageable);
}
