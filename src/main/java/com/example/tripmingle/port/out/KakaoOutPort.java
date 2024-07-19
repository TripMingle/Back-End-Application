package com.example.tripmingle.port.out;

import com.example.tripmingle.dto.res.oauth.KakaoLoginResDTO;
import com.example.tripmingle.dto.res.oauth.KakaoTokenResDTO;

public interface KakaoOutPort {
	KakaoLoginResDTO getKakaoUserInfo(String redefineAccessToken);

	KakaoTokenResDTO getToken(String kakaoGrantType, String kakaoClientId, String kakaoSecretKey, String code);
}
