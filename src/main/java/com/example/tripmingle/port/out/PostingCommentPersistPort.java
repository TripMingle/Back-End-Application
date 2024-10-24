package com.example.tripmingle.port.out;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.tripmingle.entity.PostingComment;

@Component
public interface PostingCommentPersistPort {

	List<PostingComment> getPostingCommentsByPostingId(Long postingId);

	List<PostingComment> getPostingCoCommentByParentCommentId(Long id);

	PostingComment getPostingCommentById(Long parentCommentId);

	void save(PostingComment postingComment);

	List<PostingComment> getAllChildPostingCommentByParentPostingCommentId(Long parentPostingCommentId);
}
