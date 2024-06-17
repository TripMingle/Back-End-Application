package com.example.tripmingle.application.service;

import com.example.tripmingle.common.config.oauth.KakaoProperties;
import com.example.tripmingle.common.error.ErrorResponse;
import com.example.tripmingle.common.utils.JwtUtils;
import com.example.tripmingle.dto.etc.KakaoUserInfo;
import com.example.tripmingle.dto.etc.TokenDTO;
import com.example.tripmingle.dto.res.KakaoTokenResDTO;
import com.example.tripmingle.entity.Refresh;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.RefreshPort;
import com.example.tripmingle.port.out.UserPersistPort;
import io.netty.handler.codec.http.HttpHeaderValues;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.JSONObject;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Date;
import java.util.LinkedHashMap;

import static com.example.tripmingle.common.constants.JwtConstants.ACCESS_TOKEN;
import static com.example.tripmingle.common.constants.JwtConstants.REFRESH_TOKEN;
import static com.example.tripmingle.common.constants.LoginType.KAKAO;
import static com.example.tripmingle.common.error.ErrorCode.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class KakaoService {
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final WebClient webClient;
    private final KakaoProperties kakaoProperties;
    private final UserPersistPort userPersistPort;
    private final RefreshPort refreshPort;

    @Transactional
    public TokenDTO loginKakaoAccount(String kakaoAccessToken) {
        KakaoUserInfo userInfo = getKakaoUserInfo(kakaoAccessToken);
        User user = joinStateCheckAndReturnUser(userInfo);
        userNullCheck(user);

        String accessToken = jwtUtils.createJwtToken(user.getEmail(), user.getRole(), user.getLoginType(), ACCESS_TOKEN.getMessage(), jwtUtils.getAccessTokenExpTime());
        String refreshToken = jwtUtils.createJwtToken(user.getEmail(), user.getRole(), user.getLoginType(), REFRESH_TOKEN.getMessage(), jwtUtils.getRefreshTokenExpTime());
        addRefreshEntity(user.getEmail(), refreshToken, jwtUtils.getRefreshTokenExpTimeByToken(refreshToken));

        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private KakaoUserInfo getKakaoUserInfo(String kakaoAccessToken) {
        JSONObject totalKakaoUserInfo = webClient.post()
                .uri(kakaoProperties.getKapiUserInfoHost())
                .header("Authorization", "Bearer " + kakaoAccessToken)
                .bodyValue("property_keys=[\"kakao_account.email\", \"kakao_account.\"]")
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new ErrorResponse(KAKAO_BAD_REQUEST)))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new ErrorResponse(KAKAO_SERVER_ERROR)))
                .bodyToMono(JSONObject.class)
                .block();
        LinkedHashMap kakaoAccount = (LinkedHashMap) totalKakaoUserInfo.get("kakao_account");
        return KakaoUserInfo.builder()
                .email(String.valueOf(kakaoAccount.get("email")))
                .kakaoId(String.valueOf(totalKakaoUserInfo.get("id")))
                .ageRange(String.valueOf(kakaoAccount.get("age_range")))
                .gender(String.valueOf(kakaoAccount.get("gender")))
                .name(String.valueOf(kakaoAccount.get("name")))
                .phoneNumber(String.valueOf(kakaoAccount.get("phone_number")))
                .build();
    }

    // TODO 닉네임, 국가에 대한 입력을 어떻게 할지 논의 필요 -> 카카오 액세스 토큰을 주면서 해당 데이터를 같이 넘겨줄지,
    //  아니면 나중에 해당 유저 정보를 수정하는 걸로 할지에 대해서
    private User joinStateCheckAndReturnUser(KakaoUserInfo userInfo) {
        User user = null;
        if (!userPersistPort.existsByEmail(userInfo.getEmail())) {
            user = userPersistPort.save(User.builder()
                    .email(userInfo.getEmail())
                    .password(passwordEncoder.encode("TripMingle " + userInfo.getEmail() + userInfo.getKakaoId()))
                    .role("ROLE_USER")
                    .loginType(KAKAO.getLoginType())
                    .oauthId(userInfo.getKakaoId())
                    .nickName("default")
                    .ageRange(userInfo.getAgeRange())
                    .gender(userInfo.getGender())
                    .name(userInfo.getName())
                    .nationality("default")
                    .phoneNumber(userInfo.getPhoneNumber())
                    .build());
        } else {
            user = userPersistPort.findByEmail(userInfo.getEmail()).orElseThrow(() -> new ErrorResponse(KAKAO_ALREADY_EXISTS_USER));
        }
        return user;
    }

    private void userNullCheck(User user) {
        if (user == null) {
            throw new ErrorResponse(KAKAO_NO_EXISTS_USER);
        }
    }

    private void addRefreshEntity(String email, String refresh, Long expiredMs) {
        Date date = new Date(System.currentTimeMillis() + expiredMs);
        Refresh refreshEntity = Refresh.builder()
                .email(email)
                .refreshToken(refresh)
                .expiration(date.toString())
                .build();
        refreshPort.save(refreshEntity);
    }

    public KakaoTokenResDTO getKakaoAccessToken(String code) {
        return webClient.post()
                .uri(kakaoProperties.getKauthTokenUrlHost(), uriBuilder -> uriBuilder
                        .queryParam("grant_type", "authorization_code")
                        .queryParam("client_id", kakaoProperties.getClientId())
                        .queryParam("code", code)
                        .queryParam("client_secret", kakaoProperties.getClientSecret())
                        .build(true))
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .onStatus(HttpStatusCode::is4xxClientError, clientResponse -> Mono.error(new RuntimeException("Invalid Parameter")))
                .onStatus(HttpStatusCode::is5xxServerError, clientResponse -> Mono.error(new RuntimeException("Internal Server Error")))
                .bodyToMono(KakaoTokenResDTO.class)
                .block();
    }
}
