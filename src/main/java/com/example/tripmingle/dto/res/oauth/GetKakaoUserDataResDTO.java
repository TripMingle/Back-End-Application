package com.example.tripmingle.dto.res.oauth;

import com.example.tripmingle.dto.etc.KakaoUserInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class GetKakaoUserDataResDTO {

	@JsonProperty("id")
	private String kakaoId;

	@JsonProperty("kakao_account")
	private KakaoUserInfo kakaoUserInfo;

}
