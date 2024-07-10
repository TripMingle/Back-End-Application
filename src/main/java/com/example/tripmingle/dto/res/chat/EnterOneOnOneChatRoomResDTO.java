package com.example.tripmingle.dto.res.chat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class EnterOneOnOneChatRoomResDTO {

    private Long chatRoomId;
    private Long participantId1;
    private Long participantId2;

}
