package com.example.tripmingle.dto.res.matching;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class MatchingBoardResDTO {

	private String continent;
	private String countryName;
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
	private String imageUrl;

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
