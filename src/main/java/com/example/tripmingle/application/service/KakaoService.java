package com.example.tripmingle.application.service;

import static com.example.tripmingle.common.constants.LoginType.*;
import static com.example.tripmingle.common.error.ErrorCode.*;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.tripmingle.common.exception.UserNotFoundException;
import com.example.tripmingle.common.utils.JwtUtils;
import com.example.tripmingle.common.utils.KakaoProperties;
import com.example.tripmingle.dto.etc.KakaoLoginDTO;
import com.example.tripmingle.dto.etc.KakaoUserInfo;
import com.example.tripmingle.dto.req.user.AdditionalUserDetailReqDTO;
import com.example.tripmingle.dto.res.auth.TokenDTO;
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
		User user = null;
		KakaoLoginDTO result = null;
		if (userPersistPort.existsByEmail(getKakaoUserDataResDTO.getKakaoUserInfo().getEmail())) {
			user = userPersistPort.findByEmail(getKakaoUserDataResDTO.getKakaoUserInfo().getEmail());
			TokenDTO tokens = generateKakaoTokenDTO(user);
			result = KakaoLoginDTO.builder()
				.isMemberState(true)
				.accessToken(tokens.getAccessToken())
				.refreshToken(tokens.getRefreshToken())
				.nickName(user.getNickName())
				.profileImage(user.getUserImageUrl() == null ? "" : user.getUserImageUrl())
				.build();
		}
		if (user == null) {
			result = KakaoLoginDTO.builder()
				.isMemberState(false)
				.accessToken("")
				.refreshToken("")
				.nickName("")
				.profileImage("")
				.build();
		}
		return result;
	}

	public KakaoLoginDTO joinKakaoAccount(AdditionalUserDetailReqDTO additionalUserDetailReqDTO) {
		String redefineAccessToken = "Bearer " + additionalUserDetailReqDTO.getKakaoAccessToken();
		GetKakaoUserDataResDTO getKakaoUserDataResDTO = kakaoOutPort.getKakaoUserInfo(redefineAccessToken);
		KakaoUserInfo kakaoUserInfo = getKakaoUserDataResDTO.getKakaoUserInfo();
		validateAlreadyExistsUser(kakaoUserInfo.getEmail());
		User newUser = generateUser(getKakaoUserDataResDTO, additionalUserDetailReqDTO);
		TokenDTO tokens = generateKakaoTokenDTO(newUser);
		return KakaoLoginDTO.builder()
			.accessToken(tokens.getAccessToken())
			.refreshToken(tokens.getRefreshToken())
			.isMemberState(true)
			.nickName(newUser.getNickName())
			.profileImage(newUser.getUserImageUrl() == null ? "" : newUser.getUserImageUrl())
			.build();
	}

	private TokenDTO generateKakaoTokenDTO(User user) {
		String accessToken = jwtUtils.createJwtAccessToken(user.getEmail(), user.getRole(), user.getLoginType());
		String refreshToken = jwtUtils.createJwtRefreshToken(user.getEmail(), user.getRole(), user.getLoginType());
		addRefreshEntity(user.getEmail(), refreshToken, jwtUtils.getRefreshTokenExpTimeByToken(refreshToken));
		return TokenDTO.builder()
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
			.selfIntroduction(additionalUserDetailReqDTO.getSelfIntroduction())
			.ageRange(calculateAgeRange(additionalUserDetailReqDTO.getBirthDay()))
			.gender(additionalUserDetailReqDTO.getGender())
			.name(additionalUserDetailReqDTO.getName())
			.nationality(additionalUserDetailReqDTO.getNationality())
			.phoneNumber(additionalUserDetailReqDTO.getPhoneNumber())
			.birthDay(additionalUserDetailReqDTO.getBirthDay())
			.build());
	}

	public KakaoTokenResDTO getKakaoAccessToken(String code) {
		return kakaoOutPort.getToken(kakaoProperties.getKakaoGrantType(),
			kakaoProperties.getKakaoClientId(), kakaoProperties.getKakaoSecretKey(), code);
	}

	public String calculateAgeRange(LocalDate birthDay) {
		LocalDate now = LocalDate.now();

		int age = (int)ChronoUnit.YEARS.between(birthDay, now);

		// 올해 생일을 이미 지났는지 확인
		if (now.isBefore(birthDay.plusYears(age))) {
			age -= 1;
		}

		String ageGroup;
		switch (age / 10) { // 나이를 10으로 나눠서 연령대 구하기
			case 0 -> ageGroup = "유아기";
			case 1 -> ageGroup = "10대";
			case 2 -> ageGroup = "20대";
			case 3 -> ageGroup = "30대";
			case 4 -> ageGroup = "40대";
			case 5 -> ageGroup = "50대";
			case 6 -> ageGroup = "60대";
			case 7 -> ageGroup = "70대";
			case 8 -> ageGroup = "80대";
			case 9 -> ageGroup = "90대";
			default -> ageGroup = "100세 이상";
		}
		return ageGroup;
	}

}
