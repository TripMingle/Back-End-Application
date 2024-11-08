package com.example.tripmingle.port.in;

import org.springframework.web.multipart.MultipartFile;

import com.example.tripmingle.dto.req.user.PatchUserMyPageReqDTO;
import com.example.tripmingle.dto.res.user.GetUserInfoResDTO;
import com.example.tripmingle.dto.res.user.GetUserProfileResDTO;
import com.example.tripmingle.dto.res.user.PatchUserMyPageResDTO;
import com.example.tripmingle.dto.res.user.UploadUserImageResDTO;

public interface UserUseCase {
	PatchUserMyPageResDTO updateUserMyPage(PatchUserMyPageReqDTO patchUserMyPageReqDTO);

	UploadUserImageResDTO uploadMyPageUserImage(MultipartFile image);

	GetUserInfoResDTO getUserInfo();

	GetUserProfileResDTO getUserProfile(Long userId);

}
