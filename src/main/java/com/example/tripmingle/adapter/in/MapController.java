package com.example.tripmingle.adapter.in;

import com.example.tripmingle.port.in.BoardUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "지도")
@RequiredArgsConstructor
public class MapController {
	private final BoardUseCase boardUseCase;

	//범위, 좌표를 받고 해당 범위에 들어있는 게시판 리스트 반환 -> 시간도 받아야한다.
	public void getBoardsWithinRange() {
		boardUseCase.getBoardsWithinRange();
	}

	//게시물 미리보기 리스트(핀(BoardSchedule)을 찍었을 때, 리스트로 반환) -> 시간도 받아야한다.
	public void getClusteredBoards() {
		boardUseCase.getClusteredBoards();
	}

}