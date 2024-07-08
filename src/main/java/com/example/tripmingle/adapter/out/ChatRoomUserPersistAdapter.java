package com.example.tripmingle.adapter.out;


import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.ChatRoomUserNotFoundException;
import com.example.tripmingle.entity.ChatRoomUser;
import com.example.tripmingle.port.out.ChatRoomUserPersistPort;
import com.example.tripmingle.repository.ChatRoomUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ChatRoomUserPersistAdapter implements ChatRoomUserPersistPort {

    private final ChatRoomUserRepository chatRoomUserRepository;

    @Override
    public ChatRoomUser save(ChatRoomUser chatRoomUser) {
        return chatRoomUserRepository.save(chatRoomUser);
    }

    @Override
    public ChatRoomUser getChatRoomUserByChatRoomIdAndUserId(Long groupChatRoomId, Long userId) {
        return chatRoomUserRepository.findByChatRoomIdAndUserId(groupChatRoomId, userId)
                .orElseThrow(() -> new ChatRoomUserNotFoundException("User in Chat Room Not Found", ErrorCode.USER_IN_CHAT_ROOM_NOT_FOUND));
    }

    @Override
    public List<ChatRoomUser> getChatRoomUsersByChatRoomIdAndUserId(Long chatRoomId, Long userId) {
        return chatRoomUserRepository.findAllByChatRoomIdAndUserId(chatRoomId, userId);
    }

    @Override
    public boolean existsUserInChatRoom(Long userId) {
        return chatRoomUserRepository.existsByUserId(userId);
    }
}
