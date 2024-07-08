package com.example.tripmingle.adapter.in;

import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.posting.*;
import com.example.tripmingle.dto.res.posting.*;
import com.example.tripmingle.port.in.PostingCommentUseCase;
import com.example.tripmingle.port.in.PostingUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.tripmingle.common.result.ResultCode.*;

@Tag(name = "포스팅")
@RestController
@RequiredArgsConstructor
@RequestMapping("/postings")
@Slf4j
public class PostingController {
	private final PostingUseCase postingUseCase;
	private final PostingCommentUseCase postingCommentUseCase;

    //포스트 게시하기
    @Operation(summary = "포스트 작성")
    @PostMapping("")
    public ResponseEntity<ResultResponse> createPosting(@RequestBody PostPostingReqDTO postPostingReqDTO) {
        PostPostingResDTO postPostingResDTO = postingUseCase.createPosting(postPostingReqDTO);
        return ResponseEntity.ok(ResultResponse.of(CREATED_POSTING, postPostingResDTO));
    }

    //포스트 수정하기
    @Operation(summary = "포스트 수정")
    @PatchMapping("")
    public ResponseEntity<ResultResponse> updatePosting(@RequestBody PatchPostingReqDTO patchPostingReqDTO) {
        PatchPostingResDTO patchPostingResDTO = postingUseCase.updatePosting(patchPostingReqDTO);
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
    public ResponseEntity<ResultResponse> getPreviewPostings(@RequestBody GetPreviewPostingReqDTO getPreviewPostingReqDTO) {
        List<GetPostingsResDTO> getPreviewPostingsResDTOList = postingUseCase.getPreviewPostings(getPreviewPostingReqDTO);
        return ResponseEntity.ok(ResultResponse.of(POSTING_PREVIEW_SUCCESS, getPreviewPostingsResDTOList));
    }

    // 포스트 상세조회
    @Operation(summary = "포스트 상세 조회")
    @GetMapping("/{postingId}")
    public ResponseEntity<ResultResponse> getOnePosting(@PathVariable("postingId") Long postingId) {
        GetOnePostingResDTO getOnePostingResDTO = postingUseCase.getOnePosting(postingId);
        return ResponseEntity.ok(ResultResponse.of(GET_ONE_POSTING_SUCCESS, getOnePostingResDTO));
    }

    // 포스트 전체조회
    @Operation(summary = "포스트 전체 조회")
    @GetMapping("")
    public ResponseEntity<ResultResponse> getAllPostings(@RequestBody GetAllPostingsReqDTO getAllPostingsReqDTO,
                                                         @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        List<GetPostingsResDTO> getAllPostingsResDTOList = postingUseCase.getAllPostings(getAllPostingsReqDTO, pageable);
        return ResponseEntity.ok(ResultResponse.of(GET_ALL_POSTINGS_SUCCESS, getAllPostingsResDTOList));
    }

    // 포스트 검색 조회
    @Operation(summary = "포스트 검색 조회")
    @GetMapping("/search")
    public ResponseEntity<ResultResponse> getSearchPostings(@RequestParam("keyword") String keyword,
                                                            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        List<GetPostingsResDTO> getSearchPostingsResDTOList = postingUseCase.getSearchPostings(keyword, pageable);
        return ResponseEntity.ok(ResultResponse.of(GET_SEARCH_POSTINGS_SUCCESS, getSearchPostingsResDTOList));
    }

    // 포스트 댓글 달기
    @Operation(summary = "포스트 댓글 달기")
    @PostMapping("/comments")
    public ResponseEntity<ResultResponse> createPostingComment(@RequestBody PostPostingCommentReqDTO postPostingCommentReqDTO) {
        PostPostingCommentResDTO postPostingCommentResDTO = postingUseCase.createPostingComment(postPostingCommentReqDTO);
        return ResponseEntity.ok(ResultResponse.of(POST_POSTING_COMMENT_SUCCESS, postPostingCommentResDTO));
    }

    // 포스트 댓글 수정
    @Operation(summary = "포스트 댓글 수정")
    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<ResultResponse> updatePostingComment(@PathVariable("commentId") Long commentId, @RequestBody PatchPostingCommentReqDTO patchPostingCommentReqDTO) {
        patchPostingCommentReqDTO.setPostingCommentId(commentId);
        PatchPostingCommentResDTO patchPostingCommentResDTO = postingUseCase.updatePostingComment(patchPostingCommentReqDTO);
        return ResponseEntity.ok(ResultResponse.of(UPDATE_POSTING_COMMENT_SUCCESS, patchPostingCommentResDTO));
    }

    // 포스트 댓글 삭제
    @Operation(summary = "포스트 댓글 삭제")
    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ResultResponse> deletePostingComment(@PathVariable("commentId") Long commentId) {
        DeletePostingCommentResDTO deletePostingCommentResDTO = postingUseCase.deletePostingComment(commentId);
        return ResponseEntity.ok(ResultResponse.of(DELETE_POSTING_COMMENT_SUCCESS, deletePostingCommentResDTO));
    }

    // 포스트 좋아요
    @Operation(summary = "포스트 좋아요")
    @PostMapping("/likes/{postingId}")
    public ResponseEntity<ResultResponse> togglePostingLikes(@PathVariable("postingId") Long postingId) {
        PostingLikeToggleStateResDTO postingLikeToggleStateResDTO = postingUseCase.togglePostingLikes(postingId);
        return ResponseEntity.ok(ResultResponse.of(TOGGLE_POSTING_LIKES_SUCCESS, postingLikeToggleStateResDTO));
    }

    // 내가 좋아요한 포스트 조회
    @Operation(summary = "내가 좋아요한 포스트 조회")
    @GetMapping("/likes")
    public ResponseEntity<ResultResponse> getMyLikedPostings(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        GetAllLikedPostingResDTO getAllPostingsResDTOList = postingUseCase.getMyLikedPostings(pageable);
        return ResponseEntity.ok(ResultResponse.of(GET_ALL_LIKED_POSTING_SUCCESS, getAllPostingsResDTOList));
    }

}
