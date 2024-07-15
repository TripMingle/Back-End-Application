package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.req.user.PatchUserMyPageReqDTO;
import com.example.tripmingle.dto.res.user.PatchUserMyPageResDTO;

public interface UserUseCase {
	PatchUserMyPageResDTO updateUserMyPage(PatchUserMyPageReqDTO patchUserMyPageReqDTO);
}
