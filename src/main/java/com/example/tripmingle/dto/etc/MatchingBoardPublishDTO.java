package com.example.tripmingle.dto.etc;

import java.time.LocalDate;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatchingBoardPublishDTO {
	private Long userId;
	private String messageId;
	private String countryName;
	private LocalDate startDate;
	private LocalDate endDate;
}
