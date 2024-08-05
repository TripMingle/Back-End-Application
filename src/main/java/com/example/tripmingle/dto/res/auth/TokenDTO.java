package com.example.tripmingle.dto.res.auth;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenDTO {

	private String accessToken;
	private String refreshToken;

}
