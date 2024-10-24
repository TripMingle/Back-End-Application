package com.example.tripmingle.adapter.in;

import static com.example.tripmingle.common.constants.Constants.*;
import static com.example.tripmingle.common.result.ResultCode.*;

import java.util.List;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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

import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.posting.DeletePostingCommentReqDTO;
import com.example.tripmingle.dto.req.posting.GetAllPostingsReqDTO;
import com.example.tripmingle.dto.req.posting.GetPreviewPostingReqDTO;
import com.example.tripmingle.dto.req.posting.PatchPostingCommentReqDTO;
import com.example.tripmingle.dto.req.posting.PatchPostingReqDTO;
import com.example.tripmingle.dto.req.posting.PostPostingCommentReqDTO;
import com.example.tripmingle.dto.req.posting.PostPostingReqDTO;
import com.example.tripmingle.dto.res.posting.DeletePostingResDTO;
import com.example.tripmingle.dto.res.posting.GetAllLikedPostingResDTO;
import com.example.tripmingle.dto.res.posting.GetOnePostingResDTO;
import com.example.tripmingle.dto.res.posting.GetThumbNailPostingResDTO;
import com.example.tripmingle.dto.res.posting.GetThumbNailPostingsResDTO;
import com.example.tripmingle.entity.PostingType;
import com.example.tripmingle.port.in.PostingCommentUseCase;
import com.example.tripmingle.port.in.PostingLockUseCase;
import com.example.tripmingle.port.in.PostingUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Tag(name = "포스팅")
@RestController
@RequiredArgsConstructor
@RequestMapping("/postings")
@Slf4j
public class PostingController {
	private final PostingUseCase postingUseCase;
	private final PostingLockUseCase postingLockUseCase;
	private final PostingCommentUseCase postingCommentUseCase;

	//포스트 게시하기
	@Operation(summary = "포스트 작성")
	@PostMapping
	public ResponseEntity<ResultResponse> createPosting(@RequestBody PostPostingReqDTO postPostingReqDTO) {
		GetOnePostingResDTO postPostingResDTO = postingUseCase.createPosting(postPostingReqDTO);
		return ResponseEntity.ok(ResultResponse.of(CREATED_POSTING, postPostingResDTO));
	}

	//포스트 수정하기
	@Operation(summary = "포스트 수정")
	@PatchMapping
	public ResponseEntity<ResultResponse> updatePosting(@RequestBody PatchPostingReqDTO patchPostingReqDTO) {
		GetOnePostingResDTO patchPostingResDTO = postingUseCase.updatePosting(patchPostingReqDTO);
		return ResponseEntity.ok(ResultResponse.of(UPDATE_POSTING, patchPostingResDTO));
	}

	//포스트 삭제하기
	@Operation(summary = "포스트 삭제")
	@DeleteMapping("/{postingId}")
	public ResponseEntity<ResultResponse> deletePosting(@PathVariable("postingId") Long postingId) {
		DeletePostingResDTO deletePostingResDTO = postingUseCase.deletePosting(postingId);
		return ResponseEntity.ok(ResultResponse.of(DELETE_POSTING, deletePostingResDTO));
	}

	// 포스트 미리보기
	@Operation(summary = "포스트 미리보기")
	@GetMapping("/preview")
	public ResponseEntity<ResultResponse> getPreviewPostings(@RequestParam("country") String country,
		@Parameter(description = "게시물 타입", example = "RESTAURANT, RENTAL_HOME, SCHEDULE") @RequestParam("postingType") String postingType) {
		GetPreviewPostingReqDTO getPreviewPostingReqDTO = GetPreviewPostingReqDTO.builder()
			.country(country)
			.postingType(PostingType.valueOf(postingType))
			.build();
		List<GetThumbNailPostingResDTO> getThumbNailPostingResDTOList = postingUseCase.getPreviewPostings(
			getPreviewPostingReqDTO);
		return ResponseEntity.ok(ResultResponse.of(POSTING_PREVIEW_SUCCESS, getThumbNailPostingResDTOList));
	}

	// 포스트 상세조회
	@Operation(summary = "포스트 상세 조회")
	@GetMapping("/{postingId}/details")
	public ResponseEntity<ResultResponse> getOnePosting(@PathVariable("postingId") Long postingId) {
		GetOnePostingResDTO getOnePostingResDTO = postingUseCase.getOnePosting(postingId);
		return ResponseEntity.ok(ResultResponse.of(GET_ONE_POSTING_SUCCESS, getOnePostingResDTO));
	}

