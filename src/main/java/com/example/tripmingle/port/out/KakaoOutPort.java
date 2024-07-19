package com.example.tripmingle.port.out;

import com.example.tripmingle.dto.res.oauth.GetKakaoUserDataResDTO;
import com.example.tripmingle.dto.res.oauth.KakaoTokenResDTO;

public interface KakaoOutPort {
	GetKakaoUserDataResDTO getKakaoUserInfo(String redefineAccessToken);

	KakaoTokenResDTO getToken(String kakaoGrantType, String kakaoClientId, String kakaoSecretKey, String code);
}
