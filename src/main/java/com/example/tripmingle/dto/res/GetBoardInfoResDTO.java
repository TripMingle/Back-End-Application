package com.example.tripmingle.dto.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class GetBoardInfoResDTO {
    //게시글
    private Long boardId;
    private String title;
    private String content;
    private String language;
    private List<String> traits;
    private List<String> types;
    private LocalDate startDate;
    private LocalDate endDate;
    private int currentCount;
    private int maxCount;
    private LocalDateTime createdAt;
    private int commentCount;
    private boolean isMine;

    //게시글 작성자
    private Long userId;
    private String nickName;
    private String ageRange;
    private String gender;
    private String nationality;
    private String selfIntroduction;
        //온도
        //사진

    //댓글
    private List<ParentBoardCommentResDTO> boardCommentResDTOS;


    //일정
}