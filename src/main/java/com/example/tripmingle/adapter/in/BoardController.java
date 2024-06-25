package com.example.tripmingle.adapter.in;

import com.example.tripmingle.common.result.ResultCode;
import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.board.*;
import com.example.tripmingle.dto.res.board.*;
import com.example.tripmingle.port.in.BoardCommentUseCase;
import com.example.tripmingle.port.in.BoardUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
    private final BoardUseCase boardUseCase;
    private final BoardCommentUseCase boardCommentUseCase;

    @GetMapping("/preview/{country-name}")
    //게시물 미리보기 리스트(나라명 입력, 최신순 n개)
    public ResponseEntity<ResultResponse> getRecentBoards(@PathVariable(value = "country-name") String countryName) {
        List<GetBoardsResDTO> getBoardsResDTO = boardUseCase.getRecentBoards(countryName);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_BOARD_PREVIEW_SUCCESS, getBoardsResDTO));
    }

    @GetMapping("/{country-name}")
    //게시물 전체조회(나라명 입력, 성별이나 언어로 필터링, 페이징)
    public ResponseEntity<ResultResponse> getAllBoards(@PathVariable(value = "country-name") String countryName
            , @RequestParam(value = "gender", required = false) String gender
            , @RequestParam(value = "language", required = false) String language
            , @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        GetAllBoardReqDTO getAllBoardReqDTO = GetAllBoardReqDTO.builder()
                .language(language)
                .gender(gender)
                .countryName(countryName)
                .build();
        Page<GetBoardsResDTO> getBoardsResDTOS = boardUseCase.getAllBoards(getAllBoardReqDTO, pageable);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_ALL_BOARD_SUCCESS, getBoardsResDTOS));
    }

    @GetMapping("/show/{board-id}")
    //단일게시물 조회 (상세내용 + 댓글까지 + 유저정보 + 태그)
    public ResponseEntity<ResultResponse> getBoard(@PathVariable(value = "board-id") Long boardId) {
        GetBoardInfoResDTO getBoardInfoResDTO = boardUseCase.getBoard(boardId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_SINGLE_BOARD_SUCCESS, getBoardInfoResDTO));
    }

    @PostMapping()
    //게시글 작성
    public ResponseEntity<ResultResponse> createBoard(@RequestBody CreateBoardReqDTO createBoardReqDTO) {
        PostBoardResDTO postBoardResDTO = boardUseCase.createBoard(createBoardReqDTO);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CREATE_BOARD_SUCCESS, postBoardResDTO));
    }

    @PatchMapping("/{board-id}")
    //게시글 수정
    public ResponseEntity<ResultResponse> updateBoard(@PathVariable(value = "board-id") Long boardId,
                                                      @RequestBody UpdateBoardReqDTO updateBoardReqDTO) {
        UpdateBoardResDTO updateBoardResDTO = boardUseCase.updateBoard(boardId, updateBoardReqDTO);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.UPDATE_BOARD_SUCCESS, updateBoardResDTO));
    }

    @DeleteMapping("/{board-id}")
    //게시글 삭제
    public ResponseEntity<ResultResponse> deleteBoard(@PathVariable(value = "board-id") Long boardId) {
        boardUseCase.deleteBoard(boardId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_BOARD_SUCCESS));
    }

    @GetMapping("/search/{country-name}")
    //게시글 검색
    public ResponseEntity<ResultResponse> searchBoard(@PathVariable(value = "country-name") String countryName
            , @RequestParam String keyword
            , @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<GetBoardsResDTO> getBoardsResDTOS = boardUseCase.searchBoard(keyword, pageable);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.SEARCH_BOARD_SUCCESS, getBoardsResDTOS));
    }

    @PostMapping("/comment")
    //댓글달기 (대댓글달기도 같은 API 사용)
    public ResponseEntity<ResultResponse> createComment(@RequestBody CreateBoardCommentReqDTO createBoardCommentReqDTO) {
        CreateBoardCommentResDTO createBoardCommentResDTO = boardCommentUseCase.createBoardComment(createBoardCommentReqDTO);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CREATE_BOARD_COMMENT_SUCCESS, createBoardCommentResDTO));
    }

    @PatchMapping("/comment/{comment-id}")
    //댓글수정(대댓글도 같은 API 사용)
    public ResponseEntity<ResultResponse> updateComment(@PathVariable(value = "comment-id") Long commentId,
                                                        @RequestBody UpdateBoardCommentReqDTO updateBoardCommentReqDTO) {
        UpdateBoardCommentResDTO updateBoardCommentResDTO = boardCommentUseCase.updateBoardComment(updateBoardCommentReqDTO, commentId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.UPDATE_BOARD_COMMENT_SUCCESS, updateBoardCommentResDTO));
    }

    @DeleteMapping("/comment/{comment-id}")
    //댓글삭제(대댓글도 사용가능)
    public ResponseEntity<ResultResponse> deleteComment(@PathVariable(value = "comment-id") Long commentId) {
        boardCommentUseCase.deleteBoardComment(commentId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_BOARD_COMMENT_SUCCESS));
    }

    @PostMapping("/bookmark/{board-id}")
    //북마크 토글
    public ResponseEntity<ResultResponse> toggleBoardBookmark(@PathVariable(value = "board-id") Long boardId) {
        ToggleStateResDTO toggleStateResDTO = boardUseCase.toggleBoardBookMark(boardId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.TOGGLE_BOARD_BOOK_MARK_SUCCESS, toggleStateResDTO));
    }

    @GetMapping("/bookmark")
    //내가 북마크한 모든 게시물 조회
    public ResponseEntity<ResultResponse> getMyBookMarkedBoards(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<GetBoardsResDTO> getBoardsResDTO = boardUseCase.getMyBookMarkedBoards(pageable);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_MY_BOARD_BOOK_MARK_SUCCESS, getBoardsResDTO));
    }

    @PostMapping("/likes/{board-id}")
    //좋아요 토글
    public ResponseEntity<ResultResponse> toggleBoardLikes(@PathVariable(value = "board-id") Long boardId) {
        ToggleStateResDTO toggleStateResDTO = boardUseCase.toggleBoardLikes(boardId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.TOGGLE_BOARD_LIKES_SUCCESS, toggleStateResDTO));
    }

    @GetMapping("/likes")
    //내가 좋아요한 모든 게시물 조회
    public ResponseEntity<ResultResponse> getMyLikedBoards(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<GetBoardsResDTO> getBoardsResDTO = boardUseCase.getMyLikedBoards(pageable);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_MY_BOARD_LIKES_SUCCESS, getBoardsResDTO));
    }

    @GetMapping("/mine")
    //내가 방장인 모든 게시물 조회
    public ResponseEntity<ResultResponse> getMyBoards(@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        Page<GetBoardsResDTO> getBoardsResDTOS = boardUseCase.getMyBoards(pageable);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_MY_BOARD_SUCCESS, getBoardsResDTOS));
    }

    @PostMapping("/{board-id}/confirm")
    //여행확정짓기 (참여 확정짓기)
    public ResponseEntity<ResultResponse> confirmUsers(@PathVariable(value = "board-id")Long boardId,
                                                       @RequestBody ConfirmUsersReqDTO confirmUsersReqDTO){
        boardUseCase.confirmUsers(boardId, confirmUsersReqDTO);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.CONFIRM_USERS_SUCCESS));
    }

    @GetMapping("/{board-id}/companion")
    //단일 게시물에서 확정된 유저 조회
    public ResponseEntity<ResultResponse> getCompanions(@PathVariable(value = "board-id")Long boardId){
        List<GetCompanionsResDTO> getCompanionsResDTOS = boardUseCase.getCompanions(boardId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_COMPANIONS_SUCCESS, getCompanionsResDTOS));
    }

    //내가 참여하고있는 모든 게시물 조회

    @DeleteMapping("/{board-id}/leave")
    //참여하고있는 게시물에서 탈퇴
    public ResponseEntity<ResultResponse> leaveCompanion(@PathVariable(value = "board-id")Long boardId){
        boardUseCase.leaveCompanion(boardId);
        return ResponseEntity.ok(ResultResponse.of(ResultCode.LEAVE_COMPANION_SUCCESS));
    }


}
