package com.example.tripmingle.application.facadeService;

import com.example.tripmingle.application.service.KakaoService;
import com.example.tripmingle.dto.etc.TokenDTO;
import com.example.tripmingle.dto.req.oauth.KakaoUserAdditionDetailsReqDTO;
import com.example.tripmingle.dto.req.oauth.KakaoUserDetailReqDTO;
import com.example.tripmingle.dto.res.oauth.KakaoTokenResDTO;
import com.example.tripmingle.port.in.KakaoAuthUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class KakaoAuthFacadeService implements KakaoAuthUseCase {
    private final KakaoService kakaoService;

    @Override
    public TokenDTO loginKakaoAccount(KakaoUserDetailReqDTO kakaoUserDetailReqDTO) {
        return kakaoService.loginKakaoAccount(kakaoUserDetailReqDTO);
    }

    @Override
    public KakaoTokenResDTO getKakaoAccessToken(String code) {
        return kakaoService.getKakaoAccessToken(code);
    }
}
