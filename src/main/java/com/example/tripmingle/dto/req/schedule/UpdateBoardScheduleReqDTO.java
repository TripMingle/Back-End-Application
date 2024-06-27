package com.example.tripmingle.dto.req.schedule;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateBoardScheduleReqDTO {
    private Long boardScheduleId;
    private LocalDate date;
    private String placeName;
    private int number;
    private double pointX;
    private double pointY;
    private String googlePlaceId;

}
