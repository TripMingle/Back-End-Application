package com.example.tripmingle.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class BoardCommentResDTO {
    private Long boardId;
    private Long parentCommentId;
    private String content;
    private LocalDateTime registeredDate;
    //유저정보
    private Long userId;
    private String userNickname;
}
