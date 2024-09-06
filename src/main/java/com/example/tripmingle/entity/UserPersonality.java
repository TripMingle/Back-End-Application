package com.example.tripmingle.entity;

import com.example.tripmingle.dto.etc.ChangeUserPersonalityDTO;
import com.example.tripmingle.dto.req.matching.PostUserPersonalityReqDTO;
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

import static com.example.tripmingle.common.constants.Constants.WEIGHTS;

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

	public double[] toFeatureVector() {
		return new double[] {
				(gender-3.0) * WEIGHTS[0],
				(vegan-3.0) * WEIGHTS[1],
				(islam-3.0) * WEIGHTS[2],
				(hindu-3.0) * WEIGHTS[3],
				(smoking-3.0) * WEIGHTS[4],
				(budget-3.0) * WEIGHTS[5],
				(accommodationFlexibility-3.0) * WEIGHTS[6],
				(foodFlexibility-3.0) * WEIGHTS[7],
				(activity-3.0) * WEIGHTS[8],
				(photo-3.0) * WEIGHTS[9],
				(foodExploration-3.0) * WEIGHTS[10],
				(adventure-3.0) * WEIGHTS[11],
				(personality-3.0) * WEIGHTS[12],
				(schedule-3.0) * WEIGHTS[13],
				(drink-3.0) * WEIGHTS[14],
				(ageRange-3.0) * WEIGHTS[15]
		};
	}

	public void changeUserPersonality(ChangeUserPersonalityDTO changeUserPersonalityDTO) {

	}
}
