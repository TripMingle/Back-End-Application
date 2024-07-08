package com.example.tripmingle.adapter.in;

import static com.example.tripmingle.common.result.ResultCode.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.etc.TokenDTO;
import com.example.tripmingle.dto.req.oauth.KakaoUserAdditionDetailsReqDTO;
import com.example.tripmingle.dto.res.oauth.KakaoTokenResDTO;
import com.example.tripmingle.port.in.KakaoAuthUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "카카오 로그인")
@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class KakaoAuthController {

	private final KakaoAuthUseCase kakaoAuthUseCase;

	@PostMapping("/login")
	public ResponseEntity<ResultResponse> loginKakaoAccount(
		@RequestHeader("Kakao-Authorization") String kakaoAccessToken,
		@RequestBody KakaoUserAdditionDetailsReqDTO kakaoUserAdditionDetailsReqDTO) {
		kakaoUserAdditionDetailsReqDTO.setKakaoAccessToken(kakaoAccessToken);
		TokenDTO tokenDTO = kakaoAuthUseCase.loginKakaoAccount(kakaoUserAdditionDetailsReqDTO);
		HttpHeaders tokenHeaders = new HttpHeaders();
		tokenHeaders.add("access-token", tokenDTO.getAccessToken());
		tokenHeaders.add("refresh-token", tokenDTO.getRefreshToken());
		return ResponseEntity.ok().headers(tokenHeaders).body(ResultResponse.of(OAUTH_LOGIN_SUCCESS));
	}

	@GetMapping("/callback")
	public ResponseEntity<ResultResponse> callback(@RequestParam("code") String code) {
		KakaoTokenResDTO kakaoTokenResDTO = kakaoAuthUseCase.getKakaoAccessToken(code);
		return ResponseEntity.ok().body(ResultResponse.of(OAUTH_TOKEN_ISSUE_SUCCESS, kakaoTokenResDTO));
	}

}
