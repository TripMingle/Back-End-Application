package com.example.tripmingle.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPersonality {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@OneToOne
	@JoinColumn(name = "user_id", nullable = false)
	private User user;

	private double gender; // 성별, 자동생성
	private double vegan; // 비건 여부
	private double islam; // 이슬람 신앙
	private double hindu; // 힌두 신앙
	private double smoking; // 흡연 여부
	private double budget; // 예산 범위
	private double accommodationFlexibility; // 숙소 선택 성향
	private double foodFlexibility; // 음식 선택 성향
	private double activity; // 선호 활동 - 액티비티
	private double photo; // 선호 활동 - 사진
	private double foodExploration; // 선호 활동 - 맛집탐방
	private double adventure; // 선호 활동 - 탐험
	private double personality; // 성격
	private double schedule; // 일정 계획 성향
	private double drink; //음주 성향
	private double ageRange; // 나이대, 자동생성

}
