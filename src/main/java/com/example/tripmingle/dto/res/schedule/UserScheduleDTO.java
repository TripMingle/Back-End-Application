package com.example.tripmingle.dto.res.schedule;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class UserScheduleDTO {
    private Long userTripId;
    private Long userScheduleId;
    private LocalDate date;
    private String placeName;
    private int number;
    private double pointX;
    private double pointY;
    private String googlePlaceId;
}
