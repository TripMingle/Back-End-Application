package com.example.tripmingle.dto.req.user;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdditionalUserDetailReqDTO {

	@JsonIgnore
	private String kakaoAccessToken;
	private String name;
	private String gender;
	private LocalDate birthDay;
	private String phoneNumber;
	private String nickName;
	private String nationality;
	private String selfIntroduction;

	public void insertKakaoAccessToken(String kakaoAccessToken) {
		this.kakaoAccessToken = kakaoAccessToken;
	}

}
