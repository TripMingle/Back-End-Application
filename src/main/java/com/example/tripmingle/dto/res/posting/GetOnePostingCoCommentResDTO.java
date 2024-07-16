package com.example.tripmingle.dto.res.posting;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetOnePostingCoCommentResDTO {

	private Long coCommentId;
	private Long parentCommentId;
	private String userImageUrl;
	private String userNickName;
	private String coComment;
	private boolean isMine;
	private LocalDateTime createdAt;

}
