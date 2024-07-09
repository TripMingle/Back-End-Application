package com.example.tripmingle.dto.res.chat;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ExitGroupChatRoomResDTO {

    private Long groupChatRoomId;
    private Long userId;

}
