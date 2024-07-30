package com.example.tripmingle.dto.req.board;

import java.time.LocalDate;
import java.util.List;

import com.example.tripmingle.dto.req.schedule.CreateBoardScheduleReqDTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CreateBoardReqDTO {
	private String continent;
	private String countryName;
	private LocalDate startDate;
	private LocalDate endDate;
	private String language;
	private int maxCount;
	private List<String> tripType;
	private int preferGender; // 선호 성별
	private int preferSmoking; // 선호 흡연타입
	private int preferBudget; // 선호 활동 - 예산
	private int preferPhoto; // 선호 활동 - 사진
	private int preferDrink; // 선호 활동 - 음주
	private String title;
	private String content;

	List<CreateBoardScheduleReqDTO> createBoardSchedule;

}
