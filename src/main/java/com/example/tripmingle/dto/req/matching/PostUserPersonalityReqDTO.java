package com.example.tripmingle.dto.req.matching;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserPersonalityReqDTO {
	private double vegan; // 비건 여부
	private double islam; // 이슬람 신앙
	private double hindu; // 힌두 신앙
	private double smoking; // 흡연 여부
	private double budget; // 예산 범위
	private double accommodationFlexibility; // 숙소 선택 성향
	private double foodFlexibility; // 음식 선택 성향
	private double activity; // 선호 활동 - 액티비티
	private double instagramPicture; // 선호 활동 - 인스타사진
	private double foodExploration; // 선호 활동 - 맛집탐방
	private double adventure; // 선호 활동 - 탐험
	private double personality; // 성격
	private double schedule; // 일정 계획 성향
	private double shopping; // 쇼핑성향
	private double drink; //음주 성향
}
