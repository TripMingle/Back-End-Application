package com.example.tripmingle.dto.res.posting;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAllLikedPostingResDTO {

	private String userImageUrl;
	private String userNickName;
	private String userAgeRange;
	private String userGender;
	private String userNationality;
	private GetThumbNailPostingsResDTO likedPostings;

}
