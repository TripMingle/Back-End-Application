package com.example.tripmingle.application.facadeService;

import static com.example.tripmingle.common.constants.Constants.*;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.tripmingle.application.service.BoardService;
import com.example.tripmingle.application.service.ChatRoomService;
import com.example.tripmingle.application.service.ChatService;
import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.dto.req.chat.EnterGroupChatRoomReqDTO;
import com.example.tripmingle.dto.req.chat.EnterOneOnOneChatRoomReqDTO;
import com.example.tripmingle.dto.req.chat.ExitGroupChatRoomReqDTO;
import com.example.tripmingle.dto.req.chat.ExitOneOnOneChatRoomReqDTO;
import com.example.tripmingle.dto.res.chat.EnterGroupChatRoomResDTO;
import com.example.tripmingle.dto.res.chat.EnterOneOnOneChatRoomResDTO;
import com.example.tripmingle.dto.res.chat.ExitGroupChatRoomResDTO;
import com.example.tripmingle.dto.res.chat.ExitOneOnOneChatRoomResDTO;
import com.example.tripmingle.entity.Board;
import com.example.tripmingle.entity.ChatRoomType;
import com.example.tripmingle.entity.ChatRoomUser;
import com.example.tripmingle.entity.GroupChatRoom;
import com.example.tripmingle.entity.OneOnOneChatRoom;
import com.example.tripmingle.entity.User;
import com.example.tripmingle.port.in.ChatRoomUseCase;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional
public class ChatRoomFacadeService implements ChatRoomUseCase {

	private final ChatRoomService chatRoomService;
	private final ChatService chatService;
	private final BoardService boardService;
	private final UserService userService;

	private ChatRoomUser generateChatRoomUserEntity(Long chatRoomId, Long userId, ChatRoomType chatRoomType,
		Long chatFirstIndex) {
		return ChatRoomUser.builder()
			.chatRoomId(chatRoomId)
			.user(userService.getUserById(userId))
			.chatRoomType(chatRoomType)
			.chatFirstIndex(chatFirstIndex)
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
				currentUser.getId(), ChatRoomType.ONE_ON_ONE, FIRST_ENTER_CHAT_ROOM_CHAT_COUNT);
			chatRoomService.joinOneOnOneChatRoom(currentChatRoomUser);

			ChatRoomUser contactChatRoomUser = generateChatRoomUserEntity(savedOneOnOneChatRoom.getId(),
				contactUser.getId(), ChatRoomType.ONE_ON_ONE, FIRST_ENTER_CHAT_ROOM_CHAT_COUNT);
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
			groupChatRoom = chatRoomService.saveGroupChatRoom(board, board.getUser());
			ChatRoomUser chatRoomMasterUser = ChatRoomUser.builder()
				.user(board.getUser())
				.chatRoomId(groupChatRoom.getId())
				.chatRoomType(ChatRoomType.GROUP)
				.chatFirstIndex(FIRST_ENTER_CHAT_ROOM_CHAT_COUNT)
				.build();
			chatRoomService.joinGroupChatRoom(chatRoomMasterUser);
		} else {
			groupChatRoom = chatRoomService.getGroupChatRoomByBoardId(enterGroupChatRoomReqDTO.getBoardId());
		}
		if (!chatRoomService.existsUserInChatRoom(user.getId())) {
			Long alreadyExistsChatCount = chatService.getChatMessagesCount(ChatRoomType.GROUP, groupChatRoom.getId());
			ChatRoomUser chatRoomUser = ChatRoomUser.builder()
				.chatRoomId(groupChatRoom.getId())
				.user(user)
				.chatRoomType(ChatRoomType.GROUP)
				.chatFirstIndex(
					alreadyExistsChatCount == 0 ? FIRST_ENTER_CHAT_ROOM_CHAT_COUNT : alreadyExistsChatCount - 1)
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
		OneOnOneChatRoom oneOnOneChatRoom = chatRoomService.getOneOnOneChatRoomByChatRoomId(
			exitOneOnOneChatRoomReqDTO.getOneOnOneChatRoomId());
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
