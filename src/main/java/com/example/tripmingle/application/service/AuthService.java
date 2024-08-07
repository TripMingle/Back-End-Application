package com.example.tripmingle.application.service;

import org.springframework.stereotype.Service;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.ExpiredRefreshTokenException;
import com.example.tripmingle.common.exception.InvalidUserAccessException;
import com.example.tripmingle.common.utils.JwtUtils;
import com.example.tripmingle.dto.res.auth.TokenDTO;
import com.example.tripmingle.dto.res.auth.ValidateDuplicationResDTO;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.out.RefreshPort;
import com.example.tripmingle.port.out.UserPersistPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

	private final JwtUtils jwtUtils;
	private final UserPersistPort userPersistPort;
	private final RefreshPort refreshPort;

	public ValidateDuplicationResDTO validateDuplication(String nickName) {
		boolean duplicationStatus = userPersistPort.existsByNickName(nickName);
		System.out.println(duplicationStatus);
		return ValidateDuplicationResDTO.builder()
			.duplicationStatus(duplicationStatus)
			.build();
	}

	public boolean validateMasterUser(Long validatingUserId) {
		User user = userPersistPort.findCurrentUserByEmail();
		if (validatingUserId.equals(user.getId())) {
			return true;
		} else {
			throw new InvalidUserAccessException("Invalid User Access.", ErrorCode.INVALID_USER_ACCESS);
		}
	}

	public void deleteRefresh(String email) {
		refreshPort.deleteRefresh(email);
	}

	public TokenDTO getAccessTokenByRefreshToken(String refreshToken) {
		if (jwtUtils.isExpired(refreshToken)) {
			throw new ExpiredRefreshTokenException("Invalid Refresh Token.", ErrorCode.INVALID_REFRESH_TOKEN);
		}
		String newAccessToken = parsingAndCreateJwtAccessToken(refreshToken);
		return TokenDTO.builder()
			.accessToken(newAccessToken)
			.build();
	}

	private String parsingAndCreateJwtAccessToken(String refreshToken) {
		String email = jwtUtils.getEmail(refreshToken);
		String role = jwtUtils.getRole(refreshToken);
		String loginType = jwtUtils.getLoginType(refreshToken);
		return jwtUtils.createJwtAccessToken(email, role, loginType);
	}
}
