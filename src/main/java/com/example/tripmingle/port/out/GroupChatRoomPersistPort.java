package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.GroupChatRoom;

public interface GroupChatRoomPersistPort {
    boolean existsChatRoom(Long id, Long id1);

    GroupChatRoom save(GroupChatRoom newChatRoom);

    GroupChatRoom getGroupChatRoomByChatRoomId(Long chatRoomId);
}
