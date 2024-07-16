package com.example.tripmingle.application.facadeService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tripmingle.application.service.PostingCommentService;
import com.example.tripmingle.application.service.PostingLikesService;
import com.example.tripmingle.application.service.PostingService;
import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.dto.req.posting.GetAllPostingsReqDTO;
import com.example.tripmingle.dto.req.posting.GetPreviewPostingReqDTO;
import com.example.tripmingle.dto.req.posting.PatchPostingCommentReqDTO;
import com.example.tripmingle.dto.req.posting.PatchPostingReqDTO;
import com.example.tripmingle.dto.req.posting.PostPostingCommentReqDTO;
import com.example.tripmingle.dto.req.posting.PostPostingReqDTO;
import com.example.tripmingle.dto.res.posting.DeletePostingCommentResDTO;
import com.example.tripmingle.dto.res.posting.DeletePostingResDTO;
import com.example.tripmingle.dto.res.posting.GetAllLikedPostingResDTO;
import com.example.tripmingle.dto.res.posting.GetOnePostingCoCommentResDTO;
import com.example.tripmingle.dto.res.posting.GetOnePostingCommentsResDTO;
import com.example.tripmingle.dto.res.posting.GetOnePostingResDTO;
import com.example.tripmingle.dto.res.posting.GetThumbNailPostingResDTO;
import com.example.tripmingle.dto.res.posting.PatchPostingCommentResDTO;
import com.example.tripmingle.dto.res.posting.PatchPostingResDTO;
import com.example.tripmingle.dto.res.posting.PostPostingCommentResDTO;
import com.example.tripmingle.dto.res.posting.PostPostingResDTO;
import com.example.tripmingle.dto.res.posting.PostingLikeToggleStateResDTO;
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
	public PostPostingResDTO createPosting(PostPostingReqDTO postPostingReqDTO) {
		User currentUser = userService.getCurrentUser();
		Long postingId = postingService.createPosting(postPostingReqDTO, currentUser);
		return PostPostingResDTO.builder()
			.postingId(postingId)
			.build();
	}

	@Transactional
	@Override
	public PatchPostingResDTO updatePosting(PatchPostingReqDTO patchPostingReqDTO) {
		User currentUser = userService.getCurrentUser();
		Long postingId = postingService.updatePosting(patchPostingReqDTO, currentUser);
		return PatchPostingResDTO.builder()
			.postingId(postingId)
			.build();
	}

	@Transactional
	@Override
	public DeletePostingResDTO deletePosting(Long postingId) {
		User currentUser = userService.getCurrentUser();
		Posting posting = postingService.getOnePosting(postingId);
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
				.build())
			.collect(Collectors.toList());
	}

	@Override
	public GetOnePostingResDTO getOnePosting(Long postingId) {
		User currentUser = userService.getCurrentUser();
		Posting posting = postingService.getOnePosting(postingId);
		boolean postingLikesState = postingLikesService.getPostingLikesState(posting);
		List<PostingComment> postingComments = postingCommentService.getPostingComments(postingId);
		List<GetOnePostingCommentsResDTO> commentsInOnePosting = getCommentsInPosting(postingComments, currentUser);
		return GetOnePostingResDTO.builder()
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
			.isMine(posting.getUser().getId().equals(currentUser.getId()))
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

	@Override
	public List<GetThumbNailPostingResDTO> getAllPostings(GetAllPostingsReqDTO getAllPostingsReqDTO,
		Pageable pageable) {
		Page<Posting> getAllPostings = postingService.getAllPostings(getAllPostingsReqDTO, pageable);
		return getAllPostings.stream()
			.map(posting -> GetThumbNailPostingResDTO.builder()
				.postingId(posting.getId())
				.title(posting.getTitle())
				.content(posting.getContent())
				.userNickName(posting.getUser().getNickName())
				.userAgeRange(posting.getUser().getAgeRange())
				.userGender(posting.getUser().getGender())
				.userImageUrl(posting.getUser().getUserImageUrl() == null ? "" : posting.getUser().getUserImageUrl())
				.build())
			.collect(Collectors.toList());
	}

	@Override
	public List<GetThumbNailPostingResDTO> getSearchPostings(String keyword, String postingType, Pageable pageable) {
		Page<Posting> getSearchPostings = postingService.getSearchPostings(keyword, postingType, pageable);
		return getSearchPostings.stream()
			.filter(posting -> posting.getTitle().contains(keyword) || posting.getContent().contains(keyword))
			.map(posting -> GetThumbNailPostingResDTO.builder()
				.postingId(posting.getId())
				.title(posting.getTitle())
				.content(posting.getContent())
				.userNickName(posting.getUser().getNickName())
				.userAgeRange(posting.getUser().getAgeRange())
				.userGender(posting.getUser().getGender())
				.userImageUrl(posting.getUser().getUserImageUrl() == null ? "" : posting.getUser().getUserImageUrl())
				.build())
			.collect(Collectors.toList());
	}

	@Transactional
	@Override
	public PostPostingCommentResDTO createPostingComment(PostPostingCommentReqDTO postPostingCommentReqDTO) {
		User currentUser = userService.getCurrentUser();
		Posting posting = postingService.getOnePosting(postPostingCommentReqDTO.getPostingId());
		Long newPostingCommentId = postingCommentService.createPostingComment(postPostingCommentReqDTO, posting,
			currentUser);
		return PostPostingCommentResDTO.builder()
			.postingCommentId(newPostingCommentId)
			.build();
	}

	@Transactional
	@Override
	public PatchPostingCommentResDTO updatePostingComment(PatchPostingCommentReqDTO patchPostingCommentReqDTO) {
		User currentUser = userService.getCurrentUser();
		Long postingComment = postingCommentService.updatePostingComment(patchPostingCommentReqDTO, currentUser);
		return PatchPostingCommentResDTO.builder()
			.postingCommentId(postingComment)
			.build();
	}

	@Transactional
	@Override
	public DeletePostingCommentResDTO deletePostingComment(Long commentId) {
		User currentUser = userService.getCurrentUser();
		Long postingCommentId = postingCommentService.deletePostingComment(commentId, currentUser);
		return DeletePostingCommentResDTO.builder()
			.postingCommentId(postingCommentId)
			.build();
	}

	@Transactional
	@Override
	public PostingLikeToggleStateResDTO togglePostingLikes(Long postingId) {
		Posting posting = postingService.getOnePosting(postingId);
		boolean postingToggleState = postingLikesService.updatePostingLikesToggleState(posting);
		posting.updatePostingLikeCount(postingToggleState);
		return PostingLikeToggleStateResDTO.builder()
			.postingId(postingId)
			.postingToggleState(postingToggleState)
			.build();
	}

	@Override
	public GetAllLikedPostingResDTO getMyLikedPostings(Pageable pageable) {
		Page<PostingLikes> getAllPostingLikes = postingLikesService.getAllPostingLikes(pageable);
		User user = userService.getCurrentUser();
		return GetAllLikedPostingResDTO.builder()
			.userImageUrl(user.getUserImageUrl() == null ? "" : user.getUserImageUrl())
			.userNickName(user.getNickName())
			.userAgeRange(user.getAgeRange())
			.userGender(user.getGender())
			.userNationality(user.getNationality())
			.likedPostings(getLikedPostingsByCurrentUser(getAllPostingLikes))
			.build();
	}

	private List<GetThumbNailPostingResDTO> getLikedPostingsByCurrentUser(Page<PostingLikes> likedPostings) {
		return likedPostings.stream()
			.map(postingLikes -> GetThumbNailPostingResDTO.builder()
				.postingId(postingLikes.getPosting().getId())
				.title(postingLikes.getPosting().getTitle())
				.content(postingLikes.getPosting().getContent())
				.userImageUrl(postingLikes.getPosting().getUser().getUserImageUrl() == null ? "" :
					postingLikes.getPosting().getUser().getUserImageUrl())
				.userNickName(postingLikes.getPosting().getUser().getNickName())
				.userAgeRange(postingLikes.getPosting().getUser().getAgeRange())
				.userGender(postingLikes.getPosting().getUser().getGender())
				.build())
			.collect(Collectors.toList());
	}

	@Override
	public List<GetThumbNailPostingResDTO> getAllPopularityPostings(GetAllPostingsReqDTO getAllPostingsReqDTO,
		Pageable pageable) {
		Page<Posting> postings = postingService.getAllPopularityPostings(getAllPostingsReqDTO, pageable);
		return postings.stream()
			.map(posting -> GetThumbNailPostingResDTO.builder()
				.postingId(posting.getId())
				.title(posting.getTitle())
				.content(posting.getContent())
				.userImageUrl(posting.getUser().getUserImageUrl() == null ? "" : posting.getUser().getUserImageUrl())
				.userNickName(posting.getUser().getNickName())
				.userAgeRange(posting.getUser().getAgeRange())
				.userGender(posting.getUser().getGender())
				.build())
			.collect(Collectors.toList());
	}
}
