package com.example.tripmingle.application.facadeService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tripmingle.application.service.PostingCommentService;
import com.example.tripmingle.application.service.PostingLikesService;
import com.example.tripmingle.application.service.PostingService;
import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.InvalidPostingCommentAccess;
import com.example.tripmingle.common.exception.InvalidUserAccessException;
import com.example.tripmingle.dto.req.posting.DeletePostingCommentReqDTO;
import com.example.tripmingle.dto.req.posting.GetAllPostingsReqDTO;
import com.example.tripmingle.dto.req.posting.GetPreviewPostingReqDTO;
import com.example.tripmingle.dto.req.posting.PatchPostingCommentReqDTO;
import com.example.tripmingle.dto.req.posting.PatchPostingReqDTO;
import com.example.tripmingle.dto.req.posting.PostPostingCommentReqDTO;
import com.example.tripmingle.dto.req.posting.PostPostingReqDTO;
import com.example.tripmingle.dto.res.posting.DeletePostingResDTO;
import com.example.tripmingle.dto.res.posting.GetAllLikedPostingResDTO;
import com.example.tripmingle.dto.res.posting.GetOnePostingCoCommentResDTO;
import com.example.tripmingle.dto.res.posting.GetOnePostingCommentsResDTO;
import com.example.tripmingle.dto.res.posting.GetOnePostingResDTO;
import com.example.tripmingle.dto.res.posting.GetThumbNailPostingResDTO;
import com.example.tripmingle.dto.res.posting.GetThumbNailPostingsResDTO;
import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.PostingComment;
import com.example.tripmingle.entity.PostingLikes;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.in.PostingCommentUseCase;
import com.example.tripmingle.port.in.PostingUseCase;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PostingFacadeService implements PostingUseCase, PostingCommentUseCase {

	private final PostingService postingService;
	private final PostingCommentService postingCommentService;
	private final PostingLikesService postingLikesService;
	private final UserService userService;

	@Transactional
	@Override
	public GetOnePostingResDTO createPosting(PostPostingReqDTO postPostingReqDTO) {
		User currentUser = userService.getCurrentUser();
		Posting newPosting = postingService.createPosting(postPostingReqDTO, currentUser);
		return generateGetOnePostingResDTO(newPosting, currentUser);
	}

	@Transactional
	@Override
	public GetOnePostingResDTO updatePosting(PatchPostingReqDTO patchPostingReqDTO) {
		User currentUser = userService.getCurrentUser();
		Posting posting = postingService.updatePosting(patchPostingReqDTO, currentUser);
		return generateGetOnePostingResDTO(posting, currentUser);
	}

	@Transactional(readOnly = true)
	@Override
	public GetOnePostingResDTO getOnePosting(Long postingId) {
		User currentUser = userService.getCurrentUser();
		Posting posting = postingService.getOnePosting(postingId);
		return generateGetOnePostingResDTO(posting, currentUser);
	}

	@Transactional
	@Override
	public GetOnePostingResDTO createPostingComment(PostPostingCommentReqDTO postPostingCommentReqDTO) {
		User currentUser = userService.getCurrentUser();
		Posting posting = postingService.getOnePostingWithPessimisticLock(postPostingCommentReqDTO.getPostingId());
		validatePostingCommentBelongsToPosting(posting, postPostingCommentReqDTO);
		postingCommentService.createPostingComment(postPostingCommentReqDTO, posting, currentUser);
		return generateGetOnePostingResDTO(posting, currentUser);
	}

	@Transactional
	@Override
	public GetOnePostingResDTO updatePostingComment(PatchPostingCommentReqDTO patchPostingCommentReqDTO) {
		User currentUser = userService.getCurrentUser();
		postingCommentService.updatePostingComment(patchPostingCommentReqDTO, currentUser);
		Posting posting = postingService.getOnePosting(patchPostingCommentReqDTO.getPostingId());
		return generateGetOnePostingResDTO(posting, currentUser);
	}

	@Transactional
	@Override
	public GetOnePostingResDTO deletePostingComment(DeletePostingCommentReqDTO deletePostingCommentReqDTO) {
		User currentUser = userService.getCurrentUser();
		Posting posting = postingService.getOnePostingWithPessimisticLock(deletePostingCommentReqDTO.getPostingId());
		int deletePostingCommentCount = postingCommentService.deletePostingComment(
			deletePostingCommentReqDTO.getPostingCommentId(), currentUser);
		posting.decreasePostingCommentCount(deletePostingCommentCount);
		return generateGetOnePostingResDTO(posting, currentUser);
	}

	private GetOnePostingResDTO generateGetOnePostingResDTO(Posting posting, User user) {
		boolean postingLikesState = postingLikesService.getPostingLikesState(posting, user);
		List<PostingComment> postingComments = postingCommentService.getPostingComments(posting.getId());
		List<GetOnePostingCommentsResDTO> commentsInOnePosting = getCommentsInPosting(postingComments, user);
		return GetOnePostingResDTO.builder()
			.postingId(posting.getId())
			.title(posting.getTitle())
			.content(posting.getContent())
			.country(posting.getCountry())
			.createAt(posting.getCreatedAt())
			.postingComments(commentsInOnePosting)
			.userImageUrl(posting.getUser().getUserImageUrl() == null ? "" : posting.getUser().getUserImageUrl())
			.userNickName(posting.getUser().getNickName())
			.userAgeRange(posting.getUser().getAgeRange())
			.userGender(posting.getUser().getGender())
			.userNationality(posting.getUser().getNationality())
			.selfIntroduce(
				posting.getUser().getSelfIntroduction() == null ? "" : posting.getUser().getSelfIntroduction())
			.isMine(posting.getUser().getId().equals(user.getId()))
			.userTemperature(posting.getUser().getUserScore())
			.myLikeState(postingLikesState)
			.commentCount(posting.getCommentCount())
			.likeCount(postingLikesService.getPostingTotalLikeCount(posting.getId()))
			.build();
	}

	private List<GetOnePostingCommentsResDTO> getCommentsInPosting(List<PostingComment> postingComments,
		User currentUser) {
		return postingComments.stream().filter(filter -> filter.isParentComment())
			.map(comments -> GetOnePostingCommentsResDTO.builder()
				.commentId(comments.getId())
				.userImageUrl(comments.getUser().getUserImageUrl() == null ? "" : comments.getUser().getUserImageUrl())
				.userNickName(comments.getUser().getNickName())
				.comment(comments.getComment())
				.createdAt(comments.getCreatedAt())
				.isMine(comments.getUser().getId().equals(currentUser.getId()))
				.postingCoComment(postingComments.stream()
					.filter(filter -> !filter.isParentComment() && filter.getPostingComment()
						.getId()
						.equals(comments.getId()))
					.map(cocomments -> GetOnePostingCoCommentResDTO.builder()
						.coCommentId(cocomments.getId())
						.parentCommentId(comments.getId())
						.userImageUrl(cocomments.getUser().getUserImageUrl() == null ? "" :
							cocomments.getUser().getUserImageUrl())
						.userNickName(cocomments.getUser().getNickName())
						.coComment(cocomments.getComment())
						.createdAt(cocomments.getCreatedAt())
						.isMine(cocomments.getUser().getId().equals(currentUser.getId()))
						.build()).collect(Collectors.toList()))
				.build()).toList();
	}

	private void validatePostingCommentBelongsToPosting(Posting posting,
		PostPostingCommentReqDTO postPostingCommentReqDTO) {
		if (!postPostingCommentReqDTO.getParentCommentId().equals(-1L)) {
			PostingComment postingComment = postingCommentService.getPostingCommentsByPostingCommentId(
				postPostingCommentReqDTO.getParentCommentId());
			comparePostingWithPostingInPostingComment(posting, postingComment);
		}
	}

	private void comparePostingWithPostingInPostingComment(Posting posting, PostingComment postingComment) {
		if (!posting.getId().equals(postingComment.getPosting().getId())) {
			throw new InvalidPostingCommentAccess("Invalid posting comment access",
				ErrorCode.INVALID_POSTING_COMMENT_ACCESS);
		}
	}

	@Transactional
	@Override
	public DeletePostingResDTO deletePosting(Long postingId) {
		User currentUser = userService.getCurrentUser();
		Posting posting = postingService.getOnePosting(postingId);
		if (!posting.getUser().getId().equals(currentUser.getId())) {
			throw new InvalidUserAccessException("Invalid User Access.", ErrorCode.INVALID_USER_ACCESS);
		}
		postingCommentService.deletePostingCommentsWithPosting(postingId);
		posting.deletePostingComments();
		postingLikesService.deletePostingLikesWithPosting(postingId);
		posting.deletePostingLikes();
		postingService.deletePosting(posting, currentUser);
		return DeletePostingResDTO.builder()
			.postingId(postingId)
			.build();
	}

	@Override
	public List<GetThumbNailPostingResDTO> getPreviewPostings(GetPreviewPostingReqDTO getPreviewPostingReqDTO) {
		List<Posting> postings = postingService.getPreviewPostings(getPreviewPostingReqDTO);
		return postings.stream()
			.map(posting -> GetThumbNailPostingResDTO.builder()
				.postingId(posting.getId())
				.title(posting.getTitle())
				.content(posting.getContent())
				.userImageUrl(posting.getUser().getUserImageUrl() == null ? "" : posting.getUser().getUserImageUrl())
				.userNickName(posting.getUser().getNickName())
				.userAgeRange(posting.getUser().getAgeRange())
				.userGender(posting.getUser().getGender())
				.userNationality(posting.getUser().getNationality())
				.build())
			.collect(Collectors.toList());
	}

	@Override
	public GetThumbNailPostingsResDTO getAllPostings(GetAllPostingsReqDTO getAllPostingsReqDTO,
		Pageable pageable) {
		Page<Posting> getAllPostings = postingService.getAllPostings(getAllPostingsReqDTO, pageable);
		return generateGetThumbNailPostingsResDTO(getAllPostings);
	}

	@Override
	public GetThumbNailPostingsResDTO getSearchPostings(String keyword, String postingType, Pageable pageable) {
		Page<Posting> getSearchPostings = postingService.getSearchPostings(keyword, postingType, pageable);
		return generateGetThumbNailPostingsResDTO(getSearchPostings);
	}

	@Override
	public GetThumbNailPostingsResDTO getAllPopularityPostings(GetAllPostingsReqDTO getAllPostingsReqDTO,
		Pageable pageable) {
		Page<Posting> postings = postingService.getAllPopularityPostings(getAllPostingsReqDTO, pageable);
		return generateGetThumbNailPostingsResDTO(postings);
	}

	@Override
	public GetAllLikedPostingResDTO getMyLikedPostings(Pageable pageable) {
		User currentUser = userService.getCurrentUser();
		Page<PostingLikes> getAllPostingLikes = postingLikesService.getAllPostingLikes(currentUser, pageable);
		List<Posting> rawPostings = getAllPostingLikes.stream()
			.map(PostingLikes::getPosting)
			.toList();
		Page<Posting> postings = new PageImpl<>(rawPostings, pageable, getAllPostingLikes.getTotalElements());
		return GetAllLikedPostingResDTO.builder()
			.userImageUrl(currentUser.getUserImageUrl() == null ? "" : currentUser.getUserImageUrl())
			.userNickName(currentUser.getNickName())
			.userAgeRange(currentUser.getAgeRange())
			.userGender(currentUser.getGender())
			.userNationality(currentUser.getNationality())
			.likedPostings(generateGetThumbNailPostingsResDTO(postings))
			.build();
	}

	private GetThumbNailPostingsResDTO generateGetThumbNailPostingsResDTO(Page<Posting> postings) {
		List<GetThumbNailPostingResDTO> resultPostings = postings.stream()
			.map(posting -> GetThumbNailPostingResDTO.builder()
				.postingId(posting.getId())
				.title(posting.getTitle())
				.content(posting.getContent())
				.userImageUrl(posting.getUser().getUserImageUrl() == null ? "" : posting.getUser().getUserImageUrl())
				.userNickName(posting.getUser().getNickName())
				.userAgeRange(posting.getUser().getAgeRange())
				.userGender(posting.getUser().getGender())
				.userNationality(posting.getUser().getNationality())
				.build())
			.toList();
		return GetThumbNailPostingsResDTO.builder()
			.totalPageCount(postings.getTotalPages())
			.postings(resultPostings)
			.build();
	}

	@Transactional
	@Override
	public GetOnePostingResDTO togglePostingLikes(Long postingId) {
		Posting posting = postingService.getOnePostingWithPessimisticLock(postingId);
		User currentUser = userService.getCurrentUser();
		boolean postingToggleState = postingLikesService.updatePostingLikesToggleState(posting, currentUser);
		posting.updatePostingLikeCount(postingToggleState);
		return generateGetOnePostingResDTO(posting, userService.getCurrentUser());
	}
}
