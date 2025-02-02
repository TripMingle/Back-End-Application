package com.example.tripmingle.dto.res.posting;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetThumbNailPostingResDTO {

	private Long postingId;
	private String title;
	private String content;
	private Long userId;
	private String userImageUrl;
	private String userNickName;
	private String userAgeRange;
	private String userGender;
	private String userNationality;

}
