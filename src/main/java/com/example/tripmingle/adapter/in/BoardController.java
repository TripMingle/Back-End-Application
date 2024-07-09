package com.example.tripmingle.adapter.in;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripmingle.common.result.ResultCode;
import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.board.ConfirmUsersReqDTO;
import com.example.tripmingle.dto.req.board.CreateBoardCommentReqDTO;
import com.example.tripmingle.dto.req.board.CreateBoardReqDTO;
import com.example.tripmingle.dto.req.board.GetAllBoardReqDTO;
import com.example.tripmingle.dto.req.board.UpdateBoardCommentReqDTO;
import com.example.tripmingle.dto.req.board.UpdateBoardReqDTO;
import com.example.tripmingle.dto.res.board.CreateBoardCommentResDTO;
import com.example.tripmingle.dto.res.board.GetBoardInfoResDTO;
import com.example.tripmingle.dto.res.board.GetBoardsResDTO;
import com.example.tripmingle.dto.res.board.GetCompanionsResDTO;
import com.example.tripmingle.dto.res.board.PostBoardResDTO;
import com.example.tripmingle.dto.res.board.ToggleStateResDTO;
import com.example.tripmingle.dto.res.board.UpdateBoardCommentResDTO;
import com.example.tripmingle.dto.res.board.UpdateBoardResDTO;
import com.example.tripmingle.port.in.BoardCommentUseCase;
import com.example.tripmingle.port.in.BoardUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "동행구인")
@RestController
@RequestMapping("/board")
@RequiredArgsConstructor
public class BoardController {
	private final BoardUseCase boardUseCase;
	private final BoardCommentUseCase boardCommentUseCase;

