package com.example.tripmingle.dto.req.chat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAllChatMessageReqDTO {

	private String chatRoomType;
	private Long chatRoomId;

}
