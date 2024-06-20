package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.posting.*;
import com.example.tripmingle.dto.res.posting.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostingUseCase {

    PostPostingResDTO createPosting(PostPostingReqDTO postPostingReqDTO);

    PatchPostingResDTO updatePosting(PatchPostingReqDTO patchPostingReqDTO);

    DeletePostingResDTO deletePosting(Long postingId);

    List<GetPostingsResDTO> getPreviewPostings(GetPreviewPostingReqDTO getPreviewPostingReqDTO);

    GetOnePostingResDTO getOnePosting(Long postingId);

    List<GetPostingsResDTO> getAllPostings(GetAllPostingsReqDTO getAllPostingsReqDTO, Pageable pageable);

    List<GetPostingsResDTO> getSearchPostings(String keyword, Pageable pageable);

    PostPostingCommentResDTO createPostingComment(PostPostingCommentReqDTO postPostingCommentReqDTO);

    PatchPostingCommentResDTO updatePostingComment(PatchPostingCommentReqDTO patchPostingCommentReqDTO);

    DeletePostingCommentResDTO deletePostingComment(Long commentId);

    PostingToggleStateResDTO togglePostingLikes(Long postingId);

    GetAllLikedPostingResDTO getMyLikedPostings(Pageable pageable);
}
