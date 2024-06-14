package com.example.tripmingle.common.utils;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class KakaoProperties {

    @Value("${oauth2.kakao.infoUrl}")
    private String kakaoInfoUrl;

    @Value("${oauth2.kakao.baseUrl}")
    private String kakaoBaseUrl;

    @Value("${oauth2.kakao.clientId}")
    private String kakaoClientId;

    @Value("${oauth2.kakao.secretKey}")
    private String kakaoSecretKey;

    @Value("${oauth2.kakao.grant_type}")
    private String kakaoGrantType;

}