	// 포스트 전체조회
	@Operation(summary = "포스트 전체 조회")
	@GetMapping
	public ResponseEntity<ResultResponse> getAllPostings(@RequestParam("country") String country,
		@Parameter(description = "게시물 타입", example = "RESTAURANT, RENTAL_HOME, SCHEDULE") @RequestParam("postingType") String postingType,
		@RequestParam(value = "page", defaultValue = "0") int page) {
		Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, SORT_CREATING_CRITERIA));
		GetAllPostingsReqDTO getAllPostingsReqDTO = GetAllPostingsReqDTO.builder()
			.country(country)
			.postingType(PostingType.valueOf(postingType))
			.build();
		GetThumbNailPostingsResDTO getAllPostingsResDTOList = postingUseCase.getAllPostings(getAllPostingsReqDTO,
			pageable);
		return ResponseEntity.ok(ResultResponse.of(GET_ALL_POSTINGS_SUCCESS, getAllPostingsResDTOList));
	}

	// 포스트 검색 조회
	@Operation(summary = "포스트 검색 조회")
	@GetMapping("/search")
	public ResponseEntity<ResultResponse> getSearchPostings(@RequestParam("keyword") String keyword,
		@Parameter(description = "게시물 타입", example = "RESTAURANT, RENTAL_HOME, SCHEDULE") @RequestParam("postingType") String postingType,
		@RequestParam(value = "page", defaultValue = "0") int page) {
		Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, SORT_CREATING_CRITERIA));
		GetThumbNailPostingsResDTO getSearchPostingsResDTOList = postingUseCase.getSearchPostings(keyword,
			postingType,
			pageable);
		return ResponseEntity.ok(ResultResponse.of(GET_SEARCH_POSTINGS_SUCCESS, getSearchPostingsResDTOList));
	}

	// 포스트 댓글 달기
	@Operation(summary = "포스트 댓글 달기")
	@PostMapping("{postingId}/comments")
	public ResponseEntity<ResultResponse> createPostingComment(@PathVariable("postingId") Long postingId,
		@RequestBody PostPostingCommentReqDTO postPostingCommentReqDTO) {
		postPostingCommentReqDTO.setPostingId(postingId);
		GetOnePostingResDTO postPostingCommentResDTO = postingLockUseCase.createPostingComment(
			postPostingCommentReqDTO);
		return ResponseEntity.ok(ResultResponse.of(POST_POSTING_COMMENT_SUCCESS, postPostingCommentResDTO));
	}

	// 포스트 댓글 수정
	@Operation(summary = "포스트 댓글 수정")
	@PatchMapping("{postingId}/comments/{commentId}")
	public ResponseEntity<ResultResponse> updatePostingComment(@PathVariable("postingId") Long postingId,
		@PathVariable("commentId") Long postCommentId,
		@RequestBody PatchPostingCommentReqDTO patchPostingCommentReqDTO) {
		patchPostingCommentReqDTO.setPostingId(postingId);
		patchPostingCommentReqDTO.setPostingCommentId(postCommentId);
		GetOnePostingResDTO patchPostingCommentResDTO = postingCommentUseCase.updatePostingComment(
			patchPostingCommentReqDTO);
		return ResponseEntity.ok(ResultResponse.of(UPDATE_POSTING_COMMENT_SUCCESS, patchPostingCommentResDTO));
	}

	// 포스트 댓글 삭제
	@Operation(summary = "포스트 댓글 삭제")
	@DeleteMapping("{postingId}/comments/{commentId}")
	public ResponseEntity<ResultResponse> deletePostingComment(@PathVariable("postingId") Long postingId,
		@PathVariable("commentId") Long commentId,
		@RequestBody DeletePostingCommentReqDTO deletePostingCommentReqDTO) {
		deletePostingCommentReqDTO.setPostingId(postingId);
		deletePostingCommentReqDTO.setPostingCommentId(commentId);
		GetOnePostingResDTO deletePostingCommentResDTO = postingCommentUseCase.deletePostingComment(
			deletePostingCommentReqDTO);
		return ResponseEntity.ok(ResultResponse.of(DELETE_POSTING_COMMENT_SUCCESS, deletePostingCommentResDTO));
	}

	// 포스트 좋아요
	@Operation(summary = "포스트 좋아요 버튼 누르기")
	@PostMapping("/{postingId}/likes")
	public ResponseEntity<ResultResponse> togglePostingLikes(@PathVariable("postingId") Long postingId) {
		GetOnePostingResDTO postingLikeToggleStateResDTO = postingUseCase.togglePostingLikes(postingId);
		return ResponseEntity.ok(ResultResponse.of(TOGGLE_POSTING_LIKES_SUCCESS, postingLikeToggleStateResDTO));
	}

	@Operation(summary = "포스트 인기도 순 조회")
	@GetMapping("/likes/popularity")
	public ResponseEntity<ResultResponse> getPopularityPostings(
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam("country") String country,
		@Parameter(description = "게시물 타입", example = "RESTAURANT, RENTAL_HOME, SCHEDULE") @RequestParam("postingType") String postingType) {
		Pageable pageable = PageRequest.of(page, PAGE_SIZE);
		GetAllPostingsReqDTO getAllPostingsReqDTO = GetAllPostingsReqDTO.builder()
			.country(country)
			.postingType(PostingType.valueOf(postingType))
			.build();
		GetThumbNailPostingsResDTO getAllPopularityPostingsResDTOList = postingUseCase.getAllPopularityPostings(
			getAllPostingsReqDTO,
			pageable);
		return ResponseEntity.ok(
			ResultResponse.of(GET_ALL_POPULARITY_POSTINGS_SUCCESS, getAllPopularityPostingsResDTOList));
	}

	// 내가 좋아요한 포스트 조회
	@Operation(summary = "내가 좋아요한 포스트 조회")
	@GetMapping("/my-likes")
	public ResponseEntity<ResultResponse> getMyLikedPostings(
		@RequestParam(value = "page", defaultValue = "0") int page) {
		Pageable pageable = PageRequest.of(page, PAGE_SIZE, Sort.by(Sort.Direction.DESC, SORT_ID_CRITERIA));
		GetAllLikedPostingResDTO getAllPostingsResDTOList = postingUseCase.getMyLikedPostings(pageable);
		return ResponseEntity.ok(ResultResponse.of(GET_ALL_LIKED_POSTING_SUCCESS, getAllPostingsResDTOList));
	}

}
