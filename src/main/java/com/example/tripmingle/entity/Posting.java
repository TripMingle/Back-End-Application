package com.example.tripmingle.entity;

import org.hibernate.annotations.Where;

import com.example.tripmingle.common.entity.BaseEntity;
import com.example.tripmingle.dto.req.posting.PatchPostingReqDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Where(clause = "is_deleted = false")
public class Posting extends BaseEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(nullable = false)
	private String title;

	@Column(nullable = false)
	private String content;

	@Enumerated(value = EnumType.STRING)
	@Column(nullable = false)
	private PostingType postingType;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	@Column(nullable = false)
	private String country;

	private int commentCount;
	private int likeCount;

	@Builder
	public Posting(String title, String content, PostingType postingType, User user, String country) {
		this.title = title;
		this.content = content;
		this.postingType = postingType;
		this.user = user;
		this.country = country;
		this.commentCount = 0;
		this.likeCount = 0;
	}

	public void updatePosting(PatchPostingReqDTO patchPostingReqDTO) {
		if (patchPostingReqDTO.getTitle() != null && !patchPostingReqDTO.getTitle().equals("")) {
			this.title = patchPostingReqDTO.getTitle();
		}
		if (patchPostingReqDTO.getContent() != null && !patchPostingReqDTO.getContent().equals("")) {
			this.content = patchPostingReqDTO.getContent();
		}
		if (patchPostingReqDTO.getPostingType() != null && !patchPostingReqDTO.getPostingType().equals("")) {
			this.postingType = patchPostingReqDTO.getPostingType();
		}
		if (patchPostingReqDTO.getCountry() != null && !patchPostingReqDTO.getCountry().equals("")) {
			this.country = patchPostingReqDTO.getCountry();
		}
	}

	public void deletePosting() {
		delete();
	}

	public void increasePostingCommentCount() {
		this.commentCount += 1;
	}

	public void decreasePostingCommentCount(int deletedPostingCommentCount) {
		if (this.commentCount > deletedPostingCommentCount) {
			this.commentCount -= deletedPostingCommentCount;
		} else {
			this.commentCount = 0;
		}
	}

	public void deletePostingComments() {
		this.commentCount = 0;
	}

	public void deletePostingLikes() {
		this.commentCount = 0;
	}

	public void updatePostingLikeCount(boolean postingToggleState) {
		if (postingToggleState) {
			this.likeCount += 1;
		} else {
			if (this.likeCount > 0) {
				this.likeCount -= 1;
			}
		}
	}
}
