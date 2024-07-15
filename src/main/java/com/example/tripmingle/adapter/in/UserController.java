package com.example.tripmingle.adapter.in;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.tripmingle.common.result.ResultCode;
import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.user.PatchUserMyPageReqDTO;
import com.example.tripmingle.dto.res.user.PatchUserMyPageResDTO;
import com.example.tripmingle.port.in.UserUseCase;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserUseCase userUseCase;

	@PatchMapping("/my-page")
	public ResponseEntity<ResultResponse> updateUserMyPage(@RequestBody PatchUserMyPageReqDTO patchUserMyPageReqDTO) {
		PatchUserMyPageResDTO patchUserMyPageResDTO = userUseCase.updateUserMyPage(patchUserMyPageReqDTO);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.UPDATE_USER_MY_PAGE_SUCCESS, patchUserMyPageResDTO));
	}

}
