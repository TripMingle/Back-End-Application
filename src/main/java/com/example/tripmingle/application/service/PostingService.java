package com.example.tripmingle.application.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.tripmingle.common.utils.UserUtils;
import com.example.tripmingle.dto.req.posting.GetAllPostingsReqDTO;
import com.example.tripmingle.dto.req.posting.GetPreviewPostingReqDTO;
import com.example.tripmingle.dto.req.posting.PatchPostingReqDTO;
import com.example.tripmingle.dto.req.posting.PostPostingReqDTO;
import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.PostingPersistPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostingService {
	private final PostingPersistPort postingPersistPort;
	private final UserUtils userUtils;

	public Posting createPosting(PostPostingReqDTO postPostingReqDTO, User currentUser) {
		Posting posting = Posting.builder()
			.title(postPostingReqDTO.getTitle())
			.content(postPostingReqDTO.getContent())
			.postingType(postPostingReqDTO.getPostingType())
			.country(postPostingReqDTO.getCountry())
			.user(currentUser)
			.build();
		return postingPersistPort.createPosting(posting);
	}

	public Posting updatePosting(PatchPostingReqDTO patchPostingReqDTO, User currentUser) {
		Posting posting = postingPersistPort.getPostingById(patchPostingReqDTO.getPostingId());
		userUtils.validateMasterUser(posting.getUser().getId(), currentUser.getId());
		posting.updatePosting(patchPostingReqDTO);
		return posting;
	}

	public void deletePosting(Posting posting, User currentUser) {
		userUtils.validateMasterUser(posting.getUser().getId(), currentUser.getId());
		posting.deletePosting();
	}

	public List<Posting> getPreviewPostings(GetPreviewPostingReqDTO getPreviewPostingReqDTO) {
		return postingPersistPort.findAllPostingForPreview(getPreviewPostingReqDTO.getCountry(),
			getPreviewPostingReqDTO.getPostingType());
	}

	public Posting getOnePosting(Long postingId) {
		return postingPersistPort.getPostingById(postingId);
	}

	public Posting getOnePostingWithPessimisticLock(Long postingId) {
		return postingPersistPort.getPostingByIdWithPessimisticLock(postingId);
	}

	public Page<Posting> getAllPostings(GetAllPostingsReqDTO getAllPostingsReqDTO, Pageable pageable) {
		return postingPersistPort.getAllPostings(getAllPostingsReqDTO.getCountry(),
			getAllPostingsReqDTO.getPostingType(), pageable);
	}

	public Page<Posting> getSearchPostings(String keyword, String postingType, Pageable pageable) {
		return postingPersistPort.getSearchPostings(keyword, postingType, pageable);
	}

	public Page<Posting> getAllPopularityPostings(GetAllPostingsReqDTO getAllPostingsReqDTO, Pageable pageable) {
		return postingPersistPort.getAllPopularityPostings(getAllPostingsReqDTO.getCountry(),
			getAllPostingsReqDTO.getPostingType(), pageable);
	}
}
