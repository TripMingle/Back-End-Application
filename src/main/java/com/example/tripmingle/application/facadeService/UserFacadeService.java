package com.example.tripmingle.application.facadeService;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.dto.req.user.PatchUserMyPageReqDTO;
import com.example.tripmingle.dto.res.user.PatchUserMyPageResDTO;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.in.UserUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UserFacadeService implements UserUseCase {

	private final UserService userService;

	@Transactional
	@Override
	public PatchUserMyPageResDTO updateUserMyPage(PatchUserMyPageReqDTO patchUserMyPageReqDTO) {
		User currentUser = userService.getCurrentUser();
		currentUser.updateUserMyPage(patchUserMyPageReqDTO);
		return PatchUserMyPageResDTO.builder()
			.userId(currentUser.getId())
			.build();
	}
}
