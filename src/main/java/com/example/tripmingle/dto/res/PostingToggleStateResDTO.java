package com.example.tripmingle.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PostingToggleStateResDTO {

    private Long postingId;
    private boolean postingToggleState;

}
