package com.example.tripmingle.application.facadeService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tripmingle.application.service.KakaoService;
import com.example.tripmingle.dto.etc.KakaoLoginDTO;
import com.example.tripmingle.dto.req.user.AdditionalUserDetailReqDTO;
import com.example.tripmingle.dto.res.oauth.KakaoTokenResDTO;
import com.example.tripmingle.port.in.KakaoAuthUseCase;

import lombok.RequiredArgsConstructor;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class KakaoAuthFacadeService implements KakaoAuthUseCase {
	private final KakaoService kakaoService;

	@Transactional
	@Override
	public KakaoLoginDTO joinKakaoAccount(AdditionalUserDetailReqDTO additionalUserDetailReqDTO) {
		return kakaoService.joinKakaoAccount(additionalUserDetailReqDTO);
	}

	@Transactional
	@Override
	public KakaoLoginDTO loginKakaoAccount(String kakaoAccessToken) {
		return kakaoService.loginKakaoAccount(kakaoAccessToken);
	}

	@Override
	public KakaoTokenResDTO getKakaoAccessToken(String code) {
		return kakaoService.getKakaoAccessToken(code);
	}
}
