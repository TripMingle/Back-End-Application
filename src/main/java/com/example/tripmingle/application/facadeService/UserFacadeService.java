package com.example.tripmingle.application.facadeService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import com.example.tripmingle.application.service.S3Service;
import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.dto.req.user.PatchUserMyPageReqDTO;
import com.example.tripmingle.dto.res.user.GetUserInfoForMyPageResDTO;
import com.example.tripmingle.dto.res.user.GetUserInfoResDTO;
import com.example.tripmingle.dto.res.user.GetUserProfileResDTO;
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

	@Override
	public GetUserInfoForMyPageResDTO getUserInfoForUpdatingUserInfo() {
		User currentUser = userService.getCurrentUser();
		return GetUserInfoForMyPageResDTO.builder()
			.name(currentUser.getName())
			.gender(currentUser.getGender())
			.birthDay(currentUser.getBirthDay())
			.phoneNumber(currentUser.getPhoneNumber())
			.nickName(currentUser.getNickName())
			.nationality(currentUser.getNationality())
			.selfIntroduction(currentUser.getSelfIntroduction() == null ? "" : currentUser.getSelfIntroduction())
			.build();
	}

	@Transactional
	@Override
	public PatchUserMyPageResDTO updateUserMyPage(PatchUserMyPageReqDTO patchUserMyPageReqDTO) {
		User currentUser = userService.getCurrentUser();
		currentUser.updateUserMyPage(patchUserMyPageReqDTO);
		return PatchUserMyPageResDTO.builder()
			.userId(currentUser.getId())
			.name(currentUser.getName())
			.gender(currentUser.getGender())
			.birthDay(currentUser.getBirthDay())
			.phoneNumber(currentUser.getPhoneNumber())
			.nickName(currentUser.getNickName())
			.nationality(currentUser.getNationality())
			.selfIntroduction(currentUser.getSelfIntroduction() == null ? "" : currentUser.getSelfIntroduction())
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
			.userProfileImage(currentUser.getUserImageUrl() == null ? "" : currentUser.getUserImageUrl())
			.build();
	}

	@Override
	public GetUserProfileResDTO getUserProfile(Long userId) {
		User findUser = userService.getUserById(userId);
		return GetUserProfileResDTO.builder()
			.userId(userId)
			.userNickName(findUser.getNickName())
			.userImageUrl(findUser.getUserImageUrl() == null ? "" : findUser.getUserImageUrl())
			.userScore(findUser.getUserScore())
			.userGender(findUser.getGender())
			.userAgeRange(findUser.getAgeRange())
			.userNationality(findUser.getNationality())
			.selfIntroduction(findUser.getSelfIntroduction() == null ? "" : findUser.getSelfIntroduction())
			.build();
	}
}
