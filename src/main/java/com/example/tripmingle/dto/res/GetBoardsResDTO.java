package com.example.tripmingle.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
    private String ageRange;
    private String gender;
    private String nationality;

    private boolean isMine;

}
