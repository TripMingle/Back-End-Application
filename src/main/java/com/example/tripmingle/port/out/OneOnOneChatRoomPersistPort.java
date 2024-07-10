package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.OneOnOneChatRoom;

public interface OneOnOneChatRoomPersistPort {

    OneOnOneChatRoom save(OneOnOneChatRoom oneOnOneChatRoom);

    OneOnOneChatRoom getOneOnOneChatRoomByChatRoomId(Long oneOnOneChatRoomId);

	boolean existsOneOnOneChatRoomByUserIds(Long currentUserId, Long contactUserId);

	OneOnOneChatRoom getOneOnOneChatRoomByUserIds(Long currentUserId, Long contactUserId);
}
