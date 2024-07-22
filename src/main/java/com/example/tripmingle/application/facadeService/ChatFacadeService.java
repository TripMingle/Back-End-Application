package com.example.tripmingle.application.facadeService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import com.example.tripmingle.application.service.ChatRoomService;
import com.example.tripmingle.application.service.ChatService;
import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.dto.req.chat.GetAllChatMessageReqDTO;
import com.example.tripmingle.dto.res.chat.GetAllChatMessagesResDTO;
import com.example.tripmingle.dto.res.chat.GetChatMessageResDTO;
import com.example.tripmingle.entity.ChatMessage;
import com.example.tripmingle.port.in.ChatUseCase;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatFacadeService implements ChatUseCase {

	private final UserService userService;
	private final ChatRoomService chatRoomService;
	private final ChatService chatService;

	@Override
	public GetAllChatMessagesResDTO getChatMessages(GetAllChatMessageReqDTO getAllChatMessageReqDTO,
		Pageable pageable) {
		Slice<ChatMessage> chatMessages = chatService.getChatMessages(getAllChatMessageReqDTO,
			pageable);
		List<GetChatMessageResDTO> chatMessageResDTOS = chatMessages.stream()
			.map(chat -> GetChatMessageResDTO.builder()
				.userId(chat.getSenderId())
				.userName(userService.getUserById(chat.getSenderId()).getNickName())
				.message(chat.getMessage())
				.sendingTime(chat.getSendingTime())
				.build())
			.collect(Collectors.toList());
		return GetAllChatMessagesResDTO.builder()
			.chatMessages(chatMessageResDTOS)
			.chatRoomId(getAllChatMessageReqDTO.getChatRoomId())
			.chatRoomType(getAllChatMessageReqDTO.getChatRoomType())
			.build();
	}
}
