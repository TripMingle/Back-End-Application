package com.example.tripmingle.adapter.out;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

import com.example.tripmingle.entity.ChatMessage;
import com.example.tripmingle.port.out.ChatPersistPort;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class ChatPersistAdapter implements ChatPersistPort {

	//private final ChatRepository chatRepository;

	@Override
	public Slice<ChatMessage> getChatMessages(Long chatRoomId, Pageable pageable) {
		return null;
	}

	@Override
	public void save(ChatMessage chatMessage) {

	}
}
