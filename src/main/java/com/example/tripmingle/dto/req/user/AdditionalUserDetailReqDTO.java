package com.example.tripmingle.dto.req.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdditionalUserDetailReqDTO {

	@JsonIgnore
	private String kakaoAccessToken;
	private String nickName;
	private String nationality;
	private String selfIntroduction;

	public void insertKakaoAccessToken(String kakaoAccessToken) {
		this.kakaoAccessToken = kakaoAccessToken;
	}

}
