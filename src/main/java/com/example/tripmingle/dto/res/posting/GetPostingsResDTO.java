package com.example.tripmingle.dto.res.posting;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetPostingsResDTO {

    private Long postingId;
    private String title;
    private String content;
    private int commentCount;
    private int likeCount;
    private String country;
    private String userNickName;
    private String userAgeRange;
    private String userGender;
    private String userNationality;
    private boolean myLikeState;

}
