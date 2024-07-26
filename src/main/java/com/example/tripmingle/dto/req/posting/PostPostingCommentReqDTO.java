package com.example.tripmingle.dto.req.posting;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPostingCommentReqDTO {

	@JsonIgnore
	private Long postingId;
	private Long parentCommentId;
	private String comment;

}
