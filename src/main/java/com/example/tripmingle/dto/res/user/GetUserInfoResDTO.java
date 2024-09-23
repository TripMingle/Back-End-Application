package com.example.tripmingle.dto.res.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetUserInfoResDTO {

	private String userNickName;
	private String userProfileImage;

}
