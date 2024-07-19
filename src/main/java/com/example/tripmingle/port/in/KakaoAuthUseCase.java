package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.etc.KakaoLoginDTO;
import com.example.tripmingle.dto.req.user.AdditionalUserDetailReqDTO;
import com.example.tripmingle.dto.res.oauth.KakaoTokenResDTO;

public interface KakaoAuthUseCase {

	KakaoLoginDTO loginKakaoAccount(String kakaoAccessToken);

	KakaoTokenResDTO getKakaoAccessToken(String code);

	KakaoLoginDTO joinKakaoAccount(AdditionalUserDetailReqDTO additionalUserDetailReqDTO);
}
