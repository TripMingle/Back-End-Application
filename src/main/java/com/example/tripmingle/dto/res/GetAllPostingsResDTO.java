package com.example.tripmingle.dto.res;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetAllPostingsResDTO {

    private Long postingId;
    private String title;
    private String content;
    private String userNickName;
    private String userAgeRange;
    private String userGender;
    private String userNationality;
    private boolean myLikeState;

}
