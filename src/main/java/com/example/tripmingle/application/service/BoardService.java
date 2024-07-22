package com.example.tripmingle.application.service;

import java.util.List;

import org.springframework.data.domain.Page;
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
public class BoardService {
	private final BoardPersistPort boardPersistPort;
	private final UserUtils userUtils;
	private final CommonUtils commonUtils;

	public List<Board> getRecentBoardsByCountryName(String countryName) {
		return boardPersistPort.getRecentBoardsByCountryName(countryName);
	}

	public void getBoardsByIdList() {
		boardPersistPort.getAllBoardsByIds();
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
			.preferShopping(commonUtils.convertIntToDouble(createBoardReqDTO.getPreferShopping()))
			.preferInstagramPicture(commonUtils.convertIntToDouble(createBoardReqDTO.getPreferInstagramPicture()))
			.preferDrink(commonUtils.convertIntToDouble(createBoardReqDTO.getPreferDrink()))
			.currentCount(1)
			.imageUrl(randomImageUrl)
			.maxCount(createBoardReqDTO.getMaxCount())
			.startDate(createBoardReqDTO.getStartDate())
			.endDate(createBoardReqDTO.getEndDate())
			.language(createBoardReqDTO.getLanguage())
			.commentCount(0)
			.build();

		return boardPersistPort.saveBoard(board);
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
			.preferGender(commonUtils.convertIntToDouble(updateBoardReqDTO.getPreferGender()))
			.preferSmoking(commonUtils.convertIntToDouble(updateBoardReqDTO.getPreferSmoking()))
			.preferShopping(commonUtils.convertIntToDouble(updateBoardReqDTO.getPreferShopping()))
			.preferInstagramPicture(commonUtils.convertIntToDouble(updateBoardReqDTO.getPreferInstagramPicture()))
			.preferDrink(commonUtils.convertIntToDouble(updateBoardReqDTO.getPreferDrink()))
			.title(updateBoardReqDTO.getTitle())
			.content(updateBoardReqDTO.getContent())
			.build();
		board.update(updateBoardDTO);
		return boardPersistPort.saveBoard(board);
	}

	public void deleteBoard(Long boardId, User currentUser) {
		Board board = boardPersistPort.getBoardById(boardId);
		userUtils.validateMasterUser(board.getUser().getId(), currentUser.getId());
		boardPersistPort.deleteBoardById(board.getId());
	}

	public Page<Board> searchBoard(String countryName, String keyword, Pageable pageable) {
		return boardPersistPort.searchBoard(countryName, keyword, pageable);
	}

	public void getBoardsWithinRange() {
		boardPersistPort.getBoardsWithinRange();
	}

	public void saveBoard(Board board) {
		boardPersistPort.saveBoard(board);
	}

	public void updateCommentCount(Board board, int commentCount) {
		board.decreaseCommentCount(commentCount);
	}

	public Page<Board> getBoardsByUser(User user, Pageable pageable) {
		return boardPersistPort.getBoardByUser(user, pageable);
	}

}
