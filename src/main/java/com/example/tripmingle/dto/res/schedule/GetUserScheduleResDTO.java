package com.example.tripmingle.dto.res.schedule;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class GetUserScheduleResDTO {
    private Long userTripId;
    List<UserScheduleDTO> userScheduleDTOList;
}
