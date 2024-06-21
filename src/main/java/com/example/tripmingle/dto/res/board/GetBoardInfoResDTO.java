package com.example.tripmingle.dto.res.board;

import lombok.Builder;
import lombok.Getter;

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
    private List<String> personalType;
    private List<String> tripType;
    private LocalDate startDate;
    private LocalDate endDate;
    private int currentCount;
    private int maxCount;
    private LocalDateTime createdAt;
    private int commentCount;
    private int likeCount;
    private int bookMarkCount;
    private boolean isMine;
    private boolean isLiked;
    private boolean isBookMarked;

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
