package com.example.tripmingle.port.out;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import com.example.tripmingle.dto.req.chat.GetAllChatMessageReqDTO;
import com.example.tripmingle.entity.ChatMessage;

public interface ChatPersistPort {

	Slice<ChatMessage> getChatMessages(GetAllChatMessageReqDTO getAllChatMessageReqDTO, Pageable pageable);

	void save(ChatMessage chatMessage);

}
