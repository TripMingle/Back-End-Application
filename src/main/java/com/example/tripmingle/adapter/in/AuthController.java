package com.example.tripmingle.adapter.in;

import static com.example.tripmingle.common.result.ResultCode.*;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.auth.ValidateDuplicationReqDTO;
import com.example.tripmingle.dto.res.auth.ValidateDuplicationResDTO;
import com.example.tripmingle.port.in.AuthUseCase;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "Auth")
@RestController
@RequiredArgsConstructor
@RequestMapping("/auth")
public class AuthController {

	private final AuthUseCase authUseCase;

	@GetMapping("/validate/duplication")
	public ResponseEntity<ResultResponse> validateDuplication(
		@RequestBody ValidateDuplicationReqDTO validateDuplicationReqDTO) {
		ValidateDuplicationResDTO validateDuplicationResDTO = authUseCase.validateDuplication(
			validateDuplicationReqDTO);
		return ResponseEntity.ok().body(ResultResponse.of(VALIDATE_COMPLETE, validateDuplicationResDTO));
	}

}
