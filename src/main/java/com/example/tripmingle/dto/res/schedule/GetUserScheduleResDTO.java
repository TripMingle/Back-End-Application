package com.example.tripmingle.dto.res.schedule;

import java.util.List;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class GetUserScheduleResDTO {
	private Long userTripId;
	List<UserScheduleDTO> userSchedules;
}
