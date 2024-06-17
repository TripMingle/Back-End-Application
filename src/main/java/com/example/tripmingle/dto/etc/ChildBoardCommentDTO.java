package com.example.tripmingle.dto.etc;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class ChildBoardCommentDTO {
    private Long boardId;
    private Long boardCommentId;
    private Long parentId;
    private String content;
    private LocalDateTime registeredDate;
    private Long userId;
    private String userNickname;
    private boolean isMine;
}
