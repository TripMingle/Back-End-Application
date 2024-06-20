package com.example.tripmingle.dto.res.posting;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetAllLikedPostingResDTO {

    private String userNickName;
    private String userAgeRange;
    private String userGender;
    private String userNationality;
    private List<GetLikedPostingResDTO> likedPostings;

}
