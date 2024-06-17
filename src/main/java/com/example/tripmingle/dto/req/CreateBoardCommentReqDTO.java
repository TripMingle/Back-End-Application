package com.example.tripmingle.dto.req;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBoardCommentReqDTO {
    private Long boardId;
    private Long parentBoardCommentId;
    private String content;
}
