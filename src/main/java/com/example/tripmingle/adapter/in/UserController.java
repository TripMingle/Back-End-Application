package com.example.tripmingle.adapter.in;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.tripmingle.common.result.ResultCode;
import com.example.tripmingle.common.result.ResultResponse;
import com.example.tripmingle.dto.req.user.PatchUserMyPageReqDTO;
import com.example.tripmingle.dto.res.user.GetUserInfoForMyPageResDTO;
import com.example.tripmingle.dto.res.user.GetUserInfoResDTO;
import com.example.tripmingle.dto.res.user.GetUserProfileResDTO;
import com.example.tripmingle.dto.res.user.PatchUserMyPageResDTO;
import com.example.tripmingle.dto.res.user.UploadUserImageResDTO;
import com.example.tripmingle.port.in.UserUseCase;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;

@Tag(name = "유저")
@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

	private final UserUseCase userUseCase;

	@Operation(summary = "마이페이지 정보 수정 - 유저 사진 등록")
	@PostMapping(value = "/my-page/user-image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public ResponseEntity<ResultResponse> uploadMyPageUserImage(@RequestPart("image") MultipartFile image) {
		UploadUserImageResDTO uploadUserImageResDTO = userUseCase.uploadMyPageUserImage(image);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.UPLOAD_USER_IMAGE_SUCCESS, uploadUserImageResDTO));
	}

	@Operation(summary = "마이페이지 정보 수정")
	@PatchMapping("/my-page")
	public ResponseEntity<ResultResponse> updateUserMyPage(@RequestBody PatchUserMyPageReqDTO patchUserMyPageReqDTO) {
		PatchUserMyPageResDTO patchUserMyPageResDTO = userUseCase.updateUserMyPage(patchUserMyPageReqDTO);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.UPDATE_USER_MY_PAGE_SUCCESS, patchUserMyPageResDTO));
	}

	@Operation(summary = "마이페이지 정보 수정을 위한 유저 정보 조회")
	@GetMapping("/my-page")
	public ResponseEntity<ResultResponse> getUserInfoForUpdatingUserInfo() {
		GetUserInfoForMyPageResDTO getUserInfoForMyPageResDTO = userUseCase.getUserInfoForUpdatingUserInfo();
		return ResponseEntity.ok(
			ResultResponse.of(ResultCode.GET_USER_INFO_FOR_MY_PAGE_SUCCESS, getUserInfoForMyPageResDTO));
	}

	@Operation(summary = "로그인(접속 중인) 유저 정보 조회")
	@GetMapping("/info")
	public ResponseEntity<ResultResponse> getUserInfo() {
		GetUserInfoResDTO getUserInfoDTO = userUseCase.getUserInfo();
		return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_USER_INFO_SUCCESS, getUserInfoDTO));
	}

	@Operation(summary = "유저 프로필 (클릭시) 조회")
	@GetMapping("/my-page/profile")
	public ResponseEntity<ResultResponse> getUserProfile(@RequestParam("userId") Long userId) {
		GetUserProfileResDTO getUserProfileResDTO = userUseCase.getUserProfile(userId);
		return ResponseEntity.ok(ResultResponse.of(ResultCode.GET_USER_PROFILE_SUCCESS, getUserProfileResDTO));
	}

}
