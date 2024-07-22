package com.example.tripmingle.dto.etc;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ChildBoardCommentDTO {
	private Long boardId;
	private Long boardCommentId;
	private Long parentId;
	private String content;
	private LocalDateTime registeredDate;
	private Long userId;
	private String userNickname;
	private String userImageUrl;
	private boolean isMine;
}
