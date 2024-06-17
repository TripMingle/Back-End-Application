package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.DeletePostingReqDTO;
import com.example.tripmingle.dto.req.PatchPostingReqDTO;
import com.example.tripmingle.dto.req.PostPostingReqDTO;
import com.example.tripmingle.dto.res.*;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostingUseCase {

    PostPostingResDTO createPosting(PostPostingReqDTO postPostingReqDTO);

    PatchPostingResDTO updatePosting(PatchPostingReqDTO patchPostingReqDTO);

    DeletePostingResDTO deletePosting(DeletePostingReqDTO deletePostingReqDTO);

    List<GetPreviewPostingResDTO> getPreviewPostings();

    GetOnePostingResDTO getOnePosting(Long postingId);

    List<GetAllPostingsResDTO> getAllPostings(String postingType, Pageable pageable);
}
