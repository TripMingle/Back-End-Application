package com.example.tripmingle.adapter.in;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripmingle.common.result.ResultCode;
import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.matching.MatchingBoardReqDTO;
import com.example.tripmingle.dto.req.matching.PostUserPersonalityReqDTO;
import com.example.tripmingle.dto.res.matching.AddUserResDTO;
import com.example.tripmingle.dto.res.matching.MatchingBoardResDTO;
import com.example.tripmingle.dto.res.matching.MatchingUserResDTO;
import com.example.tripmingle.port.in.MatchingUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "매칭")
@RestController
@RequiredArgsConstructor
@RequestMapping("/matching")
public class MatchingController {
	private final MatchingUseCase matchingUseCase;

	@Operation(summary = "나와 어울리는 추천유저 반환 (유저성향 추가가 선행되어야함)")
	//나와 어울리는 추천 유저 리스트 반환
	@GetMapping("/user")
	public ResponseEntity<ResultResponse> getMyMatchingUsers(
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<MatchingUserResDTO> matchingUserResDTOS = matchingUseCase.getMyMatchingUsers(pageable);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_MY_USER_MATCHING_SUCCESS, matchingUserResDTOS));
	}

	@Operation(summary = "내 유저성향 추가")
	//새로운 유저성향 추가
	@PostMapping("/userPersonality")
	public ResponseEntity<ResultResponse> addUserPersonality(
		@RequestBody PostUserPersonalityReqDTO postUserPersonalityReqDTO) {
		AddUserResDTO addUserResDTO = matchingUseCase.addUserPersonality(
			matchingUseCase.saveUserPersonality(postUserPersonalityReqDTO));
		return ResponseEntity.ok(ResultResponse.of(ResultCode.ADD_USER_PERSONALITY_SUCCESS, addUserResDTO));
	}

	@Operation(summary = "유저성향 변경")
	@PatchMapping("/userPersonality")
	//유저성향 변경
	public ResponseEntity<ResultResponse> modifyPersonality(
		@RequestBody PostUserPersonalityReqDTO postUserPersonalityReqDTO) {
		AddUserResDTO addUserResDTO = matchingUseCase.addUserPersonality(
			matchingUseCase.deleteAndSaveUserPersonality(postUserPersonalityReqDTO));
		return ResponseEntity.ok(ResultResponse.of(ResultCode.MODIFY_USER_PERSONALITY_SUCCESS, addUserResDTO));
	}

	@Operation(summary = "나와 어울리는 게시물 매칭")
	@PostMapping("/board")
	//게시물 매칭
	public ResponseEntity<ResultResponse> matchingBoard(@RequestBody MatchingBoardReqDTO matchingBoardReqDTO) {
		List<MatchingBoardResDTO> matchingBoardResDTO = matchingUseCase.matchingBoard(matchingBoardReqDTO);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.MATCHING_BOARD_SUCCESS, matchingBoardResDTO));
	}

}

