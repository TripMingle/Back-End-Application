package com.example.tripmingle.application.service;

import static com.example.tripmingle.common.constants.LoginType.*;
import static com.example.tripmingle.common.error.ErrorCode.*;

import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tripmingle.common.exception.UserNotFoundException;
import com.example.tripmingle.common.utils.JwtUtils;
import com.example.tripmingle.common.utils.KakaoProperties;
import com.example.tripmingle.dto.etc.KakaoLoginDTO;
import com.example.tripmingle.dto.res.oauth.KakaoLoginResDTO;
import com.example.tripmingle.dto.res.oauth.KakaoTokenResDTO;
import com.example.tripmingle.entity.Refresh;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.KakaoOutPort;
import com.example.tripmingle.port.out.RefreshPort;
import com.example.tripmingle.port.out.UserPersistPort;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class KakaoService {

	private final JwtUtils jwtUtils;
	private final PasswordEncoder passwordEncoder;
	private final KakaoProperties kakaoProperties;
	private final UserPersistPort userPersistPort;
	private final RefreshPort refreshPort;
	private final KakaoOutPort kakaoOutPort;

	public KakaoLoginDTO loginKakaoAccount(String kakaoAccessToken) {
		String redefineAccessToken = "Bearer " + kakaoAccessToken;
		KakaoLoginResDTO kakaoLoginResDTO = kakaoOutPort.getKakaoUserInfo(redefineAccessToken);
		System.out.println(kakaoLoginResDTO.getKakaoUserInfo());
		User user = null;
		boolean joinState = false;
		if (isExistingKakaoUser(kakaoLoginResDTO.getKakaoUserInfo().getEmail())) {
			user = userPersistPort.findByEmail(kakaoLoginResDTO.getKakaoUserInfo().getEmail());
			joinState = true;
		} else {
			user = userPersistPort.save(User.builder()
				.email(kakaoLoginResDTO.getKakaoUserInfo().getEmail())
				.password(
					passwordEncoder.encode(
						"TripMingle " + kakaoLoginResDTO.getKakaoUserInfo().getEmail() + kakaoLoginResDTO.getKakaoId()))
				.role("ROLE_USER")
				.loginType(KAKAO.getLoginType())
				.oauthId(kakaoLoginResDTO.getKakaoId())
				.ageRange(kakaoLoginResDTO.getKakaoUserInfo().getAgeRange())
				.gender(kakaoLoginResDTO.getKakaoUserInfo().getGender())
				.name(kakaoLoginResDTO.getKakaoUserInfo().getName())
				.phoneNumber(kakaoLoginResDTO.getKakaoUserInfo().getPhoneNumber())
				.build());
		}
		userNullCheck(user);

		String accessToken = jwtUtils.createJwtAccessToken(user.getEmail(), user.getRole(), user.getLoginType());
		String refreshToken = jwtUtils.createJwtRefreshToken(user.getEmail(), user.getRole(), user.getLoginType());
		addRefreshEntity(user.getEmail(), refreshToken, jwtUtils.getRefreshTokenExpTimeByToken(refreshToken));

		return KakaoLoginDTO.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.joinedUserState(joinState)
			.build();
	}

	private boolean isExistingKakaoUser(String email) {
		return userPersistPort.existsByEmail(email);
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
		return kakaoOutPort.getToken(kakaoProperties.getKakaoGrantType(),
			kakaoProperties.getKakaoClientId(), kakaoProperties.getKakaoSecretKey(), code);
	}
}
