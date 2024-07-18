package com.example.tripmingle.port.in;

import java.util.List;

import org.springframework.data.domain.Pageable;

import com.example.tripmingle.dto.req.posting.GetAllPostingsReqDTO;
import com.example.tripmingle.dto.req.posting.GetPreviewPostingReqDTO;
import com.example.tripmingle.dto.req.posting.PatchPostingReqDTO;
import com.example.tripmingle.dto.req.posting.PostPostingReqDTO;
import com.example.tripmingle.dto.res.posting.DeletePostingResDTO;
import com.example.tripmingle.dto.res.posting.GetAllLikedPostingResDTO;
import com.example.tripmingle.dto.res.posting.GetOnePostingResDTO;
import com.example.tripmingle.dto.res.posting.GetThumbNailPostingResDTO;
import com.example.tripmingle.dto.res.posting.GetThumbNailPostingsResDTO;
import com.example.tripmingle.dto.res.posting.PatchPostingResDTO;
import com.example.tripmingle.dto.res.posting.PostPostingResDTO;
import com.example.tripmingle.dto.res.posting.PostingLikeToggleStateResDTO;

public interface PostingUseCase {

	PostPostingResDTO createPosting(PostPostingReqDTO postPostingReqDTO);

	PatchPostingResDTO updatePosting(PatchPostingReqDTO patchPostingReqDTO);

	DeletePostingResDTO deletePosting(Long postingId);

	List<GetThumbNailPostingResDTO> getPreviewPostings(GetPreviewPostingReqDTO getPreviewPostingReqDTO);

	GetOnePostingResDTO getOnePosting(Long postingId);

	GetThumbNailPostingsResDTO getAllPostings(GetAllPostingsReqDTO getAllPostingsReqDTO, Pageable pageable);

	GetThumbNailPostingsResDTO getSearchPostings(String keyword, String postingType, Pageable pageable);

	PostingLikeToggleStateResDTO togglePostingLikes(Long postingId);

	GetAllLikedPostingResDTO getMyLikedPostings(Pageable pageable);

	GetThumbNailPostingsResDTO getAllPopularityPostings(GetAllPostingsReqDTO getAllPostingsReqDTO,
		Pageable pageable);
}
