package com.example.tripmingle.adapter.out;

import com.example.tripmingle.common.exception.ChatRoomNotFoundException;
import com.example.tripmingle.entity.GroupChatRoom;
import com.example.tripmingle.port.out.GroupChatRoomPersistPort;
import com.example.tripmingle.repository.GroupChatRoomRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import static com.example.tripmingle.common.error.ErrorCode.CHATROOM_NOT_FOUND;

@Component
@RequiredArgsConstructor
public class GroupChatRoomPersistAdapter implements GroupChatRoomPersistPort {

    private final GroupChatRoomRepository groupChatRoomRepository;

    @Override
    public boolean existsChatRoom(Long boardId, Long userId) {
        return groupChatRoomRepository.existsByBoardIdAndUserId(boardId, userId);
    }

    @Override
    public GroupChatRoom save(GroupChatRoom newChatRoom) {
        return groupChatRoomRepository.save(newChatRoom);
    }

    @Override
    public GroupChatRoom getGroupChatRoomByChatRoomId(Long chatRoomId) {
        return groupChatRoomRepository.findById(chatRoomId)
                .orElseThrow(() -> new ChatRoomNotFoundException("ChatRoom Not Found", CHATROOM_NOT_FOUND));
    }
}
