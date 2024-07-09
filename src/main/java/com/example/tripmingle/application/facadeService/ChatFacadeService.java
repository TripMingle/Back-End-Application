package com.example.tripmingle.application.facadeService;

import com.example.tripmingle.application.service.ChatRoomService;
import com.example.tripmingle.application.service.ChatService;
import com.example.tripmingle.application.service.UserService;
import com.example.tripmingle.dto.res.chat.GetAllChatMessagesResDTO;
import com.example.tripmingle.dto.res.chat.GetChatMessageResDTO;
import com.example.tripmingle.entity.ChatMessage;
import com.example.tripmingle.entity.GroupChatRoom;
import com.example.tripmingle.port.in.ChatUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChatFacadeService implements ChatUseCase {

    private final UserService userService;
    private final ChatRoomService chatRoomService;
    private final ChatService chatService;

    @Override
    public GetAllChatMessagesResDTO getChatMessages(Long chatRoomId, Pageable pageable) {
        GroupChatRoom chatRoom = chatRoomService.getGroupChatRoomByChatRoomId(chatRoomId);
        Slice<ChatMessage> chatMessages = chatService.getChatMessages(chatRoomId, pageable);
        List<GetChatMessageResDTO> chatMessageResDTOS = chatMessages.stream()
                .map(chat -> GetChatMessageResDTO.builder()
                        .userId(chat.getSenderId())
                        .userName(userService.getUserById(chat.getSenderId()).getNickName())
                        .content(chat.getContent())
                        .sendingTime(LocalDateTime.parse(chat.getSendingTime()))
                        .build())
                .collect(Collectors.toList());
        return GetAllChatMessagesResDTO.builder()
                .chatMessages(chatMessageResDTOS)
                .chatRoomId(chatRoom.getId())
                .build();
    }
}
