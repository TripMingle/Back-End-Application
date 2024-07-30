package com.example.tripmingle.dto.req.matching;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserPersonalityReqDTO {
	private int vegan; // 비건 여부
	private int islam; // 이슬람 신앙
	private int hindu; // 힌두 신앙
	private int smoking; // 흡연 여부
	private int budget; // 예산 범위
	private int accommodationFlexibility; // 숙소 선택 성향
	private int foodFlexibility; // 음식 선택 성향
	private int activity; // 선호 활동 - 액티비티
	private int photo; // 선호 활동 - 사진
	private int foodExploration; // 선호 활동 - 맛집탐방
	private int adventure; // 선호 활동 - 탐험
	private int personality; // 성격
	private int schedule; // 일정 계획 성향
	private int drink; //음주 성향
}
