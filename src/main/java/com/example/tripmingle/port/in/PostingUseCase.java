package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.DeletePostingReqDTO;
import com.example.tripmingle.dto.req.PatchPostingReqDTO;
import com.example.tripmingle.dto.req.PostPostingReqDTO;
import com.example.tripmingle.dto.res.*;

import java.util.List;

public interface PostingUseCase {

    PostPostingResDTO createPosting(PostPostingReqDTO postPostingReqDTO);

    PatchPostingResDTO updatePosting(PatchPostingReqDTO patchPostingReqDTO);

    DeletePostingResDTO deletePosting(DeletePostingReqDTO deletePostingReqDTO);

    List<GetPreviewPostingResDTO> getPreviewPostings();

    GetOnePostingResDTO getOnePosting(Long postingId);
}
