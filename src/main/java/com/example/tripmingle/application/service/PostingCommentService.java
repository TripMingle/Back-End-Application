package com.example.tripmingle.application.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.tripmingle.common.utils.UserUtils;
import com.example.tripmingle.dto.req.posting.PatchPostingCommentReqDTO;
import com.example.tripmingle.dto.req.posting.PostPostingCommentReqDTO;
import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.PostingComment;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.PostingCommentPersistPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostingCommentService {
	private final PostingCommentPersistPort postingCommentPersistPort;
	private final UserUtils userUtils;

	public List<PostingComment> getPostingComments(Long postingId) {
		return postingCommentPersistPort.getPostingCommentsByPostingId(postingId);
	}

	public Long createPostingComment(PostPostingCommentReqDTO postPostingCommentReqDTO, Posting posting,
		User currentUser) {
		PostingComment parentPostingComment = null;
		if (postPostingCommentReqDTO.getParentCommentId() != -1) {
			parentPostingComment = postingCommentPersistPort.getPostingCommentById(
				postPostingCommentReqDTO.getParentCommentId());
		}
		PostingComment postingComment = PostingComment.builder()
			.user(currentUser)
			.posting(posting)
			.postingComment(parentPostingComment)
			.comment(postPostingCommentReqDTO.getComment())
			.build();
		Long postingCommentId = postingCommentPersistPort.save(postingComment).getId();
		posting.increasePostingCommentCount();
		return postingCommentId;
	}

	public Long updatePostingComment(PatchPostingCommentReqDTO patchPostingCommentReqDTO, User currentUser) {
		PostingComment postingComment = postingCommentPersistPort.getPostingCommentById(
			patchPostingCommentReqDTO.getPostingCommentId());
		userUtils.validateMasterUser(postingComment.getUser().getId(), currentUser.getId());
		postingComment.updateComment(patchPostingCommentReqDTO.getComment());

		return postingComment.getId();
	}

	public int deletePostingComment(Long commentId, User currentUser) {
		PostingComment postingComment = postingCommentPersistPort.getPostingCommentById(commentId);
		userUtils.validateMasterUser(postingComment.getUser().getId(), currentUser.getId());
		int deletedPostingCommentsCount = deleteParentOrChildPostingComments(postingComment);
		return deletedPostingCommentsCount;
	}

	private int deleteParentOrChildPostingComments(PostingComment postingComment) {
		if (postingComment.isParentComment()) {
			List<PostingComment> childPostingComments = postingCommentPersistPort.getAllChildPostingCommentByParentPostingCommentId(
				postingComment.getId());
			childPostingComments.forEach(PostingComment::deletePostingComment);
			postingComment.deletePostingComment();
			return childPostingComments.size() + 1;
		}
		postingComment.deletePostingComment();
		return 1;
	}

	public void deletePostingCommentsWithPosting(Long postingId) {
		List<PostingComment> postingComments = postingCommentPersistPort.getPostingCommentsByPostingId(postingId);
		postingComments.forEach(PostingComment::deletePostingComment);
	}

	public PostingComment getPostingCommentsByPostingCommentId(Long parentCommentId) {
		return postingCommentPersistPort.getPostingCommentById(parentCommentId);
	}
}
