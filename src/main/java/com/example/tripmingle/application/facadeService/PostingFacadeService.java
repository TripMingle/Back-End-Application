package com.example.tripmingle.application.facadeService;

import com.example.tripmingle.application.service.PostingCommentService;
import com.example.tripmingle.application.service.PostingService;
import com.example.tripmingle.dto.req.DeletePostingReqDTO;
import com.example.tripmingle.dto.req.PatchPostingReqDTO;
import com.example.tripmingle.dto.req.PostPostingReqDTO;
import com.example.tripmingle.dto.res.*;
import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.port.in.PostingCommentUseCase;
import com.example.tripmingle.port.in.PostingUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostingFacadeService implements PostingUseCase, PostingCommentUseCase {

    private final PostingService postingService;
    private final PostingCommentService postingCommentService;

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
}
