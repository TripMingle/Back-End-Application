package com.example.tripmingle.application.facadeService;

import com.example.tripmingle.application.service.ChatRoomService;
import com.example.tripmingle.application.service.BoardService;
import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.common.error.ErrorCode;
import com.example.tripmingle.common.exception.AlreadyExistsChatRoomUserException;
import com.example.tripmingle.dto.req.chat.*;
import com.example.tripmingle.dto.res.chat.*;
import com.example.tripmingle.entity.*;
import com.example.tripmingle.port.in.ChatRoomUseCase;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomFacadeService implements ChatRoomUseCase {

    private final ChatRoomService chatRoomService;
    private final BoardService boardService;
    private final UserService userService;

    @Override
    public CreateChatRoomResDTO createGroupChatRoom(CreateGroupChatRoomReqDTO createGroupChatRoomReqDTO) {
        Board board = boardService.getBoardById(createGroupChatRoomReqDTO.getBoardId());

        // 마스터 유저 채팅방 입장
        User masterUser = board.getUser();
        GroupChatRoom groupChatRoom = chatRoomService.createGroupChatRoom(board, masterUser);
        ChatRoomUser chatRoomMasterUser = generateChatRoomUserEntity(groupChatRoom.getId(), board.getUser().getId(),
                ChatRoomType.GROUP);
        chatRoomService.joinGroupChatRoom(chatRoomMasterUser);

        // 채팅방 처음 입장한 사람이 게시글 마스터 유저인지 아니면 다른 유저인지
        User currentUser = userService.getCurrentUser();
        if (!masterUser.getId().equals(currentUser.getId())) {
            ChatRoomUser firstJoinChatRoomUser = generateChatRoomUserEntity(groupChatRoom.getId(),
                    currentUser.getId(), ChatRoomType.GROUP);
            chatRoomService.joinGroupChatRoom(firstJoinChatRoomUser);
        }
        return CreateChatRoomResDTO.builder()
                .chatRoomId(groupChatRoom.getId())
                .build();
    }

    private ChatRoomUser generateChatRoomUserEntity(Long chatRoomId, Long userId, ChatRoomType chatRoomType) {
        return ChatRoomUser.builder()
                .chatRoomId(chatRoomId)
                .user(userService.getUserById(userId))
                .chatRoomType(chatRoomType)
                .build();
    }

    @Override
    public CreateOneOnOneChatRoomResDTO createOneOnOneChatRoom(CreateOneOnOneChatRoomReqDTO createOneOnOneChatRoomReqDTO) {
            OneOnOneChatRoom newOneOnOneChatRoom = new OneOnOneChatRoom();
            OneOnOneChatRoom savedOneOnOneChatRoom = chatRoomService.createOneOnOneChatRoom(newOneOnOneChatRoom);
            // 1번 유저
            ChatRoomUser chatRoomUser1 = chatRoomService.joinGroupChatRoom(
                    generateChatRoomUserEntity(savedOneOnOneChatRoom.getId(), userService.getCurrentUser().getId(),
                            ChatRoomType.ONE_ON_ONE));
            // 2번 유저
            ChatRoomUser chatRoomUser2 = chatRoomService.joinGroupChatRoom(
                    generateChatRoomUserEntity(savedOneOnOneChatRoom.getId(), createOneOnOneChatRoomReqDTO.getContactUserId(),
                            ChatRoomType.ONE_ON_ONE));
            return CreateOneOnOneChatRoomResDTO.builder()
                    .chatRoomId(savedOneOnOneChatRoom.getId())
                    .participantId1(chatRoomUser1.getId())
                    .participantId2(chatRoomUser2.getId())
                    .build();
    }

    @Override
    public EnterGroupChatRoomResDTO enterGroupChatRoom(EnterGroupChatRoomReqDTO enterGroupChatRoomReqDTO) {
        User user = userService.getCurrentUser();
        GroupChatRoom groupChatRoom = chatRoomService.getGroupChatRoomByChatRoomId(
                enterGroupChatRoomReqDTO.getChatRoomId());
        validateExistChatRoomUser(user.getId());
        ChatRoomUser chatRoomUser = ChatRoomUser.builder()
                .chatRoomId(groupChatRoom.getId())
                .user(user)
                .chatRoomType(ChatRoomType.GROUP)
                .build();
        chatRoomService.joinGroupChatRoom(chatRoomUser);
        return EnterGroupChatRoomResDTO.builder()
                .groupChatRoomId(groupChatRoom.getId())
                .userId(user.getId())
                .build();
    }

    private void validateExistChatRoomUser(Long userId) {
        if (chatRoomService.existsUserInChatRoom(userId)) {
            throw new AlreadyExistsChatRoomUserException("Already Exists User In Chat Room", ErrorCode.ALREADY_EXISTS_USER_IN_CHAT_ROOM);
        }
    }

    @Override
    public ExitGroupChatRoomResDTO exitGroupChatRoom(ExitGroupChatRoomReqDTO exitGroupChatRoomReqDTO) {
        GroupChatRoom groupChatRoom = chatRoomService.getGroupChatRoomByChatRoomId(
                exitGroupChatRoomReqDTO.getGroupChatRoomId());
        User user = userService.getCurrentUser();
        exitUserForChatRoom(groupChatRoom.getId(), groupChatRoom.getUser().getId(), user.getId());
        return ExitGroupChatRoomResDTO.builder()
                .groupChatRoomId(groupChatRoom.getId())
                .userId(user.getId())
                .build();
    }

    private void exitUserForChatRoom(Long chatRoomId, Long chatRoomMasterUserId, Long userId) {
        if (chatRoomMasterUserId.equals(userId)) {
            List<ChatRoomUser> chatRoomUsers = chatRoomService.getChatRoomUsersByChatRoomIdAndUserId(chatRoomId,
                    userId);
            chatRoomUsers.forEach(ChatRoomUser::exitChatRoom);
        } else {
            ChatRoomUser chatRoomUser = chatRoomService.getChatRoomUserByChatRoomIdAndUserId(chatRoomId, userId);
            chatRoomUser.exitChatRoom();
        }
    }

    @Override
    public ExitOneOnOneChatRoomResDTO exitOneOnOneChatRoom(ExitOneOnOneChatRoomReqDTO exitOneOnOneChatRoomReqDTO) {
        User user = userService.getCurrentUser();
        ChatRoomUser chatRoomUser = chatRoomService.getChatRoomUserByChatRoomIdAndUserId(
                exitOneOnOneChatRoomReqDTO.getOneOnOneChatRoomId(), user.getId());
        chatRoomUser.exitChatRoom();
        return ExitOneOnOneChatRoomResDTO.builder()
                .oneOnOneChatRoomId(chatRoomUser.getChatRoomId())
                .userId(chatRoomUser.getUser().getId())
                .build();
    }
}
