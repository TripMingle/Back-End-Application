package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.OneOnOneChatRoomNotFoundException;
import com.example.tripmingle.entity.OneOnOneChatRoom;
import com.example.tripmingle.port.out.OneOnOneChatRoomPersistPort;
import com.example.tripmingle.repository.OneOnOneChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OneOnOneChatRoomPersistAdapter implements OneOnOneChatRoomPersistPort {

    private final OneOnOneChatRoomRepository oneOnOneChatRoomRepository;

    @Override
    public OneOnOneChatRoom save(OneOnOneChatRoom oneOnOneChatRoom) {
        return oneOnOneChatRoomRepository.save(oneOnOneChatRoom);
    }

    @Override
    public OneOnOneChatRoom getOneOnOneChatRoomByChatRoomId(Long oneOnOneChatRoomId) {
        return oneOnOneChatRoomRepository.findById(oneOnOneChatRoomId)
            .orElseThrow(() -> new OneOnOneChatRoomNotFoundException("One on One Chat Room Not Found",
                ErrorCode.ONE_ON_ONE_CHAT_ROOM_NOT_FOUND));
    }

    @Override
    public boolean existsOneOnOneChatRoomByUserIds(Long currentUserId, Long contactUserId) {
        return oneOnOneChatRoomRepository.existsByUser1IdAndUser2Id(currentUserId, contactUserId);
    }

    @Override
    public OneOnOneChatRoom getOneOnOneChatRoomByUserIds(Long currentUserId, Long contactUserId) {
        return oneOnOneChatRoomRepository.findByUser1IdAndUser2Id(currentUserId, contactUserId)
            .orElseThrow(() -> new OneOnOneChatRoomNotFoundException("One on One Chat Room Not Found",
                ErrorCode.ONE_ON_ONE_CHAT_ROOM_NOT_FOUND));
    }
}
