package com.example.tripmingle.dto.req.posting;

import com.example.tripmingle.entity.PostingType;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPostingReqDTO {

	private String title;
	private String content;
	@Schema(description = "포스팅 타입", example = "RESTAURANT, RENTAL_HOME, SCHEDULE")
	private PostingType postingType;
	private String country;

}
