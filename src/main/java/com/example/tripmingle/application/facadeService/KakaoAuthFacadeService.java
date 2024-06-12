package com.example.tripmingle.application.facadeService;

import com.example.tripmingle.application.Service.KakaoService;
import com.example.tripmingle.dto.etc.TokenDTO;
import com.example.tripmingle.dto.res.KakaoTokenResDTO;
import com.example.tripmingle.port.in.KakaoAuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class KakaoAuthFacadeService implements KakaoAuthUseCase {

    private final KakaoService kakaoService;

    @Override
    public TokenDTO loginKakaoAccount(String kakaoAccessToken) {
        return kakaoService.loginKakaoAccount(kakaoAccessToken);
    }

    @Override
    public KakaoTokenResDTO getKakaoAccessToken(String code) {
        return kakaoService.getKakaoAccessToken(code);
    }
}
