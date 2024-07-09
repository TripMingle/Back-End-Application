package com.example.tripmingle.adapter.out;

import com.example.tripmingle.entity.ChatMessage;
import com.example.tripmingle.port.out.ChatPersistPort;
import com.example.tripmingle.repository.ChatRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ChatPersistAdapter implements ChatPersistPort {

    private final ChatRepository chatRepository;

    @Override
    public Slice<ChatMessage> getChatMessages(Long chatRoomId, Pageable pageable) {
        return chatRepository.findAllByChatRoomId(chatRoomId, pageable);
    }

    @Override
    public void save(ChatMessage chatMessage) {
        chatRepository.save(chatMessage);
    }
}
