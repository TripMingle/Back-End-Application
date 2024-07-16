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

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScheduleFacadeService implements BoardScheduleUseCase, UserScheduleUseCase {
	private final BoardScheduleService boardScheduleService;
	private final UserTripService userTripService;
	private final UserScheduleService userScheduleService;
	private final BoardService boardService;
	private final UserService userService;

	@Override
	@Transactional(readOnly = false)
	public List<BoardScheduleResDTO> createBoardSchedule(Long boardId,
		List<CreateBoardScheduleReqDTO> createBoardScheduleReqDTOS) {
		Board board = boardService.getBoardById(boardId);
		User currentUser = userService.getCurrentUser();
		List<BoardSchedule> boardSchedules = boardScheduleService.createBoardSchedule(board, currentUser,
			createBoardScheduleReqDTOS);

		return boardSchedules.stream().map(boardSchedule -> {
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
		}).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = false)
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
			.map(boardSchedule -> {
				return BoardScheduleResDTO.builder()
					.boardId(board.getId())
					.boardScheduleId(boardSchedule.getId())
					.date(boardSchedule.getDate())
					.placeName(boardSchedule.getPlaceName())
					.number(boardSchedule.getNumber())
					.pointX(boardSchedule.getPointX())
					.pointY(boardSchedule.getPointY())
					.googlePlaceId(boardSchedule.getGooglePlaceId())
					.build();
			}).collect(Collectors.toList());
		return GetBoardScheduleResDTO.builder()
			.isMyBoard(currentUser.getId().equals(board.getUser().getId()))
			.boardScheduleResDTOList(boardScheduleResDTOList)
			.build();
	}

	@Override
	@Transactional(readOnly = false)
	public CreateUserTripResDTO createUserTrip(CreateUserTripReqDTO createUserTripReqDTO) {
		User currentUser = userService.getCurrentUser();
		UserTrip userTrip = userTripService.createUserTrip(currentUser, createUserTripReqDTO);
		return CreateUserTripResDTO.builder().userTripId(userTrip.getId()).build();
	}

	@Override
	@Transactional(readOnly = false)
	public List<UserScheduleResDTO> createUserSchedule(Long userTripId,
		List<CreateUserScheduleReqDTO> createUserScheduleReqDTOS) {
		User currentUser = userService.getCurrentUser();
		UserTrip userTrip = userTripService.getUserTripById(userTripId);
		List<UserSchedule> userSchedules = userScheduleService.createUserSchedule(currentUser, userTrip,
			createUserScheduleReqDTOS);
		return userSchedules.stream().map(
			userSchedule -> {
				return UserScheduleResDTO.builder()
					.userTripId(userTrip.getId())
					.userScheduleId(userSchedule.getId())
					.date(userSchedule.getDate())
					.placeName(userSchedule.getPlaceName())
					.number(userSchedule.getNumber())
					.pointX(userSchedule.getPointX())
					.pointY(userSchedule.getPointY())
					.googlePlaceId(userSchedule.getGooglePlaceId())
					.build();
			}
		).collect(Collectors.toList());
	}

	@Override
	@Transactional(readOnly = false)
	public void deleteUserTrip(Long userTripId) {
		User currentUser = userService.getCurrentUser();
		userScheduleService.deleteUserScheduleByUserTripId(userTripId);
		userTripService.deleteUserTripById(userTripId);
	}

	@Override
	@Transactional(readOnly = false)
	public void modifyUserSchedule(Long userTripId, List<UpdateUserScheduleReqDTO> updateUserScheduleReqDTOS,
		List<DeleteUserScheduleReqDTO> deleteUserScheduleReqDTOS) {
		User currentUser = userService.getCurrentUser();
		UserTrip userTrip = userTripService.getUserTripById(userTripId);
		userScheduleService.updateUserSchedule(currentUser, userTrip, updateUserScheduleReqDTOS);
		userScheduleService.deleteUserSchedule(currentUser, userTrip, deleteUserScheduleReqDTOS);
	}

	@Override
	public List<GetUserTripResDTO> getUserTrip() {
		User currentUser = userService.getCurrentUser();
		List<UserTrip> userTrips = userTripService.getUserTripsByUser(currentUser);
		return userTrips.stream()
			.map(userTrip -> GetUserTripResDTO.builder()
				.userTripId(userTrip.getId())
				.continent(userTrip.getContinent())
				.countryName(userTrip.getCountryName())
				.startDate(userTrip.getStartDate())
				.endDate(userTrip.getEndDate())
				.build())
			.collect(Collectors.toList());
	}

	@Override
	public GetUserScheduleResDTO getUserSchedule(Long userTripId) {
		User currentUser = userService.getCurrentUser();
		UserTrip userTrip = userTripService.getUserTripById(userTripId);
		List<UserSchedule> userSchedules = userScheduleService.getUserScheduleByUserTripId(userTrip.getId());
		List<UserScheduleDTO> userScheduleDTOList = userSchedules.stream()
			.map(userSchedule -> UserScheduleDTO.builder()
				.userTripId(userTrip.getId())
				.userScheduleId(userSchedule.getId())
				.date(userSchedule.getDate())
				.placeName(userSchedule.getPlaceName())
				.number(userSchedule.getNumber())
				.pointX(userSchedule.getPointX())
				.pointY(userSchedule.getPointY())
				.googlePlaceId(userSchedule.getGooglePlaceId())
				.build()).collect(Collectors.toList());

		return GetUserScheduleResDTO.builder()
			.userTripId(userTrip.getId())
			.userScheduleDTOList(userScheduleDTOList)
			.build();
	}
}
