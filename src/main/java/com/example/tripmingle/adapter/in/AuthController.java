package com.example.tripmingle.adapter.in;

import static com.example.tripmingle.common.result.ResultCode.*;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.auth.ValidateDuplicationReqDTO;
import com.example.tripmingle.dto.res.auth.TokenDTO;
import com.example.tripmingle.dto.res.auth.ValidateDuplicationResDTO;
import com.example.tripmingle.port.in.AuthUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Auth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthUseCase authUseCase;

	@Operation(summary = "닉네임 중복 여부 확인")
	@GetMapping("/validate/duplication")
	public ResponseEntity<ResultResponse> validateDuplication(
		@RequestBody ValidateDuplicationReqDTO validateDuplicationReqDTO) {
		ValidateDuplicationResDTO validateDuplicationResDTO = authUseCase.validateDuplication(
			validateDuplicationReqDTO);
		return ResponseEntity.ok(ResultResponse.of(VALIDATE_COMPLETE, validateDuplicationResDTO));
	}

	@Operation(summary = "access token 재발급")
	@GetMapping("/refresh")
	public ResponseEntity<ResultResponse> getAcessTokenByRefreshToken(
		@RequestHeader("Refresh") String refreshToken) {
		TokenDTO tokenDTO = authUseCase.getAccessTokenByRefreshToken(refreshToken);
		HttpHeaders headers = new HttpHeaders();
		headers.add("access-token", tokenDTO.getAccessToken());
		headers.add("refresh-token", refreshToken);
		return ResponseEntity.ok()
			.headers(headers)
			.body(ResultResponse.of(ACCESS_TOKEN_ISSUE_SUCCESS));
	}

	//    @Operation(summary = "로그아웃")
	//    @PostMapping("/logout")
	//    public ResponseEntity<ResultResponse> logout() {
	//        LogoutResDTO logoutResDTO = authUseCase.logout();
	//        return ResponseEntity.ok(ResultResponse.of(LOGOUT_SUCCESS, logoutResDTO));
	//    }

}
