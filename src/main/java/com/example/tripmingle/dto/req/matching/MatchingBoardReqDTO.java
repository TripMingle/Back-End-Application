package com.example.tripmingle.dto.req.matching;

import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MatchingBoardReqDTO {
	private String countryName;
	private LocalDate startDate;
	private LocalDate endDate;
}
