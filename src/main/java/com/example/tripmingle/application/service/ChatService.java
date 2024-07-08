package com.example.tripmingle.application.service;

import com.example.tripmingle.entity.ChatMessage;
import com.example.tripmingle.port.out.ChatPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChatService {

    private final ChatPersistPort chatPersistPort;

    public Slice<ChatMessage> getChatMessages(Long chatRoomId, Pageable pageable) {
        return chatPersistPort.getChatMessages(chatRoomId, pageable);
    }

}
