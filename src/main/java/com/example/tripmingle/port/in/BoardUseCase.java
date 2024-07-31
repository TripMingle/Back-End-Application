package com.example.tripmingle.port.in;

import java.util.List;

import com.example.tripmingle.dto.res.board.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.example.tripmingle.dto.req.board.ConfirmUsersReqDTO;
import com.example.tripmingle.dto.req.board.CreateBoardReqDTO;
import com.example.tripmingle.dto.req.board.GetAllBoardReqDTO;
import com.example.tripmingle.dto.req.board.UpdateBoardReqDTO;

public interface BoardUseCase {
	List<GetBoardsResDTO> getRecentBoards(String countryName);

	void getClusteredBoards();

	Page<GetBoardsResDTO> getBoardList(GetAllBoardReqDTO getAllBoardReqDTO, Pageable pageable);

	GetBoardInfoResDTO getBoard(Long boardId);

	GetBoardInfoResDTO createBoard(CreateBoardReqDTO createBoardReqDTO);

	GetBoardInfoResDTO updateBoard(Long boardId, UpdateBoardReqDTO patchBoardReqDTO);

	void deleteBoard(Long boardId);

	Page<SearchBoardResDTO>  searchBoard(String countryName, String keyword, Pageable pageable);

	void getBoardsWithinRange();

	ToggleStateResDTO toggleBoardBookMark(Long boardId);

	Page<GetBoardsResDTO> getMyBookMarkedBoards(Pageable pageable);

	ToggleStateResDTO toggleBoardLikes(Long boardId);

	Page<GetBoardsResDTO> getMyLikedBoards(Pageable pageable);

	Page<GetBoardsResDTO> getMyBoards(Pageable pageable);

	void confirmUsers(Long boardId, ConfirmUsersReqDTO confirmUsersReqDTO);

	void leaveCompanion(Long boardId);

	List<GetCompanionsResDTO> getCompanions(Long boardId);

	Page<GetBoardsResDTO> getMyCompanionBoards(Pageable pageable);
}
