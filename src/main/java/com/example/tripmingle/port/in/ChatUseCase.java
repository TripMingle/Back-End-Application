package com.example.tripmingle.port.in;

import org.springframework.data.domain.Pageable;

import com.example.tripmingle.dto.req.chat.GetAllChatMessageReqDTO;
import com.example.tripmingle.dto.res.chat.GetAllChatMessagesResDTO;

public interface ChatUseCase {

	GetAllChatMessagesResDTO getChatMessages(GetAllChatMessageReqDTO getAllChatMessageReqDTO, Pageable pageable);

}
