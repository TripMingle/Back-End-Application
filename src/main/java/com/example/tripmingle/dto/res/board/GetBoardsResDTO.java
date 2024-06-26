package com.example.tripmingle.dto.res.board;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class GetBoardsResDTO {
    //board info
    private Long boardId;
    private String title;
    private LocalDate startDate;
    private LocalDate endDate;
    private int currentCount;
    private int maxCount;
    private String language;
    private int commentCount;
    private int likeCount;
    private int bookMarkCount;
    //user info
    private String nickName;
    private String ageRange;
    private String gender;
    private String nationality;

    private boolean isMine;
    private boolean isLiked;
    private boolean isBookMarked;
    private boolean isParticipating;
    private boolean isExpired;

}
