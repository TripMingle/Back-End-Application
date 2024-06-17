package com.example.tripmingle.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetOnePostingCoCommentResDTO {

    private Long coCommentId;
    private Long parentCommentId;
    private String userNickName;
    private String coComment;

}
