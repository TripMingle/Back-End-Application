package com.example.tripmingle.dto.req;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchPostingCommentReqDTO {

    private Long postingCommentId;
    private String comment;

}
