package com.example.tripmingle.application.service;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.example.tripmingle.dto.req.chat.GetAllChatMessageReqDTO;
import com.example.tripmingle.entity.ChatMessage;
import com.example.tripmingle.entity.ChatRoomType;
import com.example.tripmingle.port.out.ChatPersistPort;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatService {

	private final ChatPersistPort chatPersistPort;

	public Slice<ChatMessage> getChatMessages(GetAllChatMessageReqDTO getAllChatMessageReqDTO, Pageable pageable) {
		return null;
	}

	public Long getChatMessagesCount(ChatRoomType chatRoomType, Long chatRoomId) {
		return chatPersistPort.getChatMessagesCount(chatRoomType, chatRoomId);
	}
}