	@Operation(summary = "게시물 미리보기 리스트")
	@GetMapping("/preview/{country-name}")
	//게시물 미리보기 리스트(나라명 입력, 최신순 n개)
	public ResponseEntity<ResultResponse> getRecentBoards(@PathVariable(value = "country-name") String countryName) {
		List<GetBoardsResDTO> getBoardsResDTO = boardUseCase.getRecentBoards(countryName);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_BOARD_PREVIEW_SUCCESS, getBoardsResDTO));
	}

	@Operation(summary = "게시물 전체조회(성별이나 언어로 필터링)")
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

	@Operation(summary = "단일게시물 조회")
	@GetMapping("/show/{board-id}")
	//단일게시물 조회 (상세내용 + 댓글까지 + 유저정보 + 태그)
	public ResponseEntity<ResultResponse> getBoard(@PathVariable(value = "board-id") Long boardId) {
		GetBoardInfoResDTO getBoardInfoResDTO = boardUseCase.getBoard(boardId);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_SINGLE_BOARD_SUCCESS, getBoardInfoResDTO));
	}

	@Operation(summary = "게시글 작성")
	@PostMapping()
	//게시글 작성
	public ResponseEntity<ResultResponse> createBoard(@RequestBody CreateBoardReqDTO createBoardReqDTO) {
		PostBoardResDTO postBoardResDTO = boardUseCase.createBoard(createBoardReqDTO);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.CREATE_BOARD_SUCCESS, postBoardResDTO));
	}

	@Operation(summary = "게시글 수정")
	@PatchMapping("/{board-id}")
	//게시글 수정
	public ResponseEntity<ResultResponse> updateBoard(@PathVariable(value = "board-id") Long boardId,
		@RequestBody UpdateBoardReqDTO updateBoardReqDTO) {
		UpdateBoardResDTO updateBoardResDTO = boardUseCase.updateBoard(boardId, updateBoardReqDTO);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.UPDATE_BOARD_SUCCESS, updateBoardResDTO));
	}

	@Operation(summary = "게시글 삭제")
	@DeleteMapping("/{board-id}")
	//게시글 삭제
	public ResponseEntity<ResultResponse> deleteBoard(@PathVariable(value = "board-id") Long boardId) {
		boardUseCase.deleteBoard(boardId);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_BOARD_SUCCESS));
	}

	@Operation(summary = "게시글 검색")
	@GetMapping("/search/{country-name}")
	//게시글 검색
	public ResponseEntity<ResultResponse> searchBoard(@PathVariable(value = "country-name") String countryName
		, @RequestParam String keyword
		, @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<GetBoardsResDTO> getBoardsResDTOS = boardUseCase.searchBoard(countryName, keyword, pageable);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.SEARCH_BOARD_SUCCESS, getBoardsResDTOS));
	}

	@Operation(summary = "댓글 작성")
	@PostMapping("/comment")
	//댓글달기 (대댓글달기도 같은 API 사용)
	public ResponseEntity<ResultResponse> createComment(
		@RequestBody CreateBoardCommentReqDTO createBoardCommentReqDTO) {
		CreateBoardCommentResDTO createBoardCommentResDTO = boardCommentUseCase.createBoardComment(
			createBoardCommentReqDTO);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.CREATE_BOARD_COMMENT_SUCCESS, createBoardCommentResDTO));
	}

	@Operation(summary = "댓글 수정")
	@PatchMapping("/comment/{comment-id}")
	//댓글수정(대댓글도 같은 API 사용)
	public ResponseEntity<ResultResponse> updateComment(@PathVariable(value = "comment-id") Long commentId,
		@RequestBody UpdateBoardCommentReqDTO updateBoardCommentReqDTO) {
		UpdateBoardCommentResDTO updateBoardCommentResDTO = boardCommentUseCase.updateBoardComment(
			updateBoardCommentReqDTO, commentId);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.UPDATE_BOARD_COMMENT_SUCCESS, updateBoardCommentResDTO));
	}

	@Operation(summary = "댓글 삭제")
	@DeleteMapping("/comment/{comment-id}")
	//댓글삭제(대댓글도 사용가능)
	public ResponseEntity<ResultResponse> deleteComment(@PathVariable(value = "comment-id") Long commentId) {
		boardCommentUseCase.deleteBoardComment(commentId);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.DELETE_BOARD_COMMENT_SUCCESS));
	}

	@Operation(summary = "북마크 토글")
	@PostMapping("/bookmark/{board-id}")
	//북마크 토글
	public ResponseEntity<ResultResponse> toggleBoardBookmark(@PathVariable(value = "board-id") Long boardId) {
		ToggleStateResDTO toggleStateResDTO = boardUseCase.toggleBoardBookMark(boardId);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.TOGGLE_BOARD_BOOK_MARK_SUCCESS, toggleStateResDTO));
	}

	@Operation(summary = "내가 북마크한 게시물 조회")
	@GetMapping("/bookmark")
	//내가 북마크한 모든 게시물 조회
	public ResponseEntity<ResultResponse> getMyBookMarkedBoards(
		@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<GetBoardsResDTO> getBoardsResDTO = boardUseCase.getMyBookMarkedBoards(pageable);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_MY_BOARD_BOOK_MARK_SUCCESS, getBoardsResDTO));
	}

	@Operation(summary = "좋아요 토글")
	@PostMapping("/likes/{board-id}")
	//좋아요 토글
	public ResponseEntity<ResultResponse> toggleBoardLikes(@PathVariable(value = "board-id") Long boardId) {
		ToggleStateResDTO toggleStateResDTO = boardUseCase.toggleBoardLikes(boardId);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.TOGGLE_BOARD_LIKES_SUCCESS, toggleStateResDTO));
	}

	@Operation(summary = "내가 좋아요한 게시물 조회")
	@GetMapping("/likes")
	//내가 좋아요한 모든 게시물 조회
	public ResponseEntity<ResultResponse> getMyLikedBoards(
		@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<GetBoardsResDTO> getBoardsResDTO = boardUseCase.getMyLikedBoards(pageable);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_MY_BOARD_LIKES_SUCCESS, getBoardsResDTO));
	}

	@Operation(summary = "내가 방장인 동행게시물 조회")
	@GetMapping("/mine")
	//내가 방장인 모든 게시물 조회
	public ResponseEntity<ResultResponse> getMyBoards(
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<GetBoardsResDTO> getBoardsResDTOS = boardUseCase.getMyBoards(pageable);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_MY_BOARD_SUCCESS, getBoardsResDTOS));
	}

	@Operation(summary = "여행 확정짓기")
	@PostMapping("/{board-id}/confirm")
	//여행확정짓기 (참여 확정짓기)
	public ResponseEntity<ResultResponse> confirmUsers(@PathVariable(value = "board-id") Long boardId,
		@RequestBody ConfirmUsersReqDTO confirmUsersReqDTO) {
		boardUseCase.confirmUsers(boardId, confirmUsersReqDTO);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.CONFIRM_USERS_SUCCESS));
	}

	@Operation(summary = "단일 게시물에서 여행 확정된 유저 조회")
	@GetMapping("/{board-id}/companion")
	//단일 게시물에서 확정된 유저 조회
	public ResponseEntity<ResultResponse> getCompanions(@PathVariable(value = "board-id") Long boardId) {
		List<GetCompanionsResDTO> getCompanionsResDTOS = boardUseCase.getCompanions(boardId);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_COMPANIONS_SUCCESS, getCompanionsResDTOS));
	}

	@Operation(summary = "내가 참여하고있는 동행게시물 조회")
	@GetMapping("/my-companion")
	//내가 참여하고있는 모든 게시물 조회
	public ResponseEntity<ResultResponse> getMyCompanionBoards(
		@PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
		Page<GetBoardsResDTO> getBoardsResDTOS = boardUseCase.getMyCompanionBoards(pageable);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_MY_COMPANIONS_BOARDS_SUCCESS, getBoardsResDTOS));
	}

	@Operation(summary = "참여하고 있는 동행 게시물에서 탈퇴")
	@DeleteMapping("/{board-id}/leave")
	//참여하고있는 게시물에서 탈퇴
	public ResponseEntity<ResultResponse> leaveCompanion(@PathVariable(value = "board-id") Long boardId) {
		boardUseCase.leaveCompanion(boardId);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.LEAVE_COMPANION_SUCCESS));
	}

}
