package com.example.tripmingle.dto.req.board;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UpdateBoardReqDTO {
    private String continent;
    private String countryName;
    private LocalDate startDate;
    private LocalDate endDate;
    private String language;
    private int maxCount;
    //private List<String> tripType;
    //private List<String> personalType;
    private double preferGender; // 선호 성별
    private double preferSmoking; // 선호 흡연타입
    private double preferActivity; // 선호 활동 - 액티비티
    private double preferInstagramPicture; // 선호 활동 - 인스타사진
    private double preferFoodExploration; // 선호 활동 - 맛집탐방
    private double preferAdventure; // 선호 활동 - 탐험
    private String title;
    private String content;
}
