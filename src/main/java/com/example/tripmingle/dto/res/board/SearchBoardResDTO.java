package com.example.tripmingle.dto.res.board;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Builder
@Getter
public class SearchBoardResDTO {

    //board info
    private String continent;
    private String countryName;
    private Long boardId;
    private String title;
    private LocalDateTime createdAt;
    private LocalDate startDate;
    private LocalDate endDate;
    private int currentCount;
    private int maxCount;
    private String language;
    private int commentCount;
    private int likeCount;
    private int bookMarkCount;
    private String imageUrl;
    private String content;

    //user info
    private String nickName;
    private String ageRange;
    private String gender;
    private String nationality;
    private String userImageUrl;

}
