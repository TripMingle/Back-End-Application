package com.example.tripmingle.dto.res.schedule;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class UserScheduleResDTO {
	private Long userTripId;
	private Long userScheduleId;
	private LocalDate date;
	private String placeName;
	private int number;
	private double pointX;
	private double pointY;
	private String googlePlaceId;
}
