package com.example.tripmingle.dto.res.schedule;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetUserTripResDTO {
	private Long userTripId;
	private String continent;
	private String countryName;
	private LocalDate startDate;
	private LocalDate endDate;
}
