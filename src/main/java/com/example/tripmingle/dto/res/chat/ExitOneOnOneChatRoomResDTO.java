package com.example.tripmingle.dto.res.chat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExitOneOnOneChatRoomResDTO {

    private Long oneOnOneChatRoomId;
    private Long userId;

}
