package com.example.tripmingle.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostPostingCommentReqDTO {

    private Long postingId;
    private Long parentCommentId;
    private String comment;

}
