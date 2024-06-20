package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.*;
import com.example.tripmingle.dto.res.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostingUseCase {

    PostPostingResDTO createPosting(PostPostingReqDTO postPostingReqDTO);

    PatchPostingResDTO updatePosting(PatchPostingReqDTO patchPostingReqDTO);

    DeletePostingResDTO deletePosting(DeletePostingReqDTO deletePostingReqDTO);

    List<GetPreviewPostingResDTO> getPreviewPostings(GetPreviewPostingReqDTO getPreviewPostingReqDTO);

    GetOnePostingResDTO getOnePosting(Long postingId);

    List<GetAllPostingsResDTO> getAllPostings(GetAllPostingsReqDTO getAllPostingsReqDTO, Pageable pageable);

    List<GetSearchPostingsResDTO> getSearchPostings(String keyword, Pageable pageable);

    PostPostingCommentResDTO createPostingComment(PostPostingCommentReqDTO postPostingCommentReqDTO);

    PatchPostingCommentResDTO updatePostingComment(PatchPostingCommentReqDTO patchPostingCommentReqDTO);

    DeletePostingCommentResDTO deletePostingComment(Long commentId);

    PostingToggleStateResDTO togglePostingLikes(Long postingId);

    GetAllLikedPostingResDTO getMyLikedPostings(Pageable pageable);
}
