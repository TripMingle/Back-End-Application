package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.ChatMessage;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

public interface ChatPersistPort {

    Slice<ChatMessage> getChatMessages(Long chatRoomId, Pageable pageable);

    void save(ChatMessage chatMessage);

}
