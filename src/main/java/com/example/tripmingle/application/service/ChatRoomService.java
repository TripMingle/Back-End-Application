package com.example.tripmingle.application.service;

import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.AlreadyExistsChatRoomException;
import com.example.tripmingle.entity.*;
import com.example.tripmingle.port.out.ChatRoomUserPersistPort;
import com.example.tripmingle.port.out.GroupChatRoomPersistPort;
import com.example.tripmingle.port.out.OneOnOneChatRoomPersistPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

    private final GroupChatRoomPersistPort groupChatRoomPersistPort;
    private final OneOnOneChatRoomPersistPort oneOnOneChatRoomPersistPort;
    private final ChatRoomUserPersistPort chatRoomUserPersistPort;

    public GroupChatRoom createGroupChatRoom(Board board, User masterUser) {
        if (groupChatRoomPersistPort.existsChatRoom(board.getId(), masterUser.getId())) {
            throw new AlreadyExistsChatRoomException("Already exists chat room", ErrorCode.ALREADY_EXISTS_CHAT_ROOM);
        }
        GroupChatRoom newChatRoom = GroupChatRoom.builder()
                .board(board)
                .user(masterUser)
                .build();
        return groupChatRoomPersistPort.save(newChatRoom);
    }

    public GroupChatRoom getGroupChatRoomByChatRoomId(Long chatRoomId) {
        return groupChatRoomPersistPort.getGroupChatRoomByChatRoomId(chatRoomId);
    }

    public OneOnOneChatRoom createOneOnOneChatRoom(OneOnOneChatRoom oneOnOneChatRoom) {
        return oneOnOneChatRoomPersistPort.save(oneOnOneChatRoom);
    }

    public ChatRoomUser joinGroupChatRoom(ChatRoomUser chatRoomUser) {
        return chatRoomUserPersistPort.save(chatRoomUser);
    }

    public ChatRoomUser getChatRoomUserByChatRoomIdAndUserId(Long groupChatRoomId, Long userId) {
        return chatRoomUserPersistPort.getChatRoomUserByChatRoomIdAndUserId(groupChatRoomId, userId);
    }

    public OneOnOneChatRoom getOneOnOneChatRoomByChatRoomId(Long oneOnOneChatRoomId) {
        return oneOnOneChatRoomPersistPort.getOneOnOneChatRoomByChatRoomId(oneOnOneChatRoomId);
    }

    public List<ChatRoomUser> getChatRoomUsersByChatRoomIdAndUserId(Long chatRoomId, Long userId) {
        return chatRoomUserPersistPort.getChatRoomUsersByChatRoomIdAndUserId(chatRoomId, userId);
    }

    public boolean existsUserInChatRoom(Long userId) {
        return chatRoomUserPersistPort.existsUserInChatRoom(userId);
    }
}

