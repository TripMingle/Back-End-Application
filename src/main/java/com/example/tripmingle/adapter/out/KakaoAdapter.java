package com.example.tripmingle.adapter.out;

import org.springframework.stereotype.Component;

import com.example.tripmingle.dto.res.oauth.GetKakaoUserDataResDTO;
import com.example.tripmingle.dto.res.oauth.KakaoTokenResDTO;
import com.example.tripmingle.feign.KakaoLoginFeignClientForAccessTokenPort;
import com.example.tripmingle.feign.KakaoLoginFeignClientPort;
import com.example.tripmingle.port.out.KakaoOutPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class KakaoAdapter implements KakaoOutPort {

	private final KakaoLoginFeignClientPort kakaoLoginFeignClientPort;
	private final KakaoLoginFeignClientForAccessTokenPort kakaoLoginFeignClientForAccessTokenPort;

	@Override
	public GetKakaoUserDataResDTO getKakaoUserInfo(String redefineAccessToken) {
		return kakaoLoginFeignClientPort.getKakaoUserInfo(redefineAccessToken);
	}

	@Override
	public KakaoTokenResDTO getToken(String kakaoGrantType, String kakaoClientId, String kakaoSecretKey, String code) {
		return kakaoLoginFeignClientForAccessTokenPort.getToken(kakaoGrantType,
			kakaoClientId, kakaoSecretKey, code);
	}
}
