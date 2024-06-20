package com.example.tripmingle.dto.res.posting;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetLikedPostingResDTO {

    private Long postingId;
    private String title;
    private String content;
    private boolean myLikeState;
    private int commentCount;
    private String country;

}
