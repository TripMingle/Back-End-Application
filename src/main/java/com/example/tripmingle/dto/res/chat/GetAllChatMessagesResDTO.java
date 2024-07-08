package com.example.tripmingle.dto.res.chat;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAllChatMessagesResDTO {

    private Long chatRoomId;
    private List<GetChatMessageResDTO> chatMessages;

}
