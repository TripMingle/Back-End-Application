package com.example.tripmingle.dto.res;

import com.example.tripmingle.entity.AgeRange;
import com.example.tripmingle.entity.Gender;
import lombok.*;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@Builder
public class GetBoardInfoResDTO {
    //게시글
    private Long boardId;
    private String title;
    private String content;
    private String language;
    private String traits;
    private String types;
    private LocalDate startDate;
    private LocalDate endDate;
    private int currentCount;
    private int maxCount;

    //댓글
    private List<BoardCommentResDTO> boardCommentResDTOS;

    //게시글 작성자
    private Long userId;
    private String nickName;
    private String ageRange;
    private String gender;
    private String nationality;
    private String selfIntroduction;
    //온도
    //사진

    //일정
}
