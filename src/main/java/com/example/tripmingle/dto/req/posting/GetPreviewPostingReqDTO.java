package com.example.tripmingle.dto.req.posting;

import com.example.tripmingle.entity.PostingType;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetPreviewPostingReqDTO {

	private String country;
	private PostingType postingType;

}
