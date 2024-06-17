package com.example.tripmingle.dto.res;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetOnePostingResDTO {

    private String title;
    private String content;
    private LocalDateTime createAt;
    private Long heartCount;
    private List<GetOnePostingCommentsResDTO> postingComments;
    private String userNickName;
    private String userAgeRange;
    private String userGender;
    private String userNationality;
    private String selfIntroduce;
    private String userTemperature;

}
