package com.example.tripmingle.dto.res.posting;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostingLikeToggleStateResDTO {

    private Long postingId;
    private boolean postingToggleState;

}
