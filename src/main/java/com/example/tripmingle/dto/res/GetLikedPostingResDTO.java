package com.example.tripmingle.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetLikedPostingResDTO {

    private Long postingId;
    private String title;
    private String content;

}
