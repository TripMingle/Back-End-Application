package com.example.tripmingle.adapter.in;

import com.example.tripmingle.port.in.BoardUseCase;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class MapController {
    private final BoardUseCase boardUseCase;

    //범위, 좌표를 받고 해당 범위에 들어있는 게시판 리스트 반환
    public void getBoardsWithinRange(){
        boardUseCase.getBoardsWithinRange();
    }

    //게시물 미리보기 리스트(핀(BoardSchedule)을 찍었을 때, 리스트로 반환)
    public void getClusteredBoards(){
        boardUseCase.getClusteredBoards();
    }

}