package com.example.tripmingle.dto.res;

import com.example.tripmingle.entity.AgeRange;
import com.example.tripmingle.entity.Gender;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class GetBoardsResDTO {
    //board info
    private Long boardId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private int maxCount;
    private String language;
    //user info
    private String nickName;
    private AgeRange ageRange;
    private Gender gender;
    private String nationality;



}
