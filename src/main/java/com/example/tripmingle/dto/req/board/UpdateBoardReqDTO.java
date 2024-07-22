package com.example.tripmingle.dto.req.board;

import java.time.LocalDate;
import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateBoardReqDTO {
	private LocalDate startDate;
	private LocalDate endDate;
	private String language;
	private int maxCount;
	private List<String> tripType;
	private double preferGender; // 선호 성별
	private double preferSmoking; // 선호 흡연타입
	private double preferShopping; // 선호 활동 - 쇼핑
	private double preferInstagramPicture; // 선호 활동 - 인스타사진
	private double preferDrink; // 선호 활동 - 음주

	private String title;
	private String content;
}
