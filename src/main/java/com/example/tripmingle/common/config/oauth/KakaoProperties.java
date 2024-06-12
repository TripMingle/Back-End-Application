package com.example.tripmingle.common.config.oauth;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.oauth2.client.OAuth2ClientProperties;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class KakaoProperties {

    private final OAuth2ClientProperties oAuth2ClientProperties;

    public String getClientId() {
        return oAuth2ClientProperties.getRegistration().get("kakao").getClientId();
    }

    public String getClientSecret() {
        return oAuth2ClientProperties.getRegistration().get("kakao").getClientSecret();
    }

    public String getKauthTokenUrlHost() {
        return oAuth2ClientProperties.getProvider().get("kakao").getTokenUri();
    }

    public String getKapiUserInfoHost() {
        return oAuth2ClientProperties.getProvider().get("kakao").getUserInfoUri();
    }

    public String getKauthAuthorizeUri() {
        return oAuth2ClientProperties.getProvider().get("kakao").getAuthorizationUri();
    }

}
