package com.example.tripmingle.application.facadeService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tripmingle.application.service.BoardScheduleService;
import com.example.tripmingle.application.service.BoardService;
import com.example.tripmingle.application.service.UserScheduleService;
import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.application.service.UserTripService;
import com.example.tripmingle.dto.req.schedule.CreateBoardScheduleReqDTO;
import com.example.tripmingle.dto.req.schedule.CreateUserScheduleReqDTO;
import com.example.tripmingle.dto.req.schedule.CreateUserTripReqDTO;
import com.example.tripmingle.dto.req.schedule.DeleteBoardScheduleReqDTO;
import com.example.tripmingle.dto.req.schedule.DeleteUserScheduleReqDTO;
import com.example.tripmingle.dto.req.schedule.UpdateBoardScheduleReqDTO;
import com.example.tripmingle.dto.req.schedule.UpdateUserScheduleReqDTO;
import com.example.tripmingle.dto.res.schedule.BoardScheduleResDTO;
import com.example.tripmingle.dto.res.schedule.CreateUserTripResDTO;
import com.example.tripmingle.dto.res.schedule.GetBoardScheduleResDTO;
import com.example.tripmingle.dto.res.schedule.GetUserScheduleResDTO;
import com.example.tripmingle.dto.res.schedule.GetUserTripResDTO;
import com.example.tripmingle.dto.res.schedule.UserScheduleDTO;
import com.example.tripmingle.dto.res.schedule.UserScheduleResDTO;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.BoardSchedule;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.entity.UserSchedule;
import com.example.tripmingle.entity.UserTrip;
import com.example.tripmingle.port.in.BoardScheduleUseCase;
import com.example.tripmingle.port.in.UserScheduleUseCase;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class BoardScheduleFacadeService implements BoardScheduleUseCase {
	private final BoardScheduleService boardScheduleService;
	private final BoardService boardService;
	private final UserService userService;

	@Override
	@Transactional
	public List<BoardScheduleResDTO> createBoardSchedule(Long boardId,
		List<CreateBoardScheduleReqDTO> createBoardScheduleReqDTOS) {
		Board board = boardService.getBoardById(boardId);
		User currentUser = userService.getCurrentUser();
		List<BoardSchedule> boardSchedules = boardScheduleService.createBoardSchedule(board, currentUser,
			createBoardScheduleReqDTOS);

		return boardSchedules.stream().map(boardSchedule -> makeBoardScheduleDTO(board,boardSchedule))
				.collect(Collectors.toList());
	}

	@Override
	@Transactional
	public void modifyBoardSchedule(Long boardId, List<UpdateBoardScheduleReqDTO> updateBoardScheduleReqDTOS,
		List<DeleteBoardScheduleReqDTO> deleteBoardScheduleReqDTOS) {
		Board board = boardService.getBoardById(boardId);
		User currentUser = userService.getCurrentUser();
		boardScheduleService.updateBoardSchedule(board, currentUser, updateBoardScheduleReqDTOS);
		boardScheduleService.deleteBoardSchedule(board, currentUser, deleteBoardScheduleReqDTOS);
	}

	@Override
	public GetBoardScheduleResDTO getBoardSchedule(Long boardId) {
		User currentUser = userService.getCurrentUser();
		Board board = boardService.getBoardById(boardId);
		List<BoardScheduleResDTO> boardScheduleResDTOList
			= boardScheduleService.getBoardSchedulesByBoardId(boardId).stream()
			.map(boardSchedule -> makeBoardScheduleDTO(board,boardSchedule)).collect(Collectors.toList());

		return GetBoardScheduleResDTO.builder()
			.isMyBoard(currentUser.getId().equals(board.getUser().getId()))
			.boardScheduleResDTOList(boardScheduleResDTOList)
			.build();
	}

	private BoardScheduleResDTO makeBoardScheduleDTO(Board board, BoardSchedule boardSchedule) {
		return BoardScheduleResDTO.builder()
				.boardId(boardSchedule.getBoard().getId())
				.boardScheduleId(boardSchedule.getId())
				.date(boardSchedule.getDate())
				.placeName(boardSchedule.getPlaceName())
				.number(boardSchedule.getNumber())
				.pointX(boardSchedule.getPointX())
				.pointY(boardSchedule.getPointY())
				.googlePlaceId(boardSchedule.getGooglePlaceId())
				.build();
	}



}
