package com.example.tripmingle.dto.etc;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class MatchingBoardPublishDTO {
	private Long userId;
	private String countryName;
	private LocalDate startDate;
	private LocalDate endDate;
}
