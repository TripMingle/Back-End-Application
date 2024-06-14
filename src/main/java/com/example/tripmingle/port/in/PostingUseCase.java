package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.PostPostingReqDTO;
import com.example.tripmingle.dto.res.PostPostingResDTO;

public interface PostingUseCase {

    void getRecentPostings();

    void getAllPostings();

    PostPostingResDTO createPosting(PostPostingReqDTO postPostingReqDTO);

    void updatePosting();

    void deletePosting();

    void getPostingInfo();
}
