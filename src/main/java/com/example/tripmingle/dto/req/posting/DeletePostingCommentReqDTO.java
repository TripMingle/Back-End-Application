package com.example.tripmingle.dto.req.posting;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeletePostingCommentReqDTO {

	private Long postingId;
	private Long postingCommentId;

}
