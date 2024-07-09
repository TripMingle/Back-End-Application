package com.example.tripmingle.entity;

import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
//import org.springframework.data.mongodb.core.mapping.Document;

@Getter
//@Document(collection = "chat")
public class ChatMessage {

	@Id
	private String id;

	private String content;

	private Long senderId;

	private ChatRoomType chatRoomType;

	private Long chatRoomId;

	private String sendingTime;

	@Builder
	public ChatMessage(String content, Long senderId, ChatRoomType chatRoomType, Long chatRoomId,
		String sendingTime) {
		this.content = content;
		this.senderId = senderId;
		this.chatRoomType = chatRoomType;
		this.chatRoomId = chatRoomId;
		this.sendingTime = sendingTime;
	}
}
