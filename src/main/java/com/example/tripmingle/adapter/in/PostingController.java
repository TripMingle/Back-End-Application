package com.example.tripmingle.adapter.in;

import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.*;
import com.example.tripmingle.dto.res.*;
import com.example.tripmingle.port.in.PostingCommentUseCase;
import com.example.tripmingle.port.in.PostingUseCase;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.tripmingle.common.result.ResultCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/postings")
@Slf4j
public class PostingController {
    private final PostingUseCase postingUseCase;
    private final PostingCommentUseCase postingCommentUseCase;

    //포스트 게시하기
    @PostMapping("")
    public ResponseEntity<ResultResponse> createPosting(@RequestBody PostPostingReqDTO postPostingReqDTO) {
        PostPostingResDTO postPostingResDTO = postingUseCase.createPosting(postPostingReqDTO);
        return ResponseEntity.ok(ResultResponse.of(CREATED_POSTING, postPostingResDTO));
    }

    //포스트 수정하기
    @PatchMapping("")
    public ResponseEntity<ResultResponse> updatePosting(@RequestBody PatchPostingReqDTO patchPostingReqDTO) {
        PatchPostingResDTO patchPostingResDTO = postingUseCase.updatePosting(patchPostingReqDTO);
        return ResponseEntity.ok(ResultResponse.of(UPDATE_POSTING, patchPostingResDTO));
    }

    //포스트 삭제하기
    @DeleteMapping("")
    public ResponseEntity<ResultResponse> deletePosting(@RequestBody DeletePostingReqDTO deletePostingReqDTO) {
        DeletePostingResDTO deletePostingResDTO = postingUseCase.deletePosting(deletePostingReqDTO);
        return ResponseEntity.ok(ResultResponse.of(DELETE_POSTING, deletePostingResDTO));
    }

    @GetMapping("/preview")
    public ResponseEntity<ResultResponse> getPreviewPostings() {
        List<GetPreviewPostingResDTO> getPreviewPostingResDTOList = postingUseCase.getPreviewPostings();
        return ResponseEntity.ok(ResultResponse.of(POSTING_PREVIEW_SUCCESS, getPreviewPostingResDTOList));
    }

    @GetMapping("/{postingId}")
    public ResponseEntity<ResultResponse> getOnePosting(@PathVariable("postingId") Long postingId) {
        GetOnePostingResDTO getOnePostingResDTO = postingUseCase.getOnePosting(postingId);
        return ResponseEntity.ok(ResultResponse.of(GET_ONE_POSTING_SUCCESS, getOnePostingResDTO));
    }

    @GetMapping("")
    public ResponseEntity<ResultResponse> getAllPostings(@RequestParam("postingType") String postingType,
                                                         @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        List<GetAllPostingsResDTO> getAllPostingsResDTOSlice = postingUseCase.getAllPostings(postingType, pageable);
        return ResponseEntity.ok(ResultResponse.of(GET_ALL_POSTINGS_SUCCESS, getAllPostingsResDTOSlice));
    }

    @GetMapping("/search")
    public ResponseEntity<ResultResponse> getSearchPostings(@RequestParam("keyword") String keyword,
                                                            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        List<GetSearchPostingsResDTO> getSearchPostingsResDTOList = postingUseCase.getSearchPostings(keyword, pageable);
        return ResponseEntity.ok(ResultResponse.of(GET_SEARCH_POSTINGS_SUCCESS, getSearchPostingsResDTOList));
    }

    @PostMapping("/comments")
    public ResponseEntity<ResultResponse> createPostingComment(@RequestBody PostPostingCommentReqDTO postPostingCommentReqDTO) {
        PostPostingCommentResDTO postPostingCommentResDTO = postingUseCase.createPostingComment(postPostingCommentReqDTO);
        return ResponseEntity.ok(ResultResponse.of(POST_POSTING_COMMENT_SUCCESS, postPostingCommentResDTO));
    }

    @PatchMapping("/comments/{commentId}")
    public ResponseEntity<ResultResponse> updatePostingComment(@PathVariable("commentId") Long commentId, @RequestBody PatchPostingCommentReqDTO patchPostingCommentReqDTO) {
        patchPostingCommentReqDTO.setPostingCommentId(commentId);
        PatchPostingCommentResDTO patchPostingCommentResDTO = postingUseCase.updatePostingComment(patchPostingCommentReqDTO);
        return ResponseEntity.ok(ResultResponse.of(UPDATE_POSTING_COMMENT_SUCCESS, patchPostingCommentResDTO));
    }

    @DeleteMapping("/comments/{commentId}")
    public ResponseEntity<ResultResponse> deletePostingComment(@PathVariable("commentId") Long commentId) {
        DeletePostingCommentResDTO deletePostingCommentResDTO = postingUseCase.deletePostingComment(commentId);
        return ResponseEntity.ok(ResultResponse.of(DELETE_POSTING_COMMENT_SUCCESS, deletePostingCommentResDTO));
    }

    @PostMapping("/likes/{postingId}")
    public ResponseEntity<ResultResponse> togglePostingLikes(@PathVariable("postingId") Long postingId) {
        PostingToggleStateResDTO postingToggleStateResDTO = postingUseCase.togglePostingLikes(postingId);
        return ResponseEntity.ok(ResultResponse.of(TOGGLE_POSTING_LIKES_SUCCESS, postingToggleStateResDTO));
    }

    @GetMapping("/likes")
    public ResponseEntity<ResultResponse> getMyLikedPostings(@PageableDefault(size = 10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        GetAllLikedPostingResDTO getAllPostingsResDTOList = postingUseCase.getMyLikedPostings(pageable);
        return ResponseEntity.ok(ResultResponse.of(GET_ALL_LIKED_POSTING_SUCCESS, getAllPostingsResDTOList));
    }

}
