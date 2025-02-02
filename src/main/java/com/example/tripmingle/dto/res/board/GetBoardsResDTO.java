package com.example.tripmingle.dto.res.board;

import java.time.LocalDate;
import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetBoardsResDTO {
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

	//user info
	private String nickName;
	private String ageRange;
	private String gender;
	private String nationality;
	private String userImageUrl;

	private boolean isMine;
	private boolean isLiked;
	private boolean isBookMarked;
	private boolean isParticipating;
	private boolean isExpired;

}
