package com.example.tripmingle.dto.req.posting;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeletePostingCommentReqDTO {

	@JsonIgnore
	private Long postingId;
	@JsonIgnore
	private Long postingCommentId;

}
