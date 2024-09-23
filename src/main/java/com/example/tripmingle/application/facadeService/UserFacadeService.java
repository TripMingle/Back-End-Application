package com.example.tripmingle.application.facadeService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.tripmingle.application.service.S3Service;
import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.dto.req.user.PatchUserMyPageReqDTO;
import com.example.tripmingle.dto.res.user.GetUserInfoResDTO;
import com.example.tripmingle.dto.res.user.PatchUserMyPageResDTO;
import com.example.tripmingle.dto.res.user.UploadUserImageResDTO;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.in.UserUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFacadeService implements UserUseCase {

	private final UserService userService;
	private final S3Service s3Service;

	@Transactional
	@Override
	public PatchUserMyPageResDTO updateUserMyPage(PatchUserMyPageReqDTO patchUserMyPageReqDTO) {
		User currentUser = userService.getCurrentUser();
		currentUser.updateUserMyPage(patchUserMyPageReqDTO);
		return PatchUserMyPageResDTO.builder()
			.userId(currentUser.getId())
			.build();
	}

	@Transactional
	@Override
	public UploadUserImageResDTO uploadMyPageUserImage(MultipartFile image) {
		String userImageUrl = s3Service.uploadImage(image);
		User currentUser = userService.getCurrentUser();
		currentUser.uploadUserImage(userImageUrl);
		return UploadUserImageResDTO.builder()
			.userImageUrl(userImageUrl)
			.build();
	}

	@Override
	public GetUserInfoResDTO getUserInfo() {
		User currentUser = userService.getCurrentUser();
		return GetUserInfoResDTO.builder()
			.userNickName(currentUser.getNickName())
			.userProfileImage(currentUser.getUserImageUrl())
			.build();
	}
}
