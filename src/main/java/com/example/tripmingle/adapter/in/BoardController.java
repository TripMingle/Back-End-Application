package com.example.tripmingle.adapter.in;

import com.example.tripmingle.common.result.ResultCode;
import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.PostBoardReqDTO;
import com.example.tripmingle.dto.res.GetBoardInfoResDTO;
import com.example.tripmingle.dto.res.GetBoardsResDTO;
import com.example.tripmingle.dto.res.PostBoardResDTO;
import com.example.tripmingle.port.in.BoardUseCase;
import com.example.tripmingle.port.in.BoardCommentUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardUseCase boardUseCase;
    private final BoardCommentUseCase boardCommentUseCase;

    @GetMapping("/preview/{country-name}")
    //게시물 미리보기 리스트(나라명 입력, 최신순 n개)
    public ResponseEntity<ResultResponse> getRecentBoards(@PathVariable(value = "country-name") String countryName){
        List<GetBoardsResDTO> getBoardsResDTO = boardUseCase.getRecentBoards(countryName);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_BOARD_PREVIEW_SUCCESS, getBoardsResDTO));
    }

    @GetMapping("/{country-name}")
    //게시물 전체조회(나라명 입력, 성별이나 언어로 필터링, 페이징)
    public ResponseEntity<ResultResponse> getAllBoards(@PathVariable(value = "country-name")String countryName
                                                       ,@RequestParam(value = "gender", required = false) String gender,
                                                       @RequestParam(value = "language", required = false) String language,
                                                       Pageable pageable){
        Page<GetBoardsResDTO> getBoardsResDTOS = boardUseCase.getAllBoards(countryName,gender,language,pageable);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_ALL_BOARD_SUCCESS,getBoardsResDTOS));
    }

    @GetMapping("/{board-id}")
    //단일게시물 조회 (상세내용 + 댓글까지 + 유저정보 + 태그)
    public ResponseEntity<ResultResponse> getBoard(@PathVariable(value="board-id")Long boardId){
        GetBoardInfoResDTO getBoardInfoResDTO = boardUseCase.getBoard(boardId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_SINGLE_BOARD_SUCCESS,getBoardInfoResDTO));
    }

    @PostMapping()
    //게시글 작성
    public ResponseEntity<ResultResponse> createBoard(@RequestBody PostBoardReqDTO postBoardReqDTO){
        PostBoardResDTO postBoardResDTO = boardUseCase.createBoard(postBoardReqDTO);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.EXAMPLE, postBoardResDTO));
    }

    //게시글 수정
    public void updateBoard(){
        boardUseCase.updateBoard();
    }

    //게시글 삭제
    public void deleteBoard(){
        boardUseCase.deleteBoard();
    }

    //게시글 검색
    public void searchBoard(){
        boardUseCase.searchBoard();
    }

    //댓글달기 (대댓글달기도 같은 API 사용)
    public void createComment(){
        boardCommentUseCase.createBoardComment();
    }

    //댓글수정(대댓글도 같은 API 사용)
    public void  updateComment(){
        boardCommentUseCase.updateBoardComment();
    }

    //댓글삭제(status만 변경, 대댓글도 사용가능)
    public void deleteComment(){
        boardCommentUseCase.deleteBoardComment();
    }

    //북마크

    //좋아요

    //여행확정짓기

    //내가 방장인 모든 게시물 조회

    //내가 참여하고있는 모든 게시물 조회



}
