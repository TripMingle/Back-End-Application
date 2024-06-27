package com.example.tripmingle.dto.res.schedule;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;

@Getter
@Builder
public class BoardScheduleResDTO {

    private Long boardId;
    private Long boardScheduleId;
    private LocalDate date;
    private String placeName;
    private int number;
    private double pointX;
    private double pointY;
    private String googlePlaceId;

}
