package com.example.tripmingle.dto.req.user;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdditionalUserDetailReqDTO {

	private String nickName;
	private String nationality;

}
