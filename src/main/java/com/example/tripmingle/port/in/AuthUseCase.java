package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.res.auth.LogoutResDTO;
import com.example.tripmingle.dto.res.auth.TokenDTO;
import com.example.tripmingle.dto.res.auth.ValidateDuplicationResDTO;

public interface AuthUseCase {

	ValidateDuplicationResDTO validateDuplication(String nickName);

	LogoutResDTO logout();

	TokenDTO getAccessTokenByRefreshToken(String refreshToken);
}
