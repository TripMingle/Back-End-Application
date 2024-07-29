package com.example.tripmingle.adapter.out;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.BoardNotFoundException;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.BoardPersistPort;
import com.example.tripmingle.repository.BoardRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class BoardPersistAdapter implements BoardPersistPort {

	private final BoardRepository boardRepository;

	@Override
	public Page<Board> getAllBoards(String country, String gender, String language, Pageable pageable) {
		return boardRepository.findAllByCountryNameAndFilters(country, gender, language, pageable);
	}

	@Override
	public Board getBoardById(Long boardId) {
		return boardRepository.findById(boardId)
			.orElseThrow(() -> new BoardNotFoundException("Board not found", ErrorCode.BOARD_NOT_FOUND));
	}

	@Override
	public void deleteBoardById(Long boardId) {
		Board board = boardRepository.findById(boardId)
			.orElseThrow(() -> new BoardNotFoundException("Board not found", ErrorCode.BOARD_NOT_FOUND));
		board.delete();
	}

	@Override
	public List<Board> getAllBoardsByIds(List<Long> boardIds) {
		return boardRepository.findAllById(boardIds);
	}

	@Override
	public List<Board> getRecentBoardsByCountryName(String countryName) {
		return boardRepository.findRecentBoardsByCountryName(countryName);
	}

	@Override
	public Board saveBoard(Board board) {
		return boardRepository.save(board);
	}

	@Override
	public Page<Board> searchBoard(String countryName, String keyword, Pageable pageable) {
		return boardRepository.searchByTitleOrContent(keyword, pageable, countryName);
	}

	@Override
	public void getBoardsWithinRange() {

	}

	@Override
	public Page<Board> getBoardByUser(User user, Pageable pageable) {
		return boardRepository.findBoardsByUser(user, pageable);
	}

	@Override
	public Page<Board> getAllBoardsByIdsAndPage(List<Long> boardIds, Pageable pageable) {
		return boardRepository.findAllByIdIn(boardIds,pageable);
	}

}
