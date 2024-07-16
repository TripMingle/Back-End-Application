package com.example.tripmingle.dto.res.posting;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetOnePostingCommentsResDTO {

	private Long commentId;
	private String userImageUrl;
	private String userNickName;
	private String comment;
	private LocalDateTime createdAt;
	private List<GetOnePostingCoCommentResDTO> postingCoComment;

}
