package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.etc.TokenDTO;
import com.example.tripmingle.dto.res.KakaoTokenResDTO;

public interface KakaoAuthUseCase {

    TokenDTO loginKakaoAccount(String kakaoAccessToken);

    KakaoTokenResDTO getKakaoAccessToken(String code);
}
