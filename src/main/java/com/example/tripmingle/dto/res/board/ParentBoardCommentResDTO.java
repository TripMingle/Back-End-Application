package com.example.tripmingle.dto.res.board;

import java.time.LocalDateTime;
import java.util.List;

import com.example.tripmingle.dto.etc.ChildBoardCommentDTO;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ParentBoardCommentResDTO {
	private Long boardId;
	private Long boardCommentId;
	private String content;
	private LocalDateTime registeredDate;
	private Long userId;
	private String userNickname;
	private String userImageUrl;
	private boolean isMine;
	private List<ChildBoardCommentDTO> childBoards;
}
