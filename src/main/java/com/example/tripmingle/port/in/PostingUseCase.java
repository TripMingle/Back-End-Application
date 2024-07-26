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

public interface PostingUseCase {

	GetOnePostingResDTO createPosting(PostPostingReqDTO postPostingReqDTO);

	GetOnePostingResDTO updatePosting(PatchPostingReqDTO patchPostingReqDTO);

	DeletePostingResDTO deletePosting(Long postingId);

	List<GetThumbNailPostingResDTO> getPreviewPostings(GetPreviewPostingReqDTO getPreviewPostingReqDTO);

	GetOnePostingResDTO getOnePosting(Long postingId);

	GetThumbNailPostingsResDTO getAllPostings(GetAllPostingsReqDTO getAllPostingsReqDTO, Pageable pageable);

	GetThumbNailPostingsResDTO getSearchPostings(String keyword, String postingType, Pageable pageable);

	GetOnePostingResDTO togglePostingLikes(Long postingId);

	GetAllLikedPostingResDTO getMyLikedPostings(Pageable pageable);

	GetThumbNailPostingsResDTO getAllPopularityPostings(GetAllPostingsReqDTO getAllPostingsReqDTO,
		Pageable pageable);
}
