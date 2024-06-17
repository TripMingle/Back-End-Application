package com.example.tripmingle.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CreateBoardCommentResDTO {
    private Long parentBoardCommentId;
    private Long BoardCommentId;
    private String content;
    //user
    private Long userId;
    private String userNickname;
    private LocalDateTime createdAt;
}

