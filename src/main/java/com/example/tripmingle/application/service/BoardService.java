package com.example.tripmingle.application.service;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.example.tripmingle.port.out.BoardSearchPort;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.tripmingle.common.utils.CommonUtils;
import com.example.tripmingle.common.utils.UserUtils;
import com.example.tripmingle.dto.etc.UpdateBoardDTO;
import com.example.tripmingle.dto.req.board.CreateBoardReqDTO;
import com.example.tripmingle.dto.req.board.GetAllBoardReqDTO;
import com.example.tripmingle.dto.req.board.UpdateBoardReqDTO;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.BoardPersistPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Slf4j
public class BoardService {
	private final BoardPersistPort boardPersistPort;
	private final BoardSearchPort boardSearchPort;
	private final UserUtils userUtils;
	private final CommonUtils commonUtils;

	public List<Board> getRecentBoardsByCountryName(String countryName) {
		return boardPersistPort.getRecentBoardsByCountryName(countryName);
	}

	public void getBoardsByIdList() {

	}

	public Page<Board> getAllBoards(GetAllBoardReqDTO getAllBoardReqDTO, Pageable pageable) {
		return boardPersistPort.getAllBoards(getAllBoardReqDTO.getCountryName()
			, getAllBoardReqDTO.getGender()
			, getAllBoardReqDTO.getLanguage()
			, pageable);
	}

	public Board getBoardById(Long boardId) {

		return boardPersistPort.getBoardById(boardId);
	}

	public Board createBoard(CreateBoardReqDTO createBoardReqDTO, User currentUser, String randomImageUrl) {

		Board board = Board.builder()
			.user(currentUser)
			.title(createBoardReqDTO.getTitle())
			.content(createBoardReqDTO.getContent())
			.continent(createBoardReqDTO.getContinent())
			.countryName(createBoardReqDTO.getCountryName())
			.tripType(commonUtils.convertListToString(createBoardReqDTO.getTripType()))
			.preferGender(commonUtils.convertIntToDouble(createBoardReqDTO.getPreferGender()))
			.preferSmoking(commonUtils.convertIntToDouble(createBoardReqDTO.getPreferSmoking()))
			.preferBudget(commonUtils.convertIntToDouble(createBoardReqDTO.getPreferBudget()))
			.preferPhoto(commonUtils.convertIntToDouble(createBoardReqDTO.getPreferPhoto()))
			.preferDrink(commonUtils.convertIntToDouble(createBoardReqDTO.getPreferDrink()))
			.currentCount(1)
			.imageUrl(randomImageUrl)
			.maxCount(createBoardReqDTO.getMaxCount())
			.startDate(createBoardReqDTO.getStartDate())
			.endDate(createBoardReqDTO.getEndDate())
			.language(createBoardReqDTO.getLanguage())
			.commentCount(0)
			.build();
		boardSearchPort.saveBoard(boardPersistPort.saveBoard(board));
		return board;
	}

	public Board updateBoard(Long boardId, UpdateBoardReqDTO updateBoardReqDTO, User currentUser) {
		Board board = boardPersistPort.getBoardById(boardId);
		userUtils.validateMasterUser(board.getUser().getId(), currentUser.getId());
		UpdateBoardDTO updateBoardDTO = UpdateBoardDTO.builder()
			.startDate(updateBoardReqDTO.getStartDate())
			.endDate(updateBoardReqDTO.getEndDate())
			.language(updateBoardReqDTO.getLanguage())
			.maxCount(updateBoardReqDTO.getMaxCount())
			.tripType(commonUtils.convertListToString(updateBoardReqDTO.getTripType()))
			.preferGender(commonUtils.convertDoubleToInt(updateBoardReqDTO.getPreferGender()))
			.preferSmoking(commonUtils.convertDoubleToInt(updateBoardReqDTO.getPreferSmoking()))
			.preferBudget(commonUtils.convertDoubleToInt(updateBoardReqDTO.getPreferBudget()))
			.preferPhoto(commonUtils.convertDoubleToInt(updateBoardReqDTO.getPreferPhoto()))
			.preferDrink(commonUtils.convertDoubleToInt(updateBoardReqDTO.getPreferDrink()))
			.title(updateBoardReqDTO.getTitle())
			.content(updateBoardReqDTO.getContent())
			.build();
		board.update(updateBoardDTO);
		boardSearchPort.saveBoard(board);
		return boardPersistPort.saveBoard(board);
	}

	public void deleteBoard(Long boardId, User currentUser) {
		Board board = boardPersistPort.getBoardById(boardId);
		userUtils.validateMasterUser(board.getUser().getId(), currentUser.getId());
		boardSearchPort.deleteBoard(board);
		boardPersistPort.deleteBoardById(board.getId());
	}

	public Page<Board> searchBoard(String countryName, String keyword, Pageable pageable) {
		List<Long> boardIds = boardSearchPort.searchBoard(countryName, keyword, pageable);

		int startIdx = (int) pageable.getOffset();
		int endIdx = Math.min((startIdx + pageable.getPageSize()), boardIds.size());

		List<Board> boards = boardPersistPort.getAllBoardsByIds(boardIds.subList(startIdx, endIdx));

		Map<Long, Board> boardMap = boards.stream()
				.collect(Collectors.toMap(Board::getId, Function.identity()));

		List<Board> sortedBoards = boardIds.stream()
				.map(boardMap::get)
				.collect(Collectors.toList());

		return new PageImpl<>(sortedBoards, pageable, sortedBoards.size());
	}

	public Page<Board> searchBoardByDB(String countryName, String keyword, Pageable pageable) {
		return boardPersistPort.searchBoard(countryName, keyword, pageable);
	}

	public void getBoardsWithinRange() {
		boardPersistPort.getBoardsWithinRange();
	}

	public void updateCommentCount(Board board, int commentCount) {
		board.decreaseCommentCount(commentCount);
	}

	public Page<Board> getBoardsByUser(User user, Pageable pageable) {
		return boardPersistPort.getBoardByUser(user, pageable);
	}

}
