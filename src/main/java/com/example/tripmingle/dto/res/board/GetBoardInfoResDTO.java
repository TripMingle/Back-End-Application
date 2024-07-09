package com.example.tripmingle.dto.res.board;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetBoardInfoResDTO {
	//게시글
	private Long boardId;
	private String continent;
	private String countryName;
	private String title;
	private String content;
	private String language;
	private double preferGender; // 선호 성별
	private double preferSmoking; // 선호 흡연타입
	private double preferActivity; // 선호 활동 - 액티비티
	private double preferInstagramPicture; // 선호 활동 - 인스타사진
	private double preferFoodExploration; // 선호 활동 - 맛집탐방
	private double preferAdventure; // 선호 활동 - 탐험

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
	private boolean isParticipating;
	private boolean isExpired;
	//지났는지 안지났는지

	//게시글 작성자
	private Long userId;
	private String nickName;
	private String ageRange;
	private String gender;
	private String nationality;
	private String selfIntroduction;
	//온도
	//사진
	private String imageUrl;

	//댓글
	private List<ParentBoardCommentResDTO> boardCommentResDTOS;

}
