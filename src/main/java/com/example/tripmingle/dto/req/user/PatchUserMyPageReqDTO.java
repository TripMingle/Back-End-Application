package com.example.tripmingle.dto.req.user;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchUserMyPageReqDTO {

	private String userImageUrl;
	private String userNickName;
	private String nationality;
	private String phoneNumber;
	private String userEmail;

}
