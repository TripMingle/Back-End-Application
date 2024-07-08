package com.example.tripmingle.adapter.in;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripmingle.common.result.ResultCode;
import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.schedule.CreateBoardScheduleReqDTO;
import com.example.tripmingle.dto.req.schedule.ModifyBoardScheduleReqDTO;
import com.example.tripmingle.dto.res.schedule.BoardScheduleResDTO;
import com.example.tripmingle.dto.res.schedule.GetBoardScheduleResDTO;
import com.example.tripmingle.port.in.BoardScheduleUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "동행 일정")
@RestController
@RequestMapping("/board/schedule")
@RequiredArgsConstructor
public class BoardScheduleController {

	private final BoardScheduleUseCase boardScheduleUseCase;

	@PostMapping("/{board-id}")
	//게시판 일정 추가 (게시물id-> 대륙,나라,기간 등 가져오기, List<장소,날짜,순서,좌표>)
	public ResponseEntity<ResultResponse> createBoardSchedule(@PathVariable(value = "board-id") Long boardId,
		@RequestBody List<CreateBoardScheduleReqDTO> createBoardScheduleReqDTOS) {
		List<BoardScheduleResDTO> boardScheduleResDTO = boardScheduleUseCase.createBoardSchedule(boardId,
			createBoardScheduleReqDTOS);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.CREATE_BOARD_SCHEDULE_SUCCESS, boardScheduleResDTO));
	}

	@PostMapping("/{board-id}/modify")
	//게시물 일정 수정&삭제 로직
	public ResponseEntity<ResultResponse> modifyBoardSchedule(@PathVariable(value = "board-id") Long boardId,
		@RequestBody ModifyBoardScheduleReqDTO modifyBoardScheduleReqDTO) {
		boardScheduleUseCase.modifyBoardSchedule(boardId, modifyBoardScheduleReqDTO.getUpdateBoardScheduleReqDTOS(),
			modifyBoardScheduleReqDTO.getDeleteBoardScheduleReqDTOS());
		return ResponseEntity.ok(ResultResponse.of(ResultCode.MODIFY_BOARD_SCHEDULE_SUCCESS));
	}

	@GetMapping("/{board-id}")
	//게시물 일정조회
	public ResponseEntity<ResultResponse> getBoardSchedule(@PathVariable(value = "board-id") Long boardId) {
		GetBoardScheduleResDTO getBoardScheduleResDTO = boardScheduleUseCase.getBoardSchedule(boardId);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_BOARD_SCHEDULE_SUCCESS, getBoardScheduleResDTO));
	}

	//내 일정에서 게시판 일정으로 옮기기

	//지도에서 조회 (나라, 기간에 대한 필터링 + 좌표에 대한 필터링이 있다, 이미 지난 일정은 조회하지 않도록 하는 로직 필요) -> 인덱싱 필요
	//is_expired를 하면 안될거같음. 그냥 쿼리문에 날짜비교쿼리를 포함시키는게 좋을듯?
}
