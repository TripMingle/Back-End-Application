package com.example.tripmingle.adapter.in;

import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.DeletePostingReqDTO;
import com.example.tripmingle.dto.req.PatchPostingReqDTO;
import com.example.tripmingle.dto.req.PostPostingReqDTO;
import com.example.tripmingle.dto.res.DeletePostingResDTO;
import com.example.tripmingle.dto.res.PatchPostingResDTO;
import com.example.tripmingle.dto.res.PostPostingResDTO;
import com.example.tripmingle.port.in.PostingCommentUseCase;
import com.example.tripmingle.port.in.PostingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.tripmingle.common.result.ResultCode.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posting")
public class PostingController {
    private final PostingUseCase postingUseCase;
    private final PostingCommentUseCase postingCommentUseCase;

    //포스트 미리보기(나라입력, 최근n개)
    public void getRecentPostings() {
        postingUseCase.getRecentPostings();
    }

    //포스트 전체조회(나라입력, 페이징)
    public void getAllPostings() {
        postingUseCase.getAllPostings();
    }

    //단일포스트 조회하기(댓글포함)
    public void getPostingInfo() {
        postingUseCase.getPostingInfo();
    }

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
        return ResponseEntity.ok().body(new ResultResponse(UPDATED_POSTING, patchPostingResDTO));
    }

    //포스트 삭제하기
    @PatchMapping("/delete")
    public ResponseEntity<ResultResponse> deletePosting(@RequestBody DeletePostingReqDTO deletePostingReqDTO) {
        DeletePostingResDTO deletePostingResDTO = postingUseCase.deletePosting(deletePostingReqDTO);
        return ResponseEntity.ok().body(new ResultResponse(DELETE_POSTING, deletePostingResDTO));
    }

    //포스트 댓글달기 (대댓글도 같은 API사용)
    public void createPostingComment() {
        postingCommentUseCase.createPostingComment();
    }

    //포스트 댓글수정 (대댓글도 같은 API사용)
    public void updatePostingComment() {
        postingCommentUseCase.updatePostingComment();
    }

    //포스트 댓글삭제 (대댓글도 같은 API사용)
    public void deletePostingComment() {
        postingCommentUseCase.deletePostingComment();
    }


}
