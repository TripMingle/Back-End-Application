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

    private ChatRoomUser generateChatRoomUserEntity(Long chatRoomId, Long userId, ChatRoomType chatRoomType) {
        return ChatRoomUser.builder()
                .chatRoomId(chatRoomId)
                .user(userService.getUserById(userId))
                .chatRoomType(chatRoomType)
                .build();
    }

    @Override
    public EnterOneOnOneChatRoomResDTO enterOneOnOneChatRoom(EnterOneOnOneChatRoomReqDTO enterOneOnOneChatRoomReqDTO) {
        User currentUser = userService.getCurrentUser();
        User contactUser = userService.getUserById(enterOneOnOneChatRoomReqDTO.getContactUserId());
        OneOnOneChatRoom oneOnOneChatRoom = null;
        if (!chatRoomService.existsOneOnOneChatRoomByUserIds(currentUser.getId(), contactUser.getId())) {
            oneOnOneChatRoom = OneOnOneChatRoom.builder()
                .user1(currentUser)
                .user2(contactUser)
                .build();
            OneOnOneChatRoom savedOneOnOneChatRoom = chatRoomService.saveOneOnOneChatRoom(oneOnOneChatRoom);

            ChatRoomUser currentChatRoomUser = generateChatRoomUserEntity(savedOneOnOneChatRoom.getId(),
                currentUser.getId(), ChatRoomType.ONE_ON_ONE);
            chatRoomService.joinOneOnOneChatRoom(currentChatRoomUser);

            ChatRoomUser contactChatRoomUser = generateChatRoomUserEntity(savedOneOnOneChatRoom.getId(),
                contactUser.getId(), ChatRoomType.ONE_ON_ONE);
            chatRoomService.joinOneOnOneChatRoom(contactChatRoomUser);
        } else {
            oneOnOneChatRoom = chatRoomService.getOneOnOneChatRoomByUserIds(currentUser.getId(), contactUser.getId());
        }
        return EnterOneOnOneChatRoomResDTO.builder()
            .chatRoomId(oneOnOneChatRoom.getId())
            .participantId1(currentUser.getId())
            .participantId2(contactUser.getId())
            .build();
    }

    @Override
    public EnterGroupChatRoomResDTO enterGroupChatRoom(EnterGroupChatRoomReqDTO enterGroupChatRoomReqDTO) {
        User user = userService.getCurrentUser();
        Board board = boardService.getBoardById(enterGroupChatRoomReqDTO.getBoardId());
        GroupChatRoom groupChatRoom = null;
        if (!chatRoomService.existsGroupChatRoomByBoardId(enterGroupChatRoomReqDTO.getBoardId())) {
            groupChatRoom = GroupChatRoom.builder()
                .board(board)
                .user(board.getUser())
                .build();
            GroupChatRoom savedChatRoom = chatRoomService.saveGroupChatRoom(board, board.getUser());

            ChatRoomUser chatRoomMasterUser = ChatRoomUser.builder()
                .user(board.getUser())
                .chatRoomId(savedChatRoom.getId())
                .chatRoomType(ChatRoomType.GROUP)
                .build();
            chatRoomService.joinGroupChatRoom(chatRoomMasterUser);
        } else {
            groupChatRoom = chatRoomService.getGroupChatRoomByBoardId(enterGroupChatRoomReqDTO.getBoardId());
        }
        if (!chatRoomService.existsUserInChatRoom(user.getId())) {
            ChatRoomUser chatRoomUser = ChatRoomUser.builder()
                .chatRoomId(groupChatRoom.getId())
                .user(user)
                .chatRoomType(ChatRoomType.GROUP)
                .build();
            chatRoomService.joinGroupChatRoom(chatRoomUser);
        }
        return EnterGroupChatRoomResDTO.builder()
                .groupChatRoomId(groupChatRoom.getId())
                .userId(user.getId())
                .build();
    }

    @Override
    public ExitGroupChatRoomResDTO exitGroupChatRoom(ExitGroupChatRoomReqDTO exitGroupChatRoomReqDTO) {
        GroupChatRoom groupChatRoom = chatRoomService.getGroupChatRoomByChatRoomId(
                exitGroupChatRoomReqDTO.getGroupChatRoomId());
        User user = userService.getCurrentUser();
        exitUserForChatRoom(groupChatRoom.getId(), groupChatRoom.getUser().getId(), user.getId());
        if (chatRoomService.countGroupChatRoomUser(exitGroupChatRoomReqDTO.getGroupChatRoomId()) == 0) {
            groupChatRoom.delete();
        }
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
        OneOnOneChatRoom oneOnOneChatRoom = chatRoomService.getOneOnOneChatRoomByChatRoomId(exitOneOnOneChatRoomReqDTO.getOneOnOneChatRoomId());
        ChatRoomUser chatRoomUser = chatRoomService.getChatRoomUserByChatRoomIdAndUserId(
                exitOneOnOneChatRoomReqDTO.getOneOnOneChatRoomId(), user.getId());
        chatRoomUser.exitChatRoom();
        if (chatRoomService.countOneOnOneChatRoomUser(exitOneOnOneChatRoomReqDTO.getOneOnOneChatRoomId()) == 0) {
            chatRoomService.deleteOneOnOneChatRoom(oneOnOneChatRoom);
        }
        return ExitOneOnOneChatRoomResDTO.builder()
                .oneOnOneChatRoomId(chatRoomUser.getChatRoomId())
                .userId(chatRoomUser.getUser().getId())
                .build();
    }
}
