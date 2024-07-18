package com.example.tripmingle.dto.res.posting;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetThumbNailPostingsResDTO {

	private int totalPageCount;
	private List<GetThumbNailPostingResDTO> postings;

}
