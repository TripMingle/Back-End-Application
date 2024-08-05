package com.example.tripmingle.dto.etc;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoLoginDTO {

	private boolean isMemberState;
	private String nickName;
	private String profileImage;
	@JsonIgnore
	private String accessToken;
	@JsonIgnore
	private String refreshToken;

}
