package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.DeletePostingReqDTO;
import com.example.tripmingle.dto.req.PatchPostingReqDTO;
import com.example.tripmingle.dto.req.PostPostingReqDTO;
import com.example.tripmingle.dto.res.DeletePostingResDTO;
import com.example.tripmingle.dto.res.PatchPostingResDTO;
import com.example.tripmingle.dto.res.PostPostingResDTO;

public interface PostingUseCase {

    void getRecentPostings();

    void getAllPostings();

    PostPostingResDTO createPosting(PostPostingReqDTO postPostingReqDTO);

    PatchPostingResDTO updatePosting(PatchPostingReqDTO patchPostingReqDTO);

    DeletePostingResDTO deletePosting(DeletePostingReqDTO deletePostingReqDTO);

    void getPostingInfo();
}
