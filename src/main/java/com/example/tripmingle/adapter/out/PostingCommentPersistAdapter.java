package com.example.tripmingle.adapter.out;

import static com.example.tripmingle.common.error.ErrorCode.*;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.tripmingle.common.exception.PostingCommentNotFoundException;
import com.example.tripmingle.entity.PostingComment;
import com.example.tripmingle.port.out.PostingCommentPersistPort;
import com.example.tripmingle.repository.PostingCommentRepository;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class PostingCommentPersistAdapter implements PostingCommentPersistPort {

	private final PostingCommentRepository postingCommentRepository;

	@Override
	public List<PostingComment> getPostingCommentsByPostingId(Long postingId) {
		return postingCommentRepository.findAllByPostingIdOrderByCreatedAt(postingId);
	}

	@Override
	public List<PostingComment> getPostingCoCommentByParentCommentId(Long parentCommentId) {
		return postingCommentRepository.findCoCommentsByParentCommentId(parentCommentId);
	}

	@Override
	public PostingComment getPostingCommentById(Long parentCommentId) {
		return postingCommentRepository.findById(parentCommentId)
			.orElseThrow(
				() -> new PostingCommentNotFoundException("Posting Comment Not Found.", POSTING_COMMENT_NOT_FOUND));
	}

	@Override
	public void save(PostingComment postingComment) {
		postingCommentRepository.save(postingComment);
	}

	@Override
	public List<PostingComment> getAllChildPostingCommentByParentPostingCommentId(Long parentPostingCommentId) {
		return postingCommentRepository.findAllByPostingCommentId(parentPostingCommentId);
	}
}
