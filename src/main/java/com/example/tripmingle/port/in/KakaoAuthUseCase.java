package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.etc.TokenDTO;
import com.example.tripmingle.dto.req.KakaoUserAdditionDetailsReqDTO;
import com.example.tripmingle.dto.res.KakaoTokenResDTO;

public interface KakaoAuthUseCase {

    TokenDTO loginKakaoAccount(KakaoUserAdditionDetailsReqDTO kakaoUserAdditionDetailsReqDTO);

    KakaoTokenResDTO getKakaoAccessToken(String code);
}
