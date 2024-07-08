package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.ChatRoomUser;

import java.util.List;

public interface ChatRoomUserPersistPort {

    ChatRoomUser getChatRoomUserByChatRoomIdAndUserId(Long groupChatRoomId, Long userId);

    List<ChatRoomUser> getChatRoomUsersByChatRoomIdAndUserId(Long chatRoomId, Long userId);

    ChatRoomUser save(ChatRoomUser chatRoomUser);

    boolean existsUserInChatRoom(Long userId);
}
