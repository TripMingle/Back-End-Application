package com.example.tripmingle.application.service;


import com.example.tripmingle.common.exception.PostingInvalidUserException;
import com.example.tripmingle.dto.req.DeletePostingReqDTO;
import com.example.tripmingle.dto.req.PatchPostingReqDTO;
import com.example.tripmingle.dto.req.PostPostingReqDTO;
import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.PostingType;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.PostingPersistPort;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.tripmingle.common.error.ErrorCode.POSTING_INVALID_USER;

@Service
@RequiredArgsConstructor
public class PostingService {
    private final PostingPersistPort postingPersistPort;
    private final UserPersistPort userPersistPort;

    public Long createPosting(PostPostingReqDTO postPostingReqDTO) {
        User user = userPersistPort.findCurrentUserByEmail();
        Posting posting = Posting.builder()
                .title(postPostingReqDTO.getTitle())
                .content(postPostingReqDTO.getContent())
                .postingType(postPostingReqDTO.getPostingType())
                .user(user)
                .build();
        return postingPersistPort.createPosting(posting);
    }

    public Long updatePosting(PatchPostingReqDTO patchPostingReqDTO) {
        Posting posting = postingPersistPort.getPostingById(patchPostingReqDTO.getPostingId());
        if (validatePostingMasterUser(posting.getUser().getId())) {
            posting.updatePosting(patchPostingReqDTO);
        }
        return posting.getId();
    }

    private boolean validatePostingMasterUser(Long rawUserId) {
        User user = userPersistPort.findCurrentUserByEmail();
        if (rawUserId.equals(user.getId())) {
            return true;
        } else {
            throw new PostingInvalidUserException("Invalid User For Posting.", POSTING_INVALID_USER);
        }
    }

    public Long deletePosting(DeletePostingReqDTO deletePostingReqDTO) {
        Posting posting = postingPersistPort.getPostingById(deletePostingReqDTO.getPostingId());
        if (validatePostingMasterUser(posting.getUser().getId())) {
            posting.deletePosting();
        }
        return posting.getId();
    }

    public List<Posting> getPreviewPostings() {
        return postingPersistPort.findAllPostingForPreview();
    }

    public Posting getOnePosting(Long postingId) {
        return postingPersistPort.getPostingById(postingId);
    }

    public Page<Posting> getAllPostings(String postingType, Pageable pageable) {
        return postingPersistPort.getAllPostings(PostingType.valueOf(postingType), pageable);
    }

    public Page<Posting> getSearchPostings(String keyword, Pageable pageable) {
        return postingPersistPort.getSearchPostings(keyword, pageable);
    }
}
