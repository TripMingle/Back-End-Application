package com.example.tripmingle.dto.res.posting;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetOnePostingResDTO {

    private String title;
    private String content;
    private String country;
    private LocalDateTime createAt;
    private Long heartCount;
    private String userNickName;
    private String userAgeRange;
    private String userGender;
    private String userNationality;
    private String selfIntroduce;
    private String userTemperature;
    private boolean myLikeState;
    private int commentCount;
    private int likeCount;
    private List<GetOnePostingCommentsResDTO> postingComments;

}
