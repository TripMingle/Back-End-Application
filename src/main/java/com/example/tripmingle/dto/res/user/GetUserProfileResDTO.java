package com.example.tripmingle.dto.res.user;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class GetUserProfileResDTO {

	private Long userId;
	private String userNickName;
	private String userImageUrl;
	private double userScore;
	private String userGender;
	private String userAgeRange;
	private String userNationality;
	private String selfIntroduction;

}
