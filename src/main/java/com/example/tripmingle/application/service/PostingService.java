package com.example.tripmingle.application.service;


import com.example.tripmingle.dto.req.DeletePostingReqDTO;
import com.example.tripmingle.dto.req.PatchPostingReqDTO;
import com.example.tripmingle.dto.req.PostPostingReqDTO;
import com.example.tripmingle.dto.res.*;
import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.PostingType;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.PostingPersistPort;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PostingService {
    private final PostingPersistPort postingPersistPort;
    private final UserPersistPort userPersistPort;

    public PostPostingResDTO createPosting(PostPostingReqDTO postPostingReqDTO) {
        User user = userPersistPort.findCurrentUserByEmail();
        Posting posting = Posting.builder()
                .title(postPostingReqDTO.getTitle())
                .content(postPostingReqDTO.getContent())
                .postingType(postPostingReqDTO.getPostingType())
                .user(user)
                .build();
        Long postingId = postingPersistPort.createPosting(posting);
        return PostPostingResDTO.builder()
                .postingId(postingId)
                .build();
    }

    public PatchPostingResDTO updatePosting(PatchPostingReqDTO patchPostingReqDTO) {
        User user = userPersistPort.findCurrentUserByEmail();
        Posting posting = postingPersistPort.getPostingById(patchPostingReqDTO.getPostingId());

        if (user.getId().equals(posting.getUser().getId())) {
            posting.updatePosting(patchPostingReqDTO);
        }
        return PatchPostingResDTO.builder()
                .postingId(posting.getId())
                .build();
    }

    public DeletePostingResDTO deletePosting(DeletePostingReqDTO deletePostingReqDTO) {
        User user = userPersistPort.findCurrentUserByEmail();
        Posting posting = postingPersistPort.getPostingById(deletePostingReqDTO.getPostingId());

        if (user.getId().equals(posting.getUser().getId())) {
            posting.deletePosting();
        }
        return DeletePostingResDTO.builder()
                .postingId(posting.getId())
                .build();
    }

    public List<GetPreviewPostingResDTO> getPreviewPostings() {
        List<Posting> postings = postingPersistPort.findAllPostingForPreview();

        return postings.stream()
                .map(posting -> GetPreviewPostingResDTO.builder()
                        .postingId(posting.getId())
                        .title(posting.getTitle())
                        .content(posting.getContent())
                        .userNickName(posting.getUser().getNickName())
                        .userAgeRange(posting.getUser().getAgeRange())
                        .userGender(posting.getUser().getGender())
                        .userNationality(posting.getUser().getNationality())
                        .build())
                .collect(Collectors.toList());
    }

    public Posting getOnePosting(Long postingId) {
        return postingPersistPort.getPostingById(postingId);
    }

    public List<GetAllPostingsResDTO> getAllPostings(String postingType, Pageable pageable) {
        Slice<Posting> getAllPostings = postingPersistPort.getAllPostings(PostingType.valueOf(postingType), pageable);
        return getAllPostings.stream()
                .map(posting -> GetAllPostingsResDTO.builder()
                        .postingId(posting.getId())
                        .title(posting.getTitle())
                        .content(posting.getContent())
                        .userNickName(posting.getUser().getNickName())
                        .userAgeRange(posting.getUser().getAgeRange())
                        .userGender(posting.getUser().getGender())
                        .userNationality(posting.getUser().getNationality())
                        .build())
                .collect(Collectors.toList());
    }
}
