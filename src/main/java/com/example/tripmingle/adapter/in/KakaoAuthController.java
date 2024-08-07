package com.example.tripmingle.adapter.in;

import static com.example.tripmingle.common.error.ErrorCode.*;
import static com.example.tripmingle.common.result.ResultCode.*;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripmingle.common.exception.NickNameDuplicationException;
import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.etc.KakaoLoginDTO;
import com.example.tripmingle.dto.req.user.AdditionalUserDetailReqDTO;
import com.example.tripmingle.dto.res.oauth.KakaoTokenResDTO;
import com.example.tripmingle.port.in.KakaoAuthUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "카카오 로그인")
@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class KakaoAuthController {

	private final KakaoAuthUseCase kakaoAuthUseCase;

	@Operation(summary = "카카오 소셜 로그인 - 로그인")
	@PostMapping("/login")
	public ResponseEntity<ResultResponse> loginKakaoAccount(
		@RequestHeader("Kakao-Authorization") String kakaoAccessToken) {
		KakaoLoginDTO kakaoLoginDTO = kakaoAuthUseCase.loginKakaoAccount(kakaoAccessToken);
		HttpHeaders tokenHeaders = generateTokenHeaders(kakaoLoginDTO);
		return ResponseEntity.ok()
			.headers(tokenHeaders)
			.body(ResultResponse.of(OAUTH_LOGIN_SUCCESS, kakaoLoginDTO));
	}

	private HttpHeaders generateTokenHeaders(KakaoLoginDTO kakaoLoginDTO) {
		HttpHeaders tokenHeaders = new HttpHeaders();
		tokenHeaders.add("access-token", kakaoLoginDTO.getAccessToken());
		tokenHeaders.add("refresh-token", kakaoLoginDTO.getRefreshToken());
		return tokenHeaders;
	}

	@Operation(summary = "카카오 소셜 로그인 - 회원가입")
	@PostMapping("/join")
	public ResponseEntity<ResultResponse> joinKakaoAccount(
		@RequestHeader("Kakao-Authorization") String kakaoAccessToken,
		@RequestBody AdditionalUserDetailReqDTO additionalUserDetailReqDTO) {
		try {
			additionalUserDetailReqDTO.insertKakaoAccessToken(kakaoAccessToken);
			KakaoLoginDTO kakaoLoginDTO = kakaoAuthUseCase.joinKakaoAccount(additionalUserDetailReqDTO);
			HttpHeaders tokenHeaders = generateTokenHeaders(kakaoLoginDTO);
			return ResponseEntity.ok()
				.headers(tokenHeaders)
				.body(ResultResponse.of(OAUTH_LOGIN_SUCCESS, kakaoLoginDTO));
		} catch (DataIntegrityViolationException exception) {
			throw new NickNameDuplicationException("닉네임이 중복되었습니다.", ALREADY_EXISTS_USER_NICKNAME);
		}
	}

	@Operation(summary = "카카오 엑세스 토큰 발급")
	@GetMapping("/callback")
	public ResponseEntity<ResultResponse> callback(@RequestParam("code") String code) {
		KakaoTokenResDTO kakaoTokenResDTO = kakaoAuthUseCase.getKakaoAccessToken(code);
		return ResponseEntity.ok(ResultResponse.of(OAUTH_TOKEN_ISSUE_SUCCESS, kakaoTokenResDTO));
	}

}
