package com.example.tripmingle.dto.res.chat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetChatMessageResDTO {

	private Long userId;
	private String userName;
	private String message;
	private String sendingTime;

}
