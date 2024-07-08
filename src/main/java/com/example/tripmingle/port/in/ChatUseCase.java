package com.example.tripmingle.port.in;

import com.example.tripmingle.dto.res.chat.GetAllChatMessagesResDTO;
import org.springframework.data.domain.Pageable;

public interface ChatUseCase {

    GetAllChatMessagesResDTO getChatMessages(Long chatRoomId, Pageable pageable);

}
