package com.example.tripmingle.application.facadeService;

import com.example.tripmingle.application.service.PostingCommentService;
import com.example.tripmingle.application.service.PostingService;
import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.dto.req.*;
import com.example.tripmingle.dto.res.*;
import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.port.in.PostingCommentUseCase;
import com.example.tripmingle.port.in.PostingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostingFacadeService implements PostingUseCase, PostingCommentUseCase {

    private final PostingService postingService;
    private final PostingCommentService postingCommentService;
    private final UserService userService;

    @Transactional
    @Override
    public PostPostingResDTO createPosting(PostPostingReqDTO postPostingReqDTO) {
        return postingService.createPosting(postPostingReqDTO);

    }

    @Transactional
    @Override
    public PatchPostingResDTO updatePosting(PatchPostingReqDTO patchPostingReqDTO) {
        return postingService.updatePosting(patchPostingReqDTO);
    }

    @Transactional
    @Override
    public DeletePostingResDTO deletePosting(DeletePostingReqDTO deletePostingReqDTO) {
        return postingService.deletePosting(deletePostingReqDTO);
    }

    @Override
    public List<GetPreviewPostingResDTO> getPreviewPostings() {
        return postingService.getPreviewPostings();
    }

    @Override
    public GetOnePostingResDTO getOnePosting(Long postingId) {
        Posting posting = postingService.getOnePosting(postingId);
        List<GetOnePostingCommentsResDTO> comments = postingCommentService.getPostingComments(postingId);
        return GetOnePostingResDTO.builder()
                .title(posting.getTitle())
                .content(posting.getContent())
                .createAt(posting.getCreatedAt())
                .heartCount(0L)
                .postingComments(comments)
                .userNickName(posting.getUser().getNickName())
                .userAgeRange(posting.getUser().getAgeRange())
                .userGender(posting.getUser().getGender())
                .userNationality(posting.getUser().getNationality())
                .selfIntroduce(posting.getUser().getSelfIntroduction())
                .userTemperature("0")
                .build();
    }

    @Override
    public List<GetAllPostingsResDTO> getAllPostings(String postingType, Pageable pageable) {
        return postingService.getAllPostings(postingType, pageable);
    }

    @Override
    public List<GetSearchPostingsResDTO> getSearchPostings(String keyword, Pageable pageable) {
        return postingService.getSearchPostings(keyword, pageable);
    }

    @Transactional
    @Override
    public PostPostingCommentResDTO createPostingComment(PostPostingCommentReqDTO postPostingCommentReqDTO) {
        Posting posting = postingService.getOnePosting(postPostingCommentReqDTO.getPostingId());
        Long newPostingCommentId = postingCommentService.createPostingComment(postPostingCommentReqDTO, posting);
        return PostPostingCommentResDTO.builder()
                .postingCommentId(newPostingCommentId)
                .build();
    }

    @Transactional
    @Override
    public PatchPostingCommentResDTO updatePostingComment(PatchPostingCommentReqDTO patchPostingCommentReqDTO) {
        Long postingComment = postingCommentService.updatePostingComment(patchPostingCommentReqDTO);
        return PatchPostingCommentResDTO.builder()
                .postingCommentId(postingComment)
                .build();
    }

    @Transactional
    @Override
    public DeletePostingCommentResDTO deletePostingComment(Long commentId) {
        Long postingCommentId = postingCommentService.deletePostingComment(commentId);
        return DeletePostingCommentResDTO.builder()
                .postingCommentId(postingCommentId)
                .build();
    }
}
