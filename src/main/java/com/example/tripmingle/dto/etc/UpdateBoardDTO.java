package com.example.tripmingle.dto.etc;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UpdateBoardDTO {
	private String continent;
	private String countryName;
	private LocalDate startDate;
	private LocalDate endDate;
	private String language;
	private int maxCount;
	private double preferGender; // 선호 성별
	private double preferSmoking; // 선호 흡연타입
	private double preferShopping; // 선호 활동 - 쇼핑
	private double preferInstagramPicture; // 선호 활동 - 인스타사진
	private double preferDrink; // 선호 활동 - 음주
	private String tripType;
	private String title;
	private String content;
}
