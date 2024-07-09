package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.etc.TokenDTO;
import com.example.tripmingle.dto.req.oauth.KakaoUserAdditionDetailsReqDTO;
import com.example.tripmingle.dto.req.oauth.KakaoUserDetailReqDTO;
import com.example.tripmingle.dto.res.oauth.KakaoTokenResDTO;

public interface KakaoAuthUseCase {

    TokenDTO loginKakaoAccount(KakaoUserDetailReqDTO kakaoUserDetailReqDTO);

    KakaoTokenResDTO getKakaoAccessToken(String code);
}
