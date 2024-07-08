package com.example.tripmingle.dto.res.chat;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class GetChatMessageResDTO {

    private Long userId;
    private String userName;
    private String content;
    private LocalDateTime sendingTime;

}
