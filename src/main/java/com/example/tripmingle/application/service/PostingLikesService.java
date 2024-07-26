package com.example.tripmingle.application.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.tripmingle.entity.Posting;
import com.example.tripmingle.entity.PostingLikes;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.PostingLikesPersistPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PostingLikesService {

	private final PostingLikesPersistPort postingLikesPersistPort;

	public boolean updatePostingLikesToggleState(Posting posting, User currentUser) {
		PostingLikes postingLikes = null;
		if (!postingLikesPersistPort.existsByPostingIdAndUserId(posting.getId(), currentUser.getId())) {
			postingLikes = PostingLikes.builder()
				.posting(posting)
				.user(currentUser)
				.build();
			postingLikesPersistPort.save(postingLikes);
			return true;
		}
		postingLikes = postingLikesPersistPort.findByPostingIdAndUserId(posting.getId(), currentUser.getId());
		postingLikes.updatePostingLikeToggleState();
		return postingLikes.isToggleState();
	}

	public Page<PostingLikes> getAllPostingLikes(User currentUser, Pageable pageable) {
		Page<PostingLikes> postingLikes = postingLikesPersistPort.getAllPostingLikesByUserId(currentUser.getId(),
			pageable);
		return postingLikes;
	}

	public boolean getPostingLikesState(Posting posting, User currentUser) {
		if (!postingLikesPersistPort.existsByPostingIdAndUserId(posting.getId(), currentUser.getId())) {
			return false;
		}
		PostingLikes myPostingLike = postingLikesPersistPort.findByPostingIdAndUserId(posting.getId(),
			currentUser.getId());
		return myPostingLike.isToggleState();
	}

	public int getPostingTotalLikeCount(Long postingId) {
		return postingLikesPersistPort.count(postingId);
	}

	public void deletePostingLikesWithPosting(Long postingId) {
		List<PostingLikes> postingLikes = postingLikesPersistPort.getAllPostingLikes(postingId);
		postingLikes.forEach(PostingLikes::deletePostingLikes);
	}
}
