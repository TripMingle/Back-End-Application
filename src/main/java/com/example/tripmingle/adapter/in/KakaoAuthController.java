package com.example.tripmingle.adapter.in;

import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.etc.TokenDTO;
import com.example.tripmingle.dto.req.oauth.KakaoUserAdditionDetailsReqDTO;
import com.example.tripmingle.dto.res.oauth.KakaoTokenResDTO;
import com.example.tripmingle.port.in.KakaoAuthUseCase;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.example.tripmingle.common.result.ResultCode.OAUTH_LOGIN_SUCCESS;
import static com.example.tripmingle.common.result.ResultCode.OAUTH_TOKEN_ISSUE_SUCCESS;

@RestController
@RequiredArgsConstructor
@RequestMapping("/kakao")
public class KakaoAuthController {

    private final KakaoAuthUseCase kakaoAuthUseCase;

    @Operation(summary = "카카오 로그인")
    @PostMapping("/login")
    public ResponseEntity<ResultResponse> loginKakaoAccount(@RequestHeader("Kakao-Authorization") String kakaoAccessToken, @RequestBody KakaoUserAdditionDetailsReqDTO kakaoUserAdditionDetailsReqDTO) {
        kakaoUserAdditionDetailsReqDTO.setKakaoAccessToken(kakaoAccessToken);
        TokenDTO tokenDTO = kakaoAuthUseCase.loginKakaoAccount(kakaoUserAdditionDetailsReqDTO);
        HttpHeaders tokenHeaders = new HttpHeaders();
        tokenHeaders.add("access-token", tokenDTO.getAccessToken());
        tokenHeaders.add("refresh-token", tokenDTO.getRefreshToken());
        return ResponseEntity.ok().headers(tokenHeaders).body(ResultResponse.of(OAUTH_LOGIN_SUCCESS));
    }

    @Operation(summary = "카카오 엑세스 토큰 발급")
    @GetMapping("/callback")
    public ResponseEntity<ResultResponse> callback(@RequestParam("code") String code) {
        KakaoTokenResDTO kakaoTokenResDTO = kakaoAuthUseCase.getKakaoAccessToken(code);
        return ResponseEntity.ok(ResultResponse.of(OAUTH_TOKEN_ISSUE_SUCCESS, kakaoTokenResDTO));
    }

}
