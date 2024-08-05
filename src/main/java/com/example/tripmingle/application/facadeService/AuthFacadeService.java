package com.example.tripmingle.application.facadeService;

import org.springframework.stereotype.Service;

import com.example.tripmingle.application.service.AuthService;
import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.dto.req.auth.ValidateDuplicationReqDTO;
import com.example.tripmingle.dto.res.auth.LogoutResDTO;
import com.example.tripmingle.dto.res.auth.TokenDTO;
import com.example.tripmingle.dto.res.auth.ValidateDuplicationResDTO;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.in.AuthUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthFacadeService implements AuthUseCase {

	private final AuthService authService;
	private final UserService userService;

	@Override
	public ValidateDuplicationResDTO validateDuplication(ValidateDuplicationReqDTO validateDuplicationReqDTO) {
		return authService.validateDuplication(validateDuplicationReqDTO);
	}

	@Override
	public LogoutResDTO logout() {
		User user = userService.getCurrentUser();
		authService.deleteRefresh(user.getEmail());
		return LogoutResDTO.builder()
			.userId(user.getId())
			.build();
	}

	@Override
	public TokenDTO getAccessTokenByRefreshToken(String refreshToken) {
		return authService.getAccessTokenByRefreshToken(refreshToken);
	}
}
