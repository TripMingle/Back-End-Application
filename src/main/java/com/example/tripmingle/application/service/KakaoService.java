package com.example.tripmingle.application.service;

import com.example.tripmingle.common.exception.UserNotFoundException;
import com.example.tripmingle.common.utils.JwtUtils;
import com.example.tripmingle.common.utils.KakaoProperties;
import com.example.tripmingle.dto.etc.KakaoUserInfo;
import com.example.tripmingle.dto.etc.TokenDTO;
import com.example.tripmingle.dto.req.KakaoUserAdditionDetailsReqDTO;
import com.example.tripmingle.dto.res.KakaoLoginResDTO;
import com.example.tripmingle.dto.res.KakaoTokenResDTO;
import com.example.tripmingle.entity.Refresh;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.KakaoLoginFeignClientForAccessTokenPort;
import com.example.tripmingle.port.out.KakaoLoginFeignClientPort;
import com.example.tripmingle.port.out.RefreshPort;
import com.example.tripmingle.port.out.UserPersistPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

import static com.example.tripmingle.common.constants.JwtConstants.ACCESS_TOKEN;
import static com.example.tripmingle.common.constants.JwtConstants.REFRESH_TOKEN;
import static com.example.tripmingle.common.constants.LoginType.KAKAO;
import static com.example.tripmingle.common.error.ErrorCode.KAKAO_NO_EXISTS_USER;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class KakaoService {

    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final KakaoProperties kakaoProperties;
    private final UserPersistPort userPersistPort;
    private final RefreshPort refreshPort;
    private final KakaoLoginFeignClientPort kakaoLoginFeignClientPort;
    private final KakaoLoginFeignClientForAccessTokenPort kakaoLoginFeignClientForAccessTokenPort;

    @Transactional
    public TokenDTO loginKakaoAccount(KakaoUserAdditionDetailsReqDTO kakaoUserAdditionDetailsReqDTO) {
        String redefineAccessToken = "Bearer " + kakaoUserAdditionDetailsReqDTO.getKakaoAccessToken();
        KakaoLoginResDTO kakaoLoginResDTO = kakaoLoginFeignClientPort.getKakaoUserInfo(redefineAccessToken);

        User user = joinStateCheckAndReturnUser(kakaoLoginResDTO, kakaoUserAdditionDetailsReqDTO);
        userNullCheck(user);

        String accessToken = jwtUtils.createJwtToken(user.getEmail(), user.getRole(), user.getLoginType(), ACCESS_TOKEN.getMessage(), jwtUtils.getAccessTokenExpTime());
        String refreshToken = jwtUtils.createJwtToken(user.getEmail(), user.getRole(), user.getLoginType(), REFRESH_TOKEN.getMessage(), jwtUtils.getRefreshTokenExpTime());
        addRefreshEntity(user.getEmail(), refreshToken, jwtUtils.getRefreshTokenExpTimeByToken(refreshToken));

        return TokenDTO.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .build();
    }

    private User joinStateCheckAndReturnUser(KakaoLoginResDTO kakaoLoginResDTO, KakaoUserAdditionDetailsReqDTO kakaoUserAdditionDetailsReqDTO) {
        KakaoUserInfo kakaoUserInfo = kakaoLoginResDTO.getKakaoUserInfo();
        User user = null;
        if (!userPersistPort.existsByEmail(kakaoUserInfo.getEmail())) {
            user = userPersistPort.save(User.builder()
                    .email(kakaoUserInfo.getEmail())
                    .password(passwordEncoder.encode("TripMingle " + kakaoUserInfo.getEmail() + kakaoLoginResDTO.getKakaoId()))
                    .role("ROLE_USER")
                    .loginType(KAKAO.getLoginType())
                    .oauthId(kakaoLoginResDTO.getKakaoId())
                    .nickName(kakaoUserAdditionDetailsReqDTO.getNickName())
                    .ageRange(kakaoUserInfo.getAgeRange())
                    .gender(kakaoUserInfo.getGender())
                    .name(kakaoUserInfo.getName())
                    .nationality(kakaoUserAdditionDetailsReqDTO.getNationality())
                    .phoneNumber(kakaoUserInfo.getPhoneNumber())
                    .build());
        } else {
            user = userPersistPort.findByEmail(kakaoUserInfo.getEmail());
        }
        return user;
    }

    private void userNullCheck(User user) {
        if (user == null) {
            throw new UserNotFoundException("User Not Found.", KAKAO_NO_EXISTS_USER);
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
        return kakaoLoginFeignClientForAccessTokenPort.getToken(kakaoProperties.getKakaoGrantType(), kakaoProperties.getKakaoClientId(), kakaoProperties.getKakaoSecretKey(), code);
    }
}
