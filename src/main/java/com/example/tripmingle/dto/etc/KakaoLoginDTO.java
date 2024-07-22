package com.example.tripmingle.dto.etc;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class KakaoLoginDTO {

	private String accessToken;
	private String refreshToken;

}
