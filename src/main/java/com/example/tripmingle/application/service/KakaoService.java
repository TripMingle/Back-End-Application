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
import com.example.tripmingle.dto.etc.KakaoUserInfo;
import com.example.tripmingle.dto.req.user.AdditionalUserDetailReqDTO;
import com.example.tripmingle.dto.res.oauth.GetKakaoUserDataResDTO;
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
		GetKakaoUserDataResDTO getKakaoUserDataResDTO = kakaoOutPort.getKakaoUserInfo(redefineAccessToken);
		User user = userPersistPort.findByEmail(getKakaoUserDataResDTO.getKakaoUserInfo().getEmail());
		KakaoLoginDTO kakaoLoginDTO = generateKakaoTokenDTO(user);
		return kakaoLoginDTO;
	}

	public KakaoLoginDTO joinKakaoAccount(AdditionalUserDetailReqDTO additionalUserDetailReqDTO) {
		String redefineAccessToken = "Bearer " + additionalUserDetailReqDTO.getKakaoAccessToken();
		GetKakaoUserDataResDTO getKakaoUserDataResDTO = kakaoOutPort.getKakaoUserInfo(redefineAccessToken);
		KakaoUserInfo kakaoUserInfo = getKakaoUserDataResDTO.getKakaoUserInfo();
		validateAlreadyExistsUser(kakaoUserInfo.getEmail());
		User newUser = generateUser(getKakaoUserDataResDTO, additionalUserDetailReqDTO);
		KakaoLoginDTO kakaoLoginDTO = generateKakaoTokenDTO(newUser);
		return kakaoLoginDTO;
	}

	private KakaoLoginDTO generateKakaoTokenDTO(User user) {
		String accessToken = jwtUtils.createJwtAccessToken(user.getEmail(), user.getRole(), user.getLoginType());
		String refreshToken = jwtUtils.createJwtRefreshToken(user.getEmail(), user.getRole(), user.getLoginType());
		addRefreshEntity(user.getEmail(), refreshToken, jwtUtils.getRefreshTokenExpTimeByToken(refreshToken));
		return KakaoLoginDTO.builder()
			.accessToken(accessToken)
			.refreshToken(refreshToken)
			.build();
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

	private void validateAlreadyExistsUser(String email) {
		if (userPersistPort.existsByEmail(email)) {
			throw new UserNotFoundException("User Already Exists.", KAKAO_ALREADY_EXISTS_USER);
		}
	}

	private User generateUser(GetKakaoUserDataResDTO getKakaoUserDataResDTO,
		AdditionalUserDetailReqDTO additionalUserDetailReqDTO) {
		KakaoUserInfo kakaoUserInfo = getKakaoUserDataResDTO.getKakaoUserInfo();
		return userPersistPort.save(User.builder()
			.email(kakaoUserInfo.getEmail())
			.password(passwordEncoder.encode(
				"TripMingle " + kakaoUserInfo.getEmail() + getKakaoUserDataResDTO.getKakaoId()))
			.role("ROLE_USER")
			.loginType(KAKAO.getLoginType())
			.oauthId(getKakaoUserDataResDTO.getKakaoId())
			.nickName(additionalUserDetailReqDTO.getNickName())
			.ageRange(kakaoUserInfo.getAgeRange())
			.gender(kakaoUserInfo.getGender())
			.name(kakaoUserInfo.getName())
			.nationality(additionalUserDetailReqDTO.getNationality())
			.phoneNumber(kakaoUserInfo.getPhoneNumber())
			.build());
	}

	public KakaoTokenResDTO getKakaoAccessToken(String code) {
		return kakaoOutPort.getToken(kakaoProperties.getKakaoGrantType(),
			kakaoProperties.getKakaoClientId(), kakaoProperties.getKakaoSecretKey(), code);
	}

}
