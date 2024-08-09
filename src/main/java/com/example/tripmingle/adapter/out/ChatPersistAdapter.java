package com.example.tripmingle.adapter.out;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import com.example.tripmingle.dto.req.chat.GetAllChatMessageReqDTO;
import com.example.tripmingle.entity.ChatMessage;
import com.example.tripmingle.entity.ChatRoomType;
import com.example.tripmingle.port.out.ChatPersistPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatPersistAdapter implements ChatPersistPort {

	private final ChatRepository chatRepository;

	@Override
	public Slice<ChatMessage> getChatMessages(GetAllChatMessageReqDTO getAllChatMessageReqDTO, Pageable pageable) {
		// return chatRepository.findByChatRoomIdAndChatRoomType(
		// 	getAllChatMessageReqDTO.getChatRoomId(),
		// 	ChatRoomType.valueOf(getAllChatMessageReqDTO.getChatRoomType()), pageable);
		return null;
	}

	@Override
	public void save(ChatMessage chatMessage) {

	}

	@Override
	public Long getChatMessagesCount(ChatRoomType chatRoomType, Long chatRoomId) {
		// return chatRepository.countByChatRoomIdAndChatRoomType(chatRoomId, chatRoomType);
		return null;
	}
}
