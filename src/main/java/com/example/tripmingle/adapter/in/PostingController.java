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
    @PostMapping("/post")
    public ResponseEntity<ResultResponse> createPosting(@RequestBody PostPostingReqDTO postPostingReqDTO) {
        PostPostingResDTO postPostingResDTO = postingUseCase.createPosting(postPostingReqDTO);
        return ResponseEntity.ok().body(new ResultResponse(CREATED_POSTING, postPostingResDTO));
    }

    //포스트 수정하기
    @PatchMapping("/update")
    public ResponseEntity<ResultResponse> updatePosting(@RequestBody PatchPostingReqDTO patchPostingReqDTO) {
        PatchPostingResDTO patchPostingResDTO = postingUseCase.updatePosting(patchPostingReqDTO);
        return ResponseEntity.ok().body(new ResultResponse(UPDATE_POSTING, patchPostingResDTO));
    }

    //포스트 삭제하기
    @PatchMapping("/delete")
    public ResponseEntity<ResultResponse> deletePosting(@RequestBody DeletePostingReqDTO deletePostingReqDTO) {
        DeletePostingResDTO deletePostingResDTO = postingUseCase.deletePosting(deletePostingReqDTO);
        return ResponseEntity.ok().body(new ResultResponse(DELETE_POSTING, deletePostingResDTO));
    }

    @GetMapping("/get/preview")
    public ResponseEntity<ResultResponse> getPreviewPostings() {
        List<GetPreviewPostingResDTO> getPreviewPostingResDTOList = postingUseCase.getPreviewPostings();
        return ResponseEntity.ok().body(new ResultResponse(POSTING_PREVIEW_SUCCESS, getPreviewPostingResDTOList));
    }

    @GetMapping("/get/{postingId}")
    public ResponseEntity<ResultResponse> getOnePosting(@PathVariable("postingId") Long postingId) {
        GetOnePostingResDTO getOnePostingResDTO = postingUseCase.getOnePosting(postingId);
        return ResponseEntity.ok().body(new ResultResponse(GET_ONE_POSTING_SUCCESS, getOnePostingResDTO));
    }

    @GetMapping("/get")
    public ResponseEntity<ResultResponse> getAllPostings(@RequestParam("postingType") String postingType,
                                                         @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        List<GetAllPostingsResDTO> getAllPostingsResDTOSlice = postingUseCase.getAllPostings(postingType, pageable);
        return ResponseEntity.ok().body(new ResultResponse(GET_ALL_POSTINGS_SUCCESS, getAllPostingsResDTOSlice));
    }

    @GetMapping("/get/search")
    public ResponseEntity<ResultResponse> getSearchPostings(@RequestParam("keyword") String keyword,
                                                            @PageableDefault(size = 10, sort = "createdAt", direction = Sort.Direction.DESC) Pageable pageable) {
        List<GetSearchPostingsResDTO> getSearchPostingsResDTOList = postingUseCase.getSearchPostings(keyword, pageable);
        return ResponseEntity.ok().body(new ResultResponse(GET_SEARCH_POSTINGS_SUCCESS, getSearchPostingsResDTOList));
    }

    @PostMapping("/post/comments")
    public ResponseEntity<ResultResponse> createPostingComment(@RequestBody PostPostingCommentReqDTO postPostingCommentReqDTO) {
        PostPostingCommentResDTO postPostingCommentResDTO = postingUseCase.createPostingComment(postPostingCommentReqDTO);
        return ResponseEntity.ok().body(ResultResponse.of(POST_POSTING_COMMENT_SUCCESS, postPostingCommentResDTO));
    }

    @PatchMapping("/update/comments/{commentId}")
    public ResponseEntity<ResultResponse> updatePostingComment(@PathVariable("commentId") Long commentId, @RequestBody PatchPostingCommentReqDTO patchPostingCommentReqDTO) {
        patchPostingCommentReqDTO.setPostingCommentId(commentId);
        PatchPostingCommentResDTO patchPostingCommentResDTO = postingUseCase.updatePostingComment(patchPostingCommentReqDTO);
        return ResponseEntity.ok().body(ResultResponse.of(UPDATE_POSTING_COMMENT_SUCCESS, patchPostingCommentResDTO));
    }

}
