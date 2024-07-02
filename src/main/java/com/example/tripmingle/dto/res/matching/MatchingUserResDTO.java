package com.example.tripmingle.dto.res.matching;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class MatchingUserResDTO {
    //유저정보
    private Long userId;
    private String nickName;
    private String ageRange;
    private String gender;
    private String nationality;
    private String selfIntroduction;

    //유저 성향 정보
    private Long userPersonalityId;
    private double vegan; // 비건 여부
    private double islam; // 이슬람 신앙
    private double hindu; // 힌두 신앙
    private double smoking; // 흡연 여부
    private double budget; // 예산 범위
    private double accommodationFlexibility; // 숙소 선택 성향
    private double foodFlexibility; // 음식 선택 성향
    private double activity; // 선호 활동 - 액티비티
    private double instagramPicture; // 선호 활동 - 인스타사진
    private double foodExploration; // 선호 활동 - 맛집탐방
    private double adventure; // 선호 활동 - 탐험
    private double personality; // 성격
    private double schedule; // 일정 계획 성향

}
