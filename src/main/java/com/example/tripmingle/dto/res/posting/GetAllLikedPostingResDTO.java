package com.example.tripmingle.dto.res.posting;

import java.util.List;

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
	private List<GetThumbNailPostingResDTO> likedPostings;

}
