package com.example.tripmingle.dto.req;

import com.example.tripmingle.entity.PostingType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PatchPostingReqDTO {

    private Long postingId;
    private String title;
    private String content;
    private PostingType postingType;

}
