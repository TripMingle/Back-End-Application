package com.example.tripmingle.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetOnePostingCommentsResDTO {

    private Long commentId;
    private String userNickName;
    private String comment;
    private List<GetOnePostingCoCommentResDTO> postingCoComment;

}
