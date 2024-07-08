package com.example.tripmingle.port.out;

import com.example.tripmingle.entity.OneOnOneChatRoom;

public interface OneOnOneChatRoomPersistPort {

    OneOnOneChatRoom save(OneOnOneChatRoom oneOnOneChatRoom);

    OneOnOneChatRoom getOneOnOneChatRoomByChatRoomId(Long oneOnOneChatRoomId);

}
